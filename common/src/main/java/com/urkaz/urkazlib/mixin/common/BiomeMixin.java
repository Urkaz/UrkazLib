/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import com.urkaz.urkazlib.api.block.IUrkazLibFreezableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    @Inject(
            method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelReader;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
            ),
            cancellable = true
    )
    private void urkazlib$shouldFreezeCustomWater(CallbackInfoReturnable<Boolean> ci,
                                                  @Local(argsOnly = true) LevelReader levelReader,
                                                  @Local(argsOnly = true) BlockPos blockPos,
                                                  @Local(argsOnly = true) boolean mustBeAtEdge) {
        BlockState blockState = levelReader.getBlockState(blockPos);
        FluidState fluidState = levelReader.getFluidState(blockPos);
        if (fluidState.is(FluidTags.WATER) && blockState.getBlock() instanceof IUrkazLibFreezableBlock FreezableBlock) {
            if (FreezableBlock.urkazlib$freezableIfNotWarmEnoughToRain()) {
                if (!mustBeAtEdge) {
                    ci.setReturnValue(true);
                }

                boolean bl2 = levelReader.isWaterAt(blockPos.west()) && levelReader.isWaterAt(blockPos.east()) && levelReader.isWaterAt(blockPos.north()) && levelReader.isWaterAt(blockPos.south());
                if (!bl2) {
                    ci.setReturnValue(true);
                }
            }
        }
    }
}
