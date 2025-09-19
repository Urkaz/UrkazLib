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

package com.urkaz.urkazlib.mixin.client;

import com.urkaz.urkazlib.api.block.IUrkazLibFogColorBlock;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {

    @Shadow
    private boolean initialized;

    @Shadow
    private BlockGetter level;

    @Shadow
    @Final
    private BlockPos.MutableBlockPos blockPosition;

    @Inject(
            method = "getFluidInCamera",
            at = @At("HEAD"),
            cancellable = true
    )
    private void urkazlib$getFluidInCamera$updateCustomFogColorAndType(CallbackInfoReturnable<FogType> ci) {
        if (this.initialized) {

            BlockState blockState = level.getBlockState(blockPosition);
            if (blockState.getBlock() instanceof IUrkazLibFogColorBlock customFogColorBlock) {
                //Return overridden fog type
                if (customFogColorBlock.urkazlib$overrideFogType())
                    ci.setReturnValue(customFogColorBlock.urkazlib$getOverriddenFogType());
            }
        }
    }
}
