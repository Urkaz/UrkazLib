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
