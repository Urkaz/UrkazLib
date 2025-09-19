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

package com.urkaz.urkazlib.neoforge.mixin.common;

import com.urkaz.urkazlib.UrkazLib;
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
