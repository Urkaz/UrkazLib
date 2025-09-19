package com.urkaz.urkazlib.fabric.client;

import com.urkaz.urkazlib.platform.core.fluid.IFluidRegisterHelper;
import net.fabricmc.api.ClientModInitializer;

public class UrkazLibClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IFluidRegisterHelper.INSTANCE.registerClientProperties();
    }
}