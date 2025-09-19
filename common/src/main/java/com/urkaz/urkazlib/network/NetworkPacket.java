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

package com.urkaz.urkazlib.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface NetworkPacket extends CustomPacketPayload {
    /**
     * Handle the message after being received and decoded.<br>
     * Your packet should have its instance-values populated at this stage.<br>
     * This method is side-agnostic, so make sure you call out to client proxies as needed
     */
    <P extends NetworkPacket> void receiveMessage(P packet, PacketContext context);
}