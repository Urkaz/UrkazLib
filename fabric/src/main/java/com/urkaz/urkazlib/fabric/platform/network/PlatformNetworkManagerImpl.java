/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.network;

import com.urkaz.urkazlib.network.NetworkPacket;
import com.urkaz.urkazlib.network.PacketContext;
import com.urkaz.urkazlib.platform.Platform;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlatformNetworkManagerImpl implements IPlatformNetworkManager {

    private final IPlatformNetworkManager delegate;

    public PlatformNetworkManagerImpl() {
        if (Platform.isClient()) {
            try {
                delegate = (IPlatformNetworkManager) Class.forName("com.urkaz.urkazlib.fabric.platform.network.PlatformNetworkManagerClientImpl").getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            delegate = new PlatformNetworkManagerServerImpl();
        }
    }

    @Override
    public void sendToServer(NetworkPacket packet) {
        delegate.sendToServer(packet);
    }

    @Override
    public void sendToPlayer(Player player, NetworkPacket packet) {
        delegate.sendToPlayer(player, packet);
    }

    @Override
    public void sendToNearPlayers(Level level, BlockPos pos, NetworkPacket packet) {
        delegate.sendToNearPlayers(level, pos, packet);
    }

    @Override
    public void sendToAllPlayers(Level level, BlockPos pos, NetworkPacket packet) {
        delegate.sendToAllPlayers(level, pos, packet);
    }

    @Override
    public void sendToTracking(Entity e, NetworkPacket packet) {
        delegate.sendToTracking(e, packet);
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CType(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        delegate.registerS2CType(type, codec);
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerC2S(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        delegate.registerC2S(type, codec);
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2C(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        delegate.registerS2C(type, codec);
    }

    static PacketContext createContext(Player player, BlockableEventLoop<?> taskQueue, boolean client) {
        return new PacketContext() {
            @Override
            public Player getPlayer() {
                return player;
            }

            @Override
            public void queue(Runnable runnable) {
                taskQueue.execute(runnable);
            }

            @Override
            public boolean isClient() {
                return client;
            }

            @Override
            public RegistryAccess registryAccess() {
                return player.registryAccess();
            }
        };
    }
}
