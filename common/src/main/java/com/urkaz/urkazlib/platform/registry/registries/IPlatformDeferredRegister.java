/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform.registry.registries;

import com.urkaz.urkazlib.api.ServiceUtil;
import com.urkaz.urkazlib.registry.registries.DeferredHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.function.Supplier;

@ApiStatus.Internal
public interface IPlatformDeferredRegister {

    <T> void registerAllEntries(ResourceKey<? extends Registry<T>> registryKey, Map<DeferredHolder<T>, Supplier<? extends T>> entries, Runnable completedEvent);

    IPlatformDeferredRegister INSTANCE = ServiceUtil.findService(IPlatformDeferredRegister.class, null);
}
