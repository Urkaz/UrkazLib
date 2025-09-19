/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
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

    final IFluidProperties properties;
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
