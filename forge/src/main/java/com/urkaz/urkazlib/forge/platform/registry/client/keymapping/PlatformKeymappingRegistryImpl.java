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