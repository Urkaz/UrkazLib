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

package com.urkaz.urkazlib.platform.core.fluid;

import com.urkaz.urkazlib.api.ServiceUtil;
import com.urkaz.urkazlib.core.fluid.IFluidProperties;
import com.urkaz.urkazlib.core.fluid.ModdedFlowingFluid;
import com.urkaz.urkazlib.lib.client.CustomFogData;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector4f;

import java.util.IdentityHashMap;
import java.util.Map;

@ApiStatus.Internal
public interface IFluidRegisterHelper {

    Map<Fluid, IFluidProperties> FLUID_PROPERTIES_MAP = new IdentityHashMap<>();

    default void register(ModdedFlowingFluid fluid, IFluidProperties properties) {
        FLUID_PROPERTIES_MAP.put(fluid, properties);
    }

    default CustomFogData fogData(Fluid fluid, Camera camera, float renderDistance, float partialTick) {
        IFluidProperties properties = FLUID_PROPERTIES_MAP.get(fluid);
        if (properties != null) {
            return properties.fogData(camera, renderDistance, partialTick);
        }
        return null;
    }

    default boolean modifyFogData(Fluid fluid) {
        IFluidProperties properties = FLUID_PROPERTIES_MAP.get(fluid);
        if (properties != null) {
            return properties.modifyFogData();
        }
        return false;
    }

    default Vector4f fogColor(Fluid fluid, Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount) {
        IFluidProperties properties = FLUID_PROPERTIES_MAP.get(fluid);
        if (properties != null) {
            return properties.fogColor(camera, partialTick, level, renderDistance, darkenWorldAmount);
        }
        return new Vector4f();
    }

    default boolean modifyFogColor(Fluid fluid) {
        IFluidProperties properties = FLUID_PROPERTIES_MAP.get(fluid);
        if (properties != null) {
            return properties.modifyFogColor();
        }
        return false;
    }

    void registerClientProperties();

    IFluidRegisterHelper INSTANCE = ServiceUtil.findService(IFluidRegisterHelper.class, null);
}
