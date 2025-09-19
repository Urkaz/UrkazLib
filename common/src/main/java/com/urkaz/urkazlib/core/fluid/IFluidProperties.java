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
