/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform.network;

import com.urkaz.urkazlib.api.ServiceUtil;
import com.urkaz.urkazlib.network.NetworkPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IPlatformNetworkManager {

    void sendToServer(NetworkPacket packet);
    void sendToPlayer(Player player, NetworkPacket packet);
    void sendToNearPlayers(Level level, BlockPos pos, NetworkPacket packet);
    void sendToAllPlayers(Level level, NetworkPacket packet);
    void sendToTracking(Entity e, NetworkPacket packet);

    <P extends NetworkPacket> void registerS2CType(CustomPacketPayload.Type<P> type, StreamCodec<RegistryFriendlyByteBuf, P> codec);
    <P extends NetworkPacket> void registerC2S(CustomPacketPayload.Type<P> type, StreamCodec<RegistryFriendlyByteBuf, P> codec);
    <P extends NetworkPacket> void registerS2C(CustomPacketPayload.Type<P> type, StreamCodec<RegistryFriendlyByteBuf, P> codec);

    IPlatformNetworkManager INSTANCE = ServiceUtil.findService(IPlatformNetworkManager.class, null);
}

