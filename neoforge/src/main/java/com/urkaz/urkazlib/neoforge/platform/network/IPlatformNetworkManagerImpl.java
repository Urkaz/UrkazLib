/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge.platform.network;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.neoforge.hooks.EventBusHooks;
import com.urkaz.urkazlib.network.NetworkPacket;
import com.urkaz.urkazlib.network.PacketContext;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class IPlatformNetworkManagerImpl implements IPlatformNetworkManager {
    @Override
    public void sendToServer(NetworkPacket packet) {
        ClientPacketDistributor.sendToServer(packet);
    }

    @Override
    public void sendToPlayer(Player player, NetworkPacket packet) {
        if (player instanceof ServerPlayer serverPlayer && !player.level().isClientSide) {
            PacketDistributor.sendToPlayer(serverPlayer, packet);
        }
    }

    @Override
    public void sendToNearPlayers(Level level, BlockPos pos, NetworkPacket packet) {
        if (!level.isClientSide) {
            PacketDistributor.sendToPlayersNear((ServerLevel) level, null, pos.getX(), pos.getY(), pos.getZ(), 64, packet);
        }
    }

    @Override
    public void sendToAllPlayers(Level level, NetworkPacket packet) {
        if (!level.isClientSide) {
            PacketDistributor.sendToAllPlayers(packet);
        }
    }

    @Override
    public void sendToTracking(Entity e, NetworkPacket packet) {
        if (!e.level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(e, packet);
        }
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CType(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        registerS2C(type, codec);
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerC2S(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        EventBusHooks.whenAvailable(UrkazLib.MOD_ID, bus -> {
            bus.<RegisterPayloadHandlersEvent>addListener(event -> {
                PayloadRegistrar registrar = event.registrar("1");
                registrar.playToServer(type,  (StreamCodec<FriendlyByteBuf, P>)codec, (packet, context) -> {
                    packet.receiveMessage(packet, createContext(context.player(), context, false));
                });
            });
        });
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2C(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        EventBusHooks.whenAvailable(UrkazLib.MOD_ID, bus -> {
            bus.<RegisterPayloadHandlersEvent>addListener(event -> {
                PayloadRegistrar registrar = event.registrar("1");
                registrar.playToClient(type,  (StreamCodec<FriendlyByteBuf, P>)codec, (packet, context) -> {
                    packet.receiveMessage(packet, createContext(context.player(), context, true));
                });
            });
        });
    }

    public PacketContext createContext(Player player, IPayloadContext taskQueue, boolean client) {
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
