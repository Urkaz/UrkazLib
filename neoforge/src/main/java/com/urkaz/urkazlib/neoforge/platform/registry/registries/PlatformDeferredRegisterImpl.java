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

package com.urkaz.urkazlib.neoforge.platform.registry.registries;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.neoforge.hooks.EventBusHooks;
import com.urkaz.urkazlib.platform.registry.registries.IPlatformDeferredRegister;
import com.urkaz.urkazlib.registry.registries.DeferredHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class PlatformDeferredRegisterImpl implements IPlatformDeferredRegister {

    @Override
    public <T> void registerAllEntries(ResourceKey<? extends Registry<T>> registryKey, Map<DeferredHolder<T>, Supplier<? extends T>> entries, Runnable completedEvent) {
        EventBusHooks.whenAvailable(UrkazLib.MOD_ID, bus -> {
            bus.addListener(RegisterEvent.class, event -> {

                Iterator<Map.Entry<DeferredHolder<T>, Supplier<? extends T>>> var3 = entries.entrySet().iterator();
                while (var3.hasNext()) {
                    Map.Entry<DeferredHolder<T>, Supplier<? extends T>> e = var3.next();
                    event.register(registryKey, e.getKey().getId(), () -> e.getValue().get());
                }
            });
        });
    }
}
