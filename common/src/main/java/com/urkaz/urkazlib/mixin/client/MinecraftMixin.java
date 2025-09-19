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

import com.urkaz.urkazlib.api.item.IUrkazLibItemExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void urkazlib$startAttack$returnIfAttackIsProhibited(CallbackInfoReturnable<Boolean> cir) {
        if (this.hitResult.getType() == HitResult.Type.BLOCK) {
            ItemStack itemStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
            if (!itemStack.isEmpty()) {
                BlockHitResult blockHitResult = (BlockHitResult) this.hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();

                boolean canAttack = ((IUrkazLibItemExtension)(itemStack.getItem())).urkazlib$canAttackBlock(itemStack, blockPos, this.player);
                if (!canAttack) {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
    private void urkazlib$continueAttack$returnIfAttackIsProhibited(CallbackInfo ci) {
        if (this.hitResult.getType() == HitResult.Type.BLOCK) {
            ItemStack itemStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
            if (!itemStack.isEmpty()) {
                BlockHitResult blockHitResult = (BlockHitResult) this.hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();

                boolean canAttack = ((IUrkazLibItemExtension)(itemStack.getItem())).urkazlib$canAttackBlock(itemStack, blockPos, this.player);
                if (!canAttack) {
                    ci.cancel();
                }
            }
        }
    }
}
