/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge.platform.hooks;


import com.urkaz.urkazlib.forge.platform.EventBuses;
import net.minecraftforge.eventbus.api.bus.BusGroup;

import java.util.Optional;
import java.util.function.Consumer;

public final class EventBusHooks {
    private EventBusHooks() {
    }

    public static void whenAvailable(String modId, Consumer<BusGroup> busConsumer) {
        EventBuses.onRegistered(modId, busConsumer);
    }

    public static Optional<BusGroup> getModEventBus(String modId) {
        return EventBuses.getModEventBus(modId);
    }
}
