/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.core.fluid;

import com.urkaz.urkazlib.lib.client.CustomFogData;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import org.joml.Vector4f;

public interface IFluidProperties {

    Fluid getFlowingFluid();
    Fluid getSourceFluid();
    boolean canConvertToSource();
    int getSlopeFindDistance(LevelReader level);
    int getDropOff(LevelReader level);
    Item getBucket();
    int getTickDelay(LevelReader level);
    float getExplosionResistance();
    LiquidBlock getBlock();
    SoundEvent getFillSound();
    SoundEvent getEmptySound();
    int getLuminosity();
    int getTemperature();
    int getViscosity();
    boolean isLighterThanAir();
    ResourceLocation getSourceTexture();
    ResourceLocation getFlowingTexture();
    ResourceLocation getOverlayTexture();
    int getTintColor();
    int getDensity();
    Rarity getRarity();
    Component getName();
    String getTranslationKey();
    CustomFogData fogData(Camera camera, float renderDistance, float partialTick);
    boolean modifyFogData();
    Vector4f fogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount);
    boolean modifyFogColor();
}
