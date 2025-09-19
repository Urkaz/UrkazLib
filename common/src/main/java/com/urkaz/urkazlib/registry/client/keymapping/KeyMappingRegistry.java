/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.registry.client.keymapping;

import com.urkaz.urkazlib.platform.registry.client.keymapping.IPlatformKeymappingRegistry;
import net.minecraft.client.KeyMapping;

public final class KeyMappingRegistry {
    public static void register(KeyMapping keyMapping) {
        IPlatformKeymappingRegistry.INSTANCE.register(keyMapping);
    }
}
