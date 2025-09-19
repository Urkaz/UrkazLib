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
