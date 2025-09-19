/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.urkaz.urkazlib.api.block.IUrkazLibDustColorBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingDustParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingDustParticle.Provider.class)
public class FallingDustParticleMixin {

    @ModifyVariable(
            method = "createParticle(Lnet/minecraft/core/particles/BlockParticleOption;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            )
    )
    private int urkazlib$createParticle$useCustomDustColor(int j,
                                                           @Local BlockState blockState,
                                                           @Local BlockPos blockPos,
                                                           @Local(argsOnly = true) ClientLevel clientLevel) {
        if (blockState.getBlock() instanceof IUrkazLibDustColorBlock) {
            ColorRGBA color = ((IUrkazLibDustColorBlock)blockState.getBlock()).urkazlib$getDustColor(blockState, clientLevel, blockPos);
            j = color.rgba() >> 8; //Remove alpha channel
        }
        return j;
    }
}
