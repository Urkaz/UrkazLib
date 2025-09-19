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
