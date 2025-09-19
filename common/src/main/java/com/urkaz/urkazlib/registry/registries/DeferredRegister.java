/*
 * This file is part of "UrkazLib".
 * Copyright (C) 2025 Urkaz - Fran Sánchez
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.urkaz.urkazlib.registry.registries;

import com.urkaz.urkazlib.platform.registry.registries.IPlatformDeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class DeferredRegister<T> implements Iterable<DeferredHolder<T>>{

    private final ResourceKey<? extends Registry<T>> registryKey;
    private final String namespace;
    private final Map<DeferredHolder<T>, Supplier<? extends T>> entries = new LinkedHashMap<>();
    private final Set<DeferredHolder<T>> entriesView = Collections.unmodifiableSet(entries.keySet());

    private boolean seenRegisterEvent = false;
    private boolean registeredEventBus = false;

    protected DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        this.registryKey = Objects.requireNonNull(registryKey);
        this.namespace = Objects.requireNonNull(namespace);
    }

    public static <T> DeferredRegister<T> create(Registry<T> registry, String namespace) {
        return new DeferredRegister<>(registry.key(), namespace);
    }

    public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        return new DeferredRegister<>(key, namespace);
    }

    public static <B> DeferredRegister<B> create(ResourceLocation registryName, String modid) {
        return new DeferredRegister<>(ResourceKey.createRegistryKey(registryName), modid);
    }

    public <R extends T> DeferredHolder<R> register(final String name, final Supplier<? extends R> sup) {
        return this.register(name, key -> sup.get());
    }

    @SuppressWarnings("unchecked")
    public <R extends T> DeferredHolder<R> register(final String name, final Function<ResourceLocation, ? extends R> func) {
        if (seenRegisterEvent)
            throw new IllegalStateException("Cannot register new entries to DeferredRegister after RegisterEvent has been fired.");
        Objects.requireNonNull(name);
        Objects.requireNonNull(func);
        final ResourceLocation key = ResourceLocation.fromNamespaceAndPath(namespace, name);

        DeferredHolder<T> ret = createHolder(this.registryKey, key);

        Supplier<? extends T> supplier = () -> (T) func.apply(key);
        if (entries.putIfAbsent(ret, supplier) != null) {
            throw new IllegalArgumentException("Duplicate registration " + name);
        }

        return (DeferredHolder<R>) ret;
    }

    protected <I extends T> DeferredHolder<T> createHolder(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation key) {
        return DeferredHolder.create(registryKey, key);
    }

    public void register() {
        if (this.registeredEventBus) {
            throw new IllegalStateException("Cannot register a deferred register twice!");
        }
        registeredEventBus = true;
        IPlatformDeferredRegister.INSTANCE.registerAllEntries(registryKey, entries, () -> seenRegisterEvent = true);
    }

    @NotNull
    @Override
    public Iterator<DeferredHolder<T>> iterator() {
        return entriesView.iterator();
    }
}
