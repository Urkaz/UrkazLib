/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.forge.platform.core.fluid;

import com.google.common.base.MoreObjects;
import com.urkaz.urkazlib.UrkazLib;
import com.urkaz.urkazlib.core.fluid.IFluidProperties;
import com.urkaz.urkazlib.platform.Platform;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;

import static net.minecraft.sounds.SoundEvents.BUCKET_EMPTY;
import static net.minecraft.sounds.SoundEvents.BUCKET_FILL;

public class ModdedFluidType extends FluidType {

    IFluidProperties properties;
    String defaultTranslationKey;

    public ModdedFluidType(Properties builder, IFluidProperties properties, Fluid fluid) {
        super(populateNeoforgeProperties(builder, properties));
        this.properties = properties;
        this.defaultTranslationKey = Util.makeDescriptionId("fluid", BuiltInRegistries.FLUID.getKey(fluid));
    }

    private static Properties populateNeoforgeProperties(Properties builder, IFluidProperties properties) {
        builder.lightLevel(properties.getLuminosity())
                .density(properties.getDensity())
                .temperature(properties.getTemperature())
                .rarity(properties.getRarity())
                .canConvertToSource(properties.canConvertToSource())
                .viscosity(properties.getViscosity());
        return builder;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(registerClient());
    }

    public IClientFluidTypeExtensions registerClient() {
        return new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return properties.getTintColor();
            }

            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return properties.getSourceTexture();
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return properties.getFlowingTexture();
            }

            @Override
            @Nullable
            public ResourceLocation getOverlayTexture() {
                return properties.getOverlayTexture();
            }

            @Override
            public @NotNull ResourceLocation getStillTexture(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
                return properties.getSourceTexture();
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
                return properties.getFlowingTexture();
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
                return properties.getOverlayTexture();
            }

            @Override
            public int getTintColor(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
                return properties.getTintColor();
            }

            @Override
            public int getTintColor(@NotNull FluidStack stack) {
                return properties.getTintColor();
            }

            @Override
            public @NotNull ResourceLocation getStillTexture(@NotNull FluidStack stack) {
                return properties.getSourceTexture();
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture(@NotNull FluidStack stack) {
                return properties.getFlowingTexture();
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture(@NotNull FluidStack stack) {
                return properties.getOverlayTexture();
            }
        };
    }

    @Override
    public int getLightLevel(FluidStack stack) {
        return properties.getLuminosity();
    }

    @Override
    public int getLightLevel(FluidState state, BlockAndTintGetter level, BlockPos pos) {
        return properties.getLuminosity();
    }

    @Override
    public int getDensity(FluidStack stack) {
        return properties.getDensity();
    }

    @Override
    public int getDensity(FluidState state, BlockAndTintGetter level, BlockPos pos) {
        return properties.getDensity();
    }

    @Override
    public int getTemperature(FluidStack stack) {
        return properties.getTemperature();
    }

    @Override
    public int getTemperature(FluidState state, BlockAndTintGetter level, BlockPos pos) {
        return properties.getTemperature();
    }

    @Override
    public int getViscosity(FluidStack stack) {
        return properties.getViscosity();
    }

    @Override
    public int getViscosity(FluidState state, BlockAndTintGetter level, BlockPos pos) {
        return properties.getViscosity();
    }

    @Override
    public Rarity getRarity() {
        return properties.getRarity();
    }

    @Override
    public Rarity getRarity(FluidStack stack) {
        return properties.getRarity();
    }

    @Override
    public Component getDescription() {
        return properties.getName();
    }

    @Override
    public Component getDescription(FluidStack stack) {
        return properties.getName();
    }

    @Override
    public String getDescriptionId() {
        return MoreObjects.firstNonNull(properties.getTranslationKey(), defaultTranslationKey);
    }

    @Override
    public String getDescriptionId(FluidStack stack) {
        return MoreObjects.firstNonNull(properties.getTranslationKey(), defaultTranslationKey);
    }

    @Override
    @Nullable
    public SoundEvent getSound(SoundAction action) {
        return getSound((FluidStack) null, action);
    }

    @Override
    @Nullable
    public SoundEvent getSound(@Nullable FluidStack stack, SoundAction action) {
        if (BUCKET_FILL.equals(action)) {
            return properties.getFillSound();
        } else if (BUCKET_EMPTY.equals(action)) {
            return properties.getEmptySound();
        }
        return null;
    }

    @Override
    public boolean canConvertToSource(FluidStack stack) {
        return properties.canConvertToSource();
    }

    @Override
    public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
        return properties.canConvertToSource();
    }
}
