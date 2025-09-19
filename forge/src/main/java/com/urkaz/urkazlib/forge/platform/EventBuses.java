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
