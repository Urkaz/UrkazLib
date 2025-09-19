/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.registry.menu;

import com.urkaz.urkazlib.platform.registry.menu.IPlatformMenuRegistry;
import com.urkaz.urkazlib.registry.menu.ExtraDataMenuProvider;
import com.urkaz.urkazlib.registry.menu.MenuRegistry;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class PlatformMenuRegistryImpl implements IPlatformMenuRegistry {

    @Override
    public void openExtraDataMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        player.openMenu(new ExtendedScreenHandlerFactory<byte[]>() {
            @Override
            public byte[] getScreenOpeningData(ServerPlayer player) {
                FriendlyByteBuf buf = PacketByteBufs.create();
                provider.saveExtraData(buf);
                byte[] bytes = ByteBufUtil.getBytes(buf);
                buf.release();
                return bytes;
            }

            @Override
            public @NotNull Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
                return provider.createMenu(i, inventory, player);
            }
        });
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> ofExtraData(MenuRegistry.ExtraDataMenuTypeFactory<T> factory) {
        return new ExtendedScreenHandlerType<>((syncId, inventory, data) -> {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
            T menu = factory.create(syncId, inventory, buf);
            buf.release();
            return menu;
        }, ByteBufCodecs.BYTE_ARRAY.mapStream(Function.identity()));
    }

    @Override
    public <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(MenuType<? extends H> type, MenuRegistry.ScreenFactory<H, S> factory) {
        MenuScreens.register(type, factory::create);
    }
}
