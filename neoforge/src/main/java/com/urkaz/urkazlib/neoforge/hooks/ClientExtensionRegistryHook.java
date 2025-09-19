/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge.hooks;

import com.urkaz.urkazlib.UrkazLib;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@EventBusSubscriber(modid = UrkazLib.MOD_ID, value = Dist.CLIENT)
public class ClientExtensionRegistryHook {
    private static final List<Consumer<@Nullable RegisterClientExtensionsEvent>> CALLBACKS = new ArrayList<>();
    private static boolean eventCalled = false;

    public static void register(Consumer<@Nullable RegisterClientExtensionsEvent> callback) {
        if (ClientExtensionRegistryHook.eventCalled) {
            callback.accept(null);
        } else {
            CALLBACKS.add(callback);
        }
    }

    @SubscribeEvent
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {
        ClientExtensionRegistryHook.eventCalled = true;
        for (Consumer<@Nullable RegisterClientExtensionsEvent> callback : CALLBACKS) {
            callback.accept(event);
        }
    }
}