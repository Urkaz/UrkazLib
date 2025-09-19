/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge.mixin.common;

import com.urkaz.urkazlib.core.fluid.ModdedFlowingFluid;
import com.urkaz.urkazlib.neoforge.platform.core.fluid.FluidRegisterHelperImpl;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

@Mixin(ModdedFlowingFluid.class)
public abstract class ModdedFlowingFluidMixin extends FlowingFluid {

    @Override
    public @NotNull FluidType getFluidType() {
        ModdedFlowingFluid thisObject = (ModdedFlowingFluid)(Object)this;
        Supplier<FluidType> ft = FluidRegisterHelperImpl.FLUID_TYPE_MAP.get(thisObject.getProperties());
        return ft.get();
    }
}
