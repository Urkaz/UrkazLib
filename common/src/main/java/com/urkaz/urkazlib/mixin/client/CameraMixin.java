/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
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
