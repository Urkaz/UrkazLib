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
