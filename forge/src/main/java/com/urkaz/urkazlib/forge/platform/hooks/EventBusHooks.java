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
