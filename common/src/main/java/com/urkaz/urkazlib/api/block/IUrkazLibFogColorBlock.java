/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.api.block;

import com.urkaz.urkazlib.lib.client.CustomFogData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;

public interface IUrkazLibFogColorBlock {

    default boolean urkazlib$overrideFogType() {
        return false;
    }

    default FogType urkazlib$getOverriddenFogType() {
        return FogType.NONE;
    }

    default boolean urkazlib$overrideFogColor() {
        return false;
    }

    default ColorRGBA urkazlib$getOverriddenFogColor(BlockState blockState, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount) {
        return new ColorRGBA(0xFFFFFFFF);
    }

    default CustomFogData urkazlib$overrideFogData() {
        return null;
    }
}
