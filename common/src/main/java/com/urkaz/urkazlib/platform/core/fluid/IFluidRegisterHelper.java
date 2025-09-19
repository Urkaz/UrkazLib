/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
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
