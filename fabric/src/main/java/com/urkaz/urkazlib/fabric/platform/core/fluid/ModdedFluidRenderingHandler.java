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

package com.urkaz.urkazlib.fabric.platform.core.fluid;

import com.urkaz.urkazlib.core.fluid.IFluidProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModdedFluidRenderingHandler implements FluidRenderHandler, FluidVariantRenderHandler {

    IFluidProperties properties;
    protected final TextureAtlasSprite[] sprites;

    public ModdedFluidRenderingHandler(IFluidProperties properties) {
        this.properties = properties;
        this.sprites = new TextureAtlasSprite[properties.getOverlayTexture() == null ? 2 : 3];
    }

    @Override
    public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, FluidState fluidState) {
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
        ResourceLocation overlayTexture = properties.getOverlayTexture();
        this.sprites[0] = atlas.apply(properties.getSourceTexture());
        this.sprites[1] = atlas.apply(properties.getFlowingTexture());
        if (overlayTexture != null)
            this.sprites[2] = atlas.apply(overlayTexture);
        return sprites;
    }

    @Override
    public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
        return this.properties.getTintColor();
    }

    @Override
    public int getColor(FluidVariant fluidVariant, @Nullable BlockAndTintGetter view, @Nullable BlockPos pos) {
        return this.properties.getTintColor();
    }

    @Override
    public @Nullable TextureAtlasSprite[] getSprites(FluidVariant fluidVariant) {
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
        ResourceLocation overlayTexture = properties.getOverlayTexture();
        this.sprites[0] = atlas.apply(properties.getSourceTexture());
        this.sprites[1] = atlas.apply(properties.getFlowingTexture());
        if (overlayTexture != null)
            this.sprites[2] = atlas.apply(overlayTexture);
        return sprites;
    }
}
