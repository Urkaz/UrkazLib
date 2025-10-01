/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.network;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.network.NetworkPacket;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;

import static com.urkaz.urkazlib.fabric.platform.network.PlatformNetworkManagerImpl.createContext;

@Environment(EnvType.CLIENT)
public class PlatformNetworkManagerClientImpl implements IPlatformNetworkManager {
    @Override
    public void sendToServer(NetworkPacket packet) {
        ClientPlayNetworking.send(packet);
    }

    @Override
    public void sendToPlayer(Player player, NetworkPacket packet) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, packet);
        }
    }

    @Override
    public void sendToNearPlayers(Level level, BlockPos pos, NetworkPacket packet) {
        var pkt = ServerPlayNetworking.createS2CPacket(packet);
        for (var player : PlayerLookup.tracking((ServerLevel) level, pos)) {
            if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64) {
                player.connection.send(pkt);
            }
        }
    }

    @Override
    public void sendToAllPlayers(Level level, NetworkPacket packet) {
        var pkt = ServerPlayNetworking.createS2CPacket(packet);
        for (var player : PlayerLookup.all(Objects.requireNonNull(level.getServer()))) {
            player.connection.send(pkt);
        }
    }

    @Override
    public void sendToTracking(Entity e, NetworkPacket packet) {
        var pkt = ServerPlayNetworking.createS2CPacket(packet);
        PlayerLookup.tracking(e).forEach(p -> p.connection.send(pkt));
        if (e instanceof ServerPlayer) {
            ((ServerPlayer) e).connection.send(pkt);
        }
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CType(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        PayloadTypeRegistry.playS2C().register(type, (StreamCodec<FriendlyByteBuf, P>) codec);
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerC2S(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        UrkazLib.LOGGER.info("Registering C2S receiver with id {}", type.id());
        PayloadTypeRegistry.playC2S().register(type, (StreamCodec<FriendlyByteBuf, P>) codec);
        ServerPlayNetworking.registerGlobalReceiver(type, (packet, context) -> {
            packet.receiveMessage(packet, createContext(context.player(), context.player().getServer(), false));
        });
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2C(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        UrkazLib.LOGGER.info("Registering S2C receiver with id {}", type.id());
        PayloadTypeRegistry.playS2C().register(type, (StreamCodec<FriendlyByteBuf, P>) codec);
        ClientPlayNetworking.registerGlobalReceiver(type, (packet, context) -> {
            packet.receiveMessage(packet, createContext(context.player(), context.client(), true));
        });
    }
}
