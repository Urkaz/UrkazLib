/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge.platform.registry.menu;

import com.urkaz.urkazlib.platform.registry.menu.IPlatformMenuRegistry;
import com.urkaz.urkazlib.registry.menu.ExtraDataMenuProvider;
import com.urkaz.urkazlib.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class PlatformMenuRegistryImpl implements IPlatformMenuRegistry {

    @Override
    public void openExtraDataMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        player.openMenu(provider, provider::saveExtraData);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> ofExtraData(MenuRegistry.ExtraDataMenuTypeFactory<T> factory) {
        return IForgeMenuType.create(factory::create);
    }

    @Override
    public <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(MenuType<? extends H> type, MenuRegistry.ScreenFactory<H, S> factory) {
        MenuScreens.register(type, factory::create);
    }
}
