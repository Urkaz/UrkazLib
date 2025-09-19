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

package com.urkaz.urkazlib.forge.platform.network;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.network.NetworkPacket;
import com.urkaz.urkazlib.network.PacketContext;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.payload.PayloadProtocol;

public class IPlatformNetworkManagerImpl implements IPlatformNetworkManager {

    private static final int PROTOCOL_VERSION = 1;
    public static PayloadProtocol<RegistryFriendlyByteBuf, CustomPacketPayload> NETWORK_CHANNEL_BUILDER = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(UrkazLib.MOD_ID, "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .acceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .optional().payloadChannel().play();
    public static Channel<CustomPacketPayload> CHANNEL;

    public static void init() {
        CHANNEL = NETWORK_CHANNEL_BUILDER.bidirectional().build();
    }

    @Override
    public void sendToServer(NetworkPacket packet) {
        CHANNEL.send(packet, PacketDistributor.SERVER.noArg());
    }

    @Override
    public void sendToPlayer(Player player, NetworkPacket packet) {
        if (player instanceof ServerPlayer serverPlayer && !player.level().isClientSide) {
            CHANNEL.send(packet, PacketDistributor.PLAYER.with(serverPlayer));
        }
    }

    @Override
    public void sendToNearPlayers(Level level, BlockPos pos, NetworkPacket packet) {
        if (!level.isClientSide) {
            CHANNEL.send(packet, PacketDistributor.NEAR.with(
                    new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 64, level.dimension())
            ));
        }
    }

    @Override
    public void sendToAllPlayers(Level level, BlockPos pos, NetworkPacket packet) {
        if (!level.isClientSide) {
            CHANNEL.send(packet, PacketDistributor.ALL.noArg());
        }
    }

    @Override
    public void sendToTracking(Entity e, NetworkPacket packet) {
        if (!e.level().isClientSide) {
            CHANNEL.send(packet, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(e));
        }
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CType(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        registerS2C(type, codec);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerC2S(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        NETWORK_CHANNEL_BUILDER.serverbound().add(type, (StreamCodec<RegistryFriendlyByteBuf, P>) codec, (packet, context) -> {
            packet.receiveMessage(packet, createContext(context.getSender(), context, false));
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2C(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        NETWORK_CHANNEL_BUILDER.clientbound().add(type, (StreamCodec<RegistryFriendlyByteBuf, P>) codec, (packet, context) -> {
            packet.receiveMessage(packet, createContext(context.getSender(), context, true));
        });
    }

    public PacketContext createContext(Player player, CustomPayloadEvent.Context taskQueue, boolean client) {
        return new PacketContext() {
            @Override
            public Player getPlayer() {
                return isClient() ? getClientPlayer() : player;
            }

            @Override
            public void queue(Runnable runnable) {
                taskQueue.enqueueWork(runnable);
            }

            @Override
            public boolean isClient() {
                return client;
            }

            @Override
            public RegistryAccess registryAccess() {
                return isClient() ? getClientRegistryAccess() : player.registryAccess();
            }
        };
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static RegistryAccess getClientRegistryAccess() {
        if (Minecraft.getInstance().level != null) {
            return Minecraft.getInstance().level.registryAccess();
        } else if (Minecraft.getInstance().getConnection() != null) {
            return Minecraft.getInstance().getConnection().registryAccess();
        } else if (Minecraft.getInstance().gameMode != null) {
            // Sometimes the packet is sent way too fast and is between the connection and the level, better safe than sorry
            return Minecraft.getInstance().gameMode.connection.registryAccess();
        }

        // Fail-safe
        return RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    }
}
