/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.client;

import com.urkaz.urkazlib.platform.core.fluid.IFluidRegisterHelper;
import net.fabricmc.api.ClientModInitializer;

public class UrkazLibClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IFluidRegisterHelper.INSTANCE.registerClientProperties();
    }
}