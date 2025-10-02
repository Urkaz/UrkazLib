/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge;

import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.forge.platform.EventBuses;
import com.urkaz.urkazlib.forge.platform.network.IPlatformNetworkManagerImpl;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UrkazLib.MOD_ID)
public class UrkazLibForge {
    public UrkazLibForge(FMLJavaModLoadingContext context) {
        EventBuses.registerModEventBus(UrkazLib.MOD_ID, context.getModBusGroup());
        IPlatformNetworkManagerImpl.init();
        UrkazLib.init();
    }
}
