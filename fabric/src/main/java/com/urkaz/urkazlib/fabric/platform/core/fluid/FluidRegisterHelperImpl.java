/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.core.fluid;

import com.urkaz.urkazlib.core.fluid.IFluidProperties;
import com.urkaz.urkazlib.core.fluid.ModdedFlowingFluid;
import com.urkaz.urkazlib.platform.core.fluid.IFluidRegisterHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.world.level.material.Fluid;

import java.util.Map;

public class FluidRegisterHelperImpl implements IFluidRegisterHelper {

    @Override
    public void register(ModdedFlowingFluid fluid, IFluidProperties properties) {
        IFluidRegisterHelper.super.register(fluid, properties);

        FluidVariantAttributes.register(fluid, new ModdedFluidAttributesHandler(properties));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void registerClientProperties() {

        for (Map.Entry<Fluid, IFluidProperties> entry : FLUID_PROPERTIES_MAP.entrySet()) {
            Fluid fluid = entry.getKey();
            IFluidProperties properties = entry.getValue();

            FluidVariantRendering.register(fluid, new ModdedFluidRenderingHandler(properties));
            FluidRenderHandlerRegistry.INSTANCE.register(fluid, new ModdedFluidRenderingHandler(properties));
        }
    }
}
