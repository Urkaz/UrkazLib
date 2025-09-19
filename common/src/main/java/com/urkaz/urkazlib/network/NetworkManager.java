/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.network;

import com.urkaz.urkazlib.platform.Platform;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
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
