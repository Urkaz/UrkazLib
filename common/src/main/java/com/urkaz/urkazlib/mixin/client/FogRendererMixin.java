/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.urkaz.urkazlib.api.block.IUrkazLibFogColorBlock;
import com.urkaz.urkazlib.lib.client.CustomFogData;
import com.urkaz.urkazlib.platform.core.fluid.IFluidRegisterHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(
            method = "computeFogColor",
            at = @At("HEAD"),
            cancellable = true)
    private void urkazlib$computeFogColor$handleCustomColor(CallbackInfoReturnable<Vector4f> ci,
                                                            @Local(argsOnly = true) Camera camera,
                                                            @Local(ordinal = 0, argsOnly = true) float partialTick,
                                                            @Local(argsOnly = true) ClientLevel level,
                                                            @Local(ordinal = 0, argsOnly = true) int renderDistance,
                                                            @Local(ordinal = 1, argsOnly = true) float darkenWorldAmount) {

        Vector4f fogColor = new Vector4f(1F, 1F, 1F, 1F);
        BlockPos cameraBlockPos = camera.getBlockPosition();

        BlockState blockState = level.getBlockState(cameraBlockPos);
        if (blockState.getBlock() instanceof IUrkazLibFogColorBlock customFogColorBlock) {
            if (customFogColorBlock.urkazlib$overrideFogColor()) {
                ColorRGBA color = customFogColorBlock.urkazlib$getOverriddenFogColor(blockState, partialTick, level, renderDistance, darkenWorldAmount);
                int blockFogColor = color.rgba();
                float r = ((blockFogColor >> 24) & 0xFF) / 255f;
                float g = ((blockFogColor >> 16) & 0xFF) / 255f;
                float b = ((blockFogColor >> 8) & 0xFF) / 255f;
                float a = (blockFogColor & 0xFF) / 255f;

                fogColor = new Vector4f(r, g, b, a);
            }
        }

        FluidState fluidState = level.getFluidState(cameraBlockPos);
        Fluid fluid = fluidState.getType();
        if (IFluidRegisterHelper.INSTANCE.modifyFogColor(fluid)) {
            fogColor = IFluidRegisterHelper.INSTANCE.fogColor(fluid, camera, partialTick, level, renderDistance, darkenWorldAmount);
        }

        if (!fogColor.equals(1F, 1F, 1F, 1F))
            ci.setReturnValue(fogColor);
    }

    @Inject(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/CommandEncoder;mapBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;ZZ)Lcom/mojang/blaze3d/buffers/GpuBuffer$MappedView;"
            ))
    private void urkazlib$computeFogColor$handleCustomColor(CallbackInfoReturnable<Vector4f> ci,
                                                            @Local(argsOnly = true) Camera camera,
                                                            @Local(argsOnly = true) DeltaTracker deltaTracker,
                                                            @Local FogData fogData,
                                                            @Local(ordinal = 1) float renderDistance

    ) {
        Entity entity = camera.getEntity();
        Level level = entity.level();
        FluidState fluidState = level.getFluidState(camera.getBlockPosition());
        Fluid fluid = fluidState.getType();
        if (IFluidRegisterHelper.INSTANCE.modifyFogData(fluid)) {
            CustomFogData cFogData = IFluidRegisterHelper.INSTANCE.fogData(fluid, camera, renderDistance, deltaTracker.getGameTimeDeltaPartialTick(false));
            fogData.renderDistanceStart = cFogData.start;
            fogData.renderDistanceEnd = cFogData.end;
        }
    }
}
