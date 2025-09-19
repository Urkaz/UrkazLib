/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.registry.registries;

import com.urkaz.urkazlib.platform.registry.registries.IPlatformDeferredRegister;
import com.urkaz.urkazlib.registry.registries.DeferredHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class PlatformDeferredRegisterImpl implements IPlatformDeferredRegister {

    @Override
    public <T> void registerAllEntries(ResourceKey<? extends Registry<T>> registryKey, Map<DeferredHolder<T>, Supplier<? extends T>> entries, Runnable completedEvent) {

        Iterator<Map.Entry<DeferredHolder<T>, Supplier<? extends T>>> var3 = entries.entrySet().iterator();
        while (var3.hasNext()) {
            Map.Entry<DeferredHolder<T>, Supplier<? extends T>> e = var3.next();

            // Get registry from key
            Registry<T> blR = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(registryKey.location());
            // Register
            Supplier<? extends T> a = e.getValue();
            Registry.register(blR, e.getKey().getId(), a.get());
        }

        completedEvent.run();
    }
}
