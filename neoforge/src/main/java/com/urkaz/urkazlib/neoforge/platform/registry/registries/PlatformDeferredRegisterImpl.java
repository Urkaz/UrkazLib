/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge.platform.registry.registries;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.neoforge.hooks.EventBusHooks;
import com.urkaz.urkazlib.platform.registry.registries.IPlatformDeferredRegister;
import com.urkaz.urkazlib.registry.registries.DeferredHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.function.Supplier;

public class PlatformDeferredRegisterImpl implements IPlatformDeferredRegister {

    @Override
    public <T> void registerAllEntries(ResourceKey<? extends Registry<T>> registryKey, Map<DeferredHolder<T>, Supplier<? extends T>> entries, Runnable completedEvent) {
        EventBusHooks.whenAvailable(UrkazLib.MOD_ID, bus -> {
            bus.addListener(RegisterEvent.class, event -> {

                for (Map.Entry<DeferredHolder<T>, Supplier<? extends T>> e : entries.entrySet()) {
                    event.register(registryKey, e.getKey().getId(), () -> e.getValue().get());
                }
            });
        });
    }
}
