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

import com.urkaz.urkazlib.platform.Platform;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class NetworkManager {

    public static <B extends FriendlyByteBuf, P extends NetworkPacket> void registerPacket(NetworkManager.Side side, CustomPacketPayload.Type<P> id, StreamCodec<B, P> codec) {
        if (side == Side.C2S) {
            registerReceiver(NetworkManager.c2s(), id, codec);
        }
        else {
            if (Platform.isClient()) {
                registerReceiver(NetworkManager.s2c(), id, codec);
            } else {
                registerS2CPayloadType(id, codec);
            }
        }
    }

    public static <B extends FriendlyByteBuf, P extends NetworkPacket> void registerReceiver(NetworkManager.Side side, CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        Objects.requireNonNull(type, "Cannot register receiver with a null type!");
        if (side == NetworkManager.Side.C2S) {
            registerC2SReceiver(type, codec);
        } else if (side == NetworkManager.Side.S2C) {
            registerS2CReceiver(type, codec);
        }
    }

    private static <B extends FriendlyByteBuf, P extends NetworkPacket> void registerC2SReceiver(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        IPlatformNetworkManager.INSTANCE.registerC2S(type, codec);
    }

    private static <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CReceiver(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        IPlatformNetworkManager.INSTANCE.registerS2C(type, codec);
    }

    public static <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CPayloadType(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        Objects.requireNonNull(type, "Cannot register a null type!");
        IPlatformNetworkManager.INSTANCE.registerS2CType(type, codec);
    }

    public static void sendToServer(NetworkPacket packet) {
        IPlatformNetworkManager.INSTANCE.sendToServer(packet);
    }

    public static void sendToPlayer(Player player, NetworkPacket packet) {
        IPlatformNetworkManager.INSTANCE.sendToPlayer(player, packet);
    }

    public static void sendToNear(Level level, BlockPos pos, NetworkPacket packet) {
        IPlatformNetworkManager.INSTANCE.sendToNearPlayers(level, pos, packet);
    }

    public static void sendToAll(Level level, BlockPos pos, NetworkPacket packet) {
        IPlatformNetworkManager.INSTANCE.sendToAllPlayers(level, pos, packet);
    }

    public static void sendToTracking(Entity e, NetworkPacket packet) {
        IPlatformNetworkManager.INSTANCE.sendToTracking(e, packet);
    }

    public static Side s2c() {
        return Side.S2C;
    }

    public static Side c2s() {
        return Side.C2S;
    }

    public static Side serverToClient() {
        return Side.S2C;
    }

    public static Side clientToServer() {
        return Side.C2S;
    }

    public enum Side {
        S2C,
        C2S
    }
}
