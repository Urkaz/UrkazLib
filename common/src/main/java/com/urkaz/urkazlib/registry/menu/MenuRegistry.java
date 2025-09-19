/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.registry.menu;

import com.urkaz.urkazlib.platform.registry.menu.IPlatformMenuRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class MenuRegistry {
    private MenuRegistry() {
    }

    public static void openExtraDataMenu(ServerPlayer player, MenuProvider provider, Consumer<FriendlyByteBuf> bufWriter) {
        IPlatformMenuRegistry.INSTANCE.openExtraDataMenu(player, new ExtraDataMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buf) {
                bufWriter.accept(buf);
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

    public static void openExtraDataMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        IPlatformMenuRegistry.INSTANCE.openExtraDataMenu(player, provider);
    }

    public static void openMenu(ServerPlayer player, MenuProvider provider) {
        player.openMenu(provider);
    }

    public static <T extends AbstractContainerMenu> MenuType<T> ofExtraData(ExtraDataMenuTypeFactory<T> factory) {
        return IPlatformMenuRegistry.INSTANCE.ofExtraData(factory);
    }

    public static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(MenuType<? extends H> type, ScreenFactory<H, S> factory) {
        IPlatformMenuRegistry.INSTANCE.registerScreenFactory(type, factory);
    }

    @FunctionalInterface
    public interface ScreenFactory<H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> {
        /**
         * Creates a new {@link S} that extends {@link Screen}
         *
         * @param containerMenu The {@link AbstractContainerMenu} that controls the game logic for the screen
         * @param inventory     The {@link Inventory} for the screen
         * @param component     The {@link Component} for the screen
         * @return A new {@link S} that extends {@link Screen}
         */
        S create(H containerMenu, Inventory inventory, Component component);
    }

    @FunctionalInterface
    public interface ExtraDataMenuTypeFactory<T extends AbstractContainerMenu> {
        /**
         * Creates a new {@link T} that extends {@link AbstractContainerMenu}.
         *
         * @param id        The id for the menu
         * @param inventory The {@link Inventory} for the menu
         * @param buf       The {@link FriendlyByteBuf} for the menu to provide extra data
         * @return A new {@link T} that extends {@link AbstractContainerMenu}
         */
        T create(int id, Inventory inventory, FriendlyByteBuf buf);
    }
}
