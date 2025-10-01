/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
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

                if (itemStack.getItem() instanceof IUrkazLibItemExtension itemExtended) {
                    boolean canAttack = itemExtended.urkazlib$canAttackBlock(itemStack, blockPos, this.player);
                    if (!canAttack) {
                        cir.setReturnValue(false);
                    }
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

                if (itemStack.getItem() instanceof IUrkazLibItemExtension itemExtended) {
                    boolean canAttack = itemExtended.urkazlib$canAttackBlock(itemStack, blockPos, this.player);
                    if (!canAttack) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
