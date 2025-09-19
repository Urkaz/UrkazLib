/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge.platform.registry.menu;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.neoforge.hooks.EventBusHooks;
import com.urkaz.urkazlib.platform.registry.menu.IPlatformMenuRegistry;
import com.urkaz.urkazlib.registry.menu.ExtraDataMenuProvider;
import com.urkaz.urkazlib.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

public class PlatformMenuRegistryImpl implements IPlatformMenuRegistry {

    @Override
    public void openExtraDataMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        player.openMenu(provider, provider::saveExtraData);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> ofExtraData(MenuRegistry.ExtraDataMenuTypeFactory<T> factory) {
        return IMenuTypeExtension.create(factory::create);
    }

    @Override
    public <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(MenuType<? extends H> type, MenuRegistry.ScreenFactory<H, S> factory) {
        EventBusHooks.whenAvailable(UrkazLib.MOD_ID, bus -> {
            bus.addListener(RegisterMenuScreensEvent.class, event -> event.register(type, factory::create));
        });
    }
}
