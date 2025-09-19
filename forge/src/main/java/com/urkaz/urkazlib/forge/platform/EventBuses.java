/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge.platform;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraftforge.eventbus.api.bus.BusGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public final class EventBuses {
    private EventBuses() {
    }

    private static final Map<String, BusGroup> EVENT_BUS_MAP = Collections.synchronizedMap(new HashMap<>());
    private static final Multimap<String, Consumer<BusGroup>> ON_REGISTERED = Multimaps.synchronizedMultimap(LinkedListMultimap.create());

    public static void registerModEventBus(String modId, BusGroup bus) {
        if (EVENT_BUS_MAP.putIfAbsent(modId, bus) != null) {
            throw new IllegalStateException("Can't register event bus for mod '" + modId + "' because it was previously registered!");
        }

        for (Consumer<BusGroup> consumer : ON_REGISTERED.get(modId)) {
            consumer.accept(bus);
        }
    }

    public static void onRegistered(String modId, Consumer<BusGroup> busConsumer) {
        if (EVENT_BUS_MAP.containsKey(modId)) {
            busConsumer.accept(EVENT_BUS_MAP.get(modId));
        } else {
            ON_REGISTERED.put(modId, busConsumer);
        }
    }

    public static Optional<BusGroup> getModEventBus(String modId) {
        return Optional.ofNullable(EVENT_BUS_MAP.get(modId));
    }
}
