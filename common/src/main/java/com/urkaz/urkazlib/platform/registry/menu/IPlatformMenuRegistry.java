/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform.registry.menu;

import com.urkaz.urkazlib.api.ServiceUtil;
import com.urkaz.urkazlib.registry.menu.ExtraDataMenuProvider;
import com.urkaz.urkazlib.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IPlatformMenuRegistry {

    void openExtraDataMenu(ServerPlayer player, ExtraDataMenuProvider provider);
    <T extends AbstractContainerMenu> MenuType<T> ofExtraData(MenuRegistry.ExtraDataMenuTypeFactory<T> factory);
    <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(MenuType<? extends H> type, MenuRegistry.ScreenFactory<H, S> factory);

    IPlatformMenuRegistry INSTANCE = ServiceUtil.findService(IPlatformMenuRegistry.class, null);
}
