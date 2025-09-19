/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.urkaz.urkazlib.api.block.IUrkazLibFreezableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @WrapOperation(
            method = "tickPrecipitation",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z")
    )
    private boolean urkazlib$setIceBlockIfShouldFreeze(ServerLevel instance,
                                                       BlockPos blockPos,
                                                       BlockState iceBlockState,
                                                       Operation<Boolean> original) {
        BlockState blockState = instance.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IUrkazLibFreezableBlock FreezableBlock) {
            return instance.setBlockAndUpdate(blockPos, FreezableBlock.urkazlib$getFrozenBlock().defaultBlockState());
        }
        else {
            return original.call(instance, blockPos, iceBlockState);
        }
    }
}
