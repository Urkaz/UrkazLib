package com.urkaz.urkazlib.fabric.platform.network;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.network.NetworkPacket;
import com.urkaz.urkazlib.platform.network.IPlatformNetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.urkaz.urkazlib.fabric.platform.network.PlatformNetworkManagerImpl.createContext;

@Environment(EnvType.CLIENT)
public class PlatformNetworkManagerClientImpl implements IPlatformNetworkManager {
    @Override
    public void sendToServer(NetworkPacket packet) {
        ClientPlayNetworking.send(packet);
    }

    @Override
    public void sendToPlayer(Player player, NetworkPacket packet) {

    }

    @Override
    public void sendToNearPlayers(Level level, BlockPos pos, NetworkPacket packet) {

    }

    @Override
    public void sendToAllPlayers(Level level, BlockPos pos, NetworkPacket packet) {

    }

    @Override
    public void sendToTracking(Entity e, NetworkPacket packet) {

    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerS2CType(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {
        PayloadTypeRegistry.playS2C().register(type, (StreamCodec<FriendlyByteBuf, P>) codec);
    }

    @Override
    public <B extends FriendlyByteBuf, P extends NetworkPacket> void registerC2S(CustomPacketPayload.Type<P> type, StreamCodec<B, P> codec) {

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
