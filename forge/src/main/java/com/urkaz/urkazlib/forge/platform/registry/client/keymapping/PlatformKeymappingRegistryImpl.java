/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge.platform.registry.client.keymapping;

import com.llamalad7.mixinextras.lib.apache.commons.ArrayUtils;
import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.forge.platform.hooks.EventBusHooks;
import com.urkaz.urkazlib.platform.registry.client.keymapping.IPlatformKeymappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

import java.util.ArrayList;
import java.util.List;

public class PlatformKeymappingRegistryImpl implements IPlatformKeymappingRegistry {

    private static final List<KeyMapping> MAPPINGS = new ArrayList<>();
    private static boolean eventCalled = false;

    static {
        EventBusHooks.whenAvailable(UrkazLib.MOD_ID, busGroup -> {
            RegisterKeyMappingsEvent.getBus(busGroup).addListener(PlatformKeymappingRegistryImpl::onRegisterKeyMappings);
        });
    }

    public void register(KeyMapping keyMapping) {
        if (eventCalled) {
            Options options = Minecraft.getInstance().options;
            options.keyMappings = ArrayUtils.add(options.keyMappings, keyMapping);
            UrkazLib.LOGGER.warn("Key mapping %s registered after event".formatted(keyMapping.getName()), new RuntimeException());
        } else {
            MAPPINGS.add(keyMapping);
        }
    }

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        MAPPINGS.forEach(event::register);
        eventCalled = true;
    }
}