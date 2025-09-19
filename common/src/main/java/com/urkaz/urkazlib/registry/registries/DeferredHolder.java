/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.registry.registries;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DeferredHolder<T> implements Holder<T>, Supplier<T> {

    protected final ResourceKey<T> key;
    @Nullable
    private Holder<T> holder = null;

    public DeferredHolder(ResourceKey<T> key) {
        this.key = Objects.requireNonNull(key);
        this.bind(false);
    }

    public static <R, T extends R> DeferredHolder<T> create(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation valueName) {
        return create(ResourceKey.create(registryKey, valueName));
    }

    public static <R, T extends R> DeferredHolder<T> create(ResourceLocation registryName, ResourceLocation valueName) {
        return create(ResourceKey.createRegistryKey(registryName), valueName);
    }

    public static <R, T extends R> DeferredHolder<T> create(ResourceKey<T> key) {
        return new DeferredHolder<>(key);
    }

    @Override
    public @NotNull T value() {
        bind(true);
        if (this.holder == null) {
            throw new NullPointerException("Trying to access unbound value: " + this.key);
        }

        return this.holder.value();
    }

    @Override
    public T get() {
        return this.value();
    }

    public Optional<T> asOptional() {
        return isBound() ? Optional.of(value()) : Optional.empty();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected Registry<T> getRegistry() {
        return (Registry<T>) BuiltInRegistries.REGISTRY.getValue(this.key.registry());
    }

    protected final void bind(boolean throwOnMissingRegistry) {
        if (this.holder != null) return;

        Registry<T> registry = getRegistry();
        if (registry != null) {
            this.holder = registry.get(this.key).orElse(null);
        } else if (throwOnMissingRegistry) {
            throw new IllegalStateException("Registry not present for " + this + ": " + this.key.registry());
        }
    }

    public ResourceLocation getId() {
        return this.key.location();
    }

    public ResourceKey<T> getKey() {
        return this.key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Holder<?> h && h.kind() == Kind.REFERENCE && h.unwrapKey().orElse(null) == this.key;
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "DeferredHolder{%s}", this.key);
    }

    @Override
    public boolean isBound() {
        bind(false);
        return this.holder != null && this.holder.isBound();
    }

    @Override
    public boolean is(ResourceLocation id) {
        return id.equals(this.key.location());
    }

    @Override
    public boolean is(@NotNull ResourceKey<T> key) {
        return key == this.key;
    }

    @Override
    public boolean is(Predicate<ResourceKey<T>> filter) {
        return filter.test(this.key);
    }

    @Override
    public boolean is(@NotNull TagKey<T> tag) {
        bind(false);
        return this.holder != null && this.holder.is(tag);
    }

    @Override
    @Deprecated
    public boolean is(@NotNull Holder<T> holder) {
        bind(false);
        return this.holder != null && this.holder.is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<T>> tags() {
        bind(false);
        return this.holder != null ? this.holder.tags() : Stream.empty();
    }

    @Override
    public @NotNull Either<ResourceKey<T>, T> unwrap() {
        // Holder.Reference always returns the key, do the same here.
        return Either.left(this.key);
    }
    
    @Override
    public @NotNull Optional<ResourceKey<T>> unwrapKey() {
        return Optional.of(this.key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(@NotNull HolderOwner<T> owner) {
        bind(false);
        return this.holder != null && this.holder.canSerializeIn(owner);
    }
}
