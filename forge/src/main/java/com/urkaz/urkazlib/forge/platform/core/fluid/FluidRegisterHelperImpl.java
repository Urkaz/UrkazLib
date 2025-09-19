/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge.platform.core.fluid;

import com.google.common.base.Suppliers;
import com.urkaz.urkazlib.core.fluid.IFluidProperties;
import com.urkaz.urkazlib.core.fluid.ModdedFlowingFluid;
import com.urkaz.urkazlib.platform.core.fluid.IFluidRegisterHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FluidRegisterHelperImpl implements IFluidRegisterHelper {

    public static final Map<IFluidProperties, Supplier<FluidType>> FLUID_TYPE_MAP = new IdentityHashMap<>();

    @Override
    public void register(ModdedFlowingFluid fluid, IFluidProperties properties) {
        IFluidRegisterHelper.super.register(fluid, properties);

        FLUID_TYPE_MAP.computeIfAbsent(properties, props -> {
            return Suppliers.memoize(() -> new ModdedFluidType(FluidType.Properties.create(), props, fluid));
        });
    }

    @Override
    public void registerClientProperties() {

    }
}
