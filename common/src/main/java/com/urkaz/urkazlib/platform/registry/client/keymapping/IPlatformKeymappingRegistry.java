/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform.registry.client.keymapping;

import com.urkaz.urkazlib.api.ServiceUtil;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.ApiStatus;


@ApiStatus.Internal
public interface IPlatformKeymappingRegistry {

    void register(KeyMapping keyMapping);

    IPlatformKeymappingRegistry INSTANCE = ServiceUtil.findService(IPlatformKeymappingRegistry.class, null);
}