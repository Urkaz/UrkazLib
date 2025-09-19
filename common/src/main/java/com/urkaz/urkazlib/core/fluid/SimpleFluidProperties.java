/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.core.fluid;

import com.google.common.base.Suppliers;
import com.urkaz.urkazlib.lib.client.CustomFogData;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import java.util.function.Supplier;

public class SimpleFluidProperties implements IFluidProperties {

    private final Supplier<? extends Fluid> sourceFluid;
    private final Supplier<? extends Fluid> flowingFluid;
    private Supplier<? extends Item> bucket;
    private Supplier<? extends LiquidBlock> block;
    private int slopeFindDistance = 4;
    private int dropOff = 1;
    private float explosionResistance = 1.0F;
    private int tickDelay = 5;
    private boolean canConvertToSource = false;
    @Nullable
    private SoundEvent fillSound = SoundEvents.BUCKET_FILL;
    @Nullable
    private SoundEvent emptySound = SoundEvents.BUCKET_EMPTY;
    private int luminosity = 0;
    private int temperature = 300;
    private int viscosity = 1000;
    private boolean lighterThanAir = false;
    @Nullable
    private ResourceLocation sourceTexture;
    @Nullable
    private ResourceLocation flowingTexture;
    @Nullable
    private ResourceLocation overlayTexture;
    int color = 0xffffffff;
    private int density = 1000;
    private Rarity rarity = Rarity.COMMON;
    private final Supplier<String> defaultTranslationKey;
    private CustomFogData customFogData = null;
    private boolean modifyFogData = false;
    private Vector4f fogColorRGBA = new Vector4f(1F, 1F, 1F, 1F);
    private boolean modifyFogColor = false;


    protected SimpleFluidProperties(Supplier<? extends Fluid> sourceFluid, Supplier<? extends Fluid> flowingFluid) {
        this.sourceFluid = sourceFluid;
        this.flowingFluid = flowingFluid;
        this.defaultTranslationKey = Suppliers.memoize(() -> Util.makeDescriptionId("fluid", BuiltInRegistries.FLUID.getKey(sourceFluid.get())));
    }

    public static SimpleFluidProperties of(Supplier<? extends Fluid> flowingFluid, Supplier<? extends Fluid> sourceFluid) {
        return new SimpleFluidProperties(flowingFluid, sourceFluid);
    }

    public static SimpleFluidProperties ofSupplier(Supplier<? extends Supplier<? extends Fluid>> flowingFluid, Supplier<? extends Supplier<? extends Fluid>> sourceFluid) {
        return of(() -> flowingFluid.get().get(), () -> sourceFluid.get().get());
    }

    public SimpleFluidProperties bucket(Supplier<? extends Item> bucket) {
        this.bucket = bucket;
        return this;
    }

    public SimpleFluidProperties block(Supplier<? extends LiquidBlock> block) {
        this.block = block;
        return this;
    }

    public SimpleFluidProperties slopeFindDistance(int slopeFindDistance) {
        this.slopeFindDistance = slopeFindDistance;
        return this;
    }

    public SimpleFluidProperties dropOff(int dropOff) {
        this.dropOff = dropOff;
        return this;
    }

    public SimpleFluidProperties explosionResistance(float explosionResistance) {
        this.explosionResistance = explosionResistance;
        return this;
    }

    public SimpleFluidProperties tickDelay(int tickRate) {
        this.tickDelay = tickRate;
        return this;
    }

    public SimpleFluidProperties canConvertToSource(boolean canConvertToSource) {
        this.canConvertToSource = canConvertToSource;
        return this;
    }

    public SimpleFluidProperties fillSound(SoundEvent fillSound) {
        this.fillSound = fillSound;
        return this;
    }

    public SimpleFluidProperties emptySound(SoundEvent emptySound) {
        this.emptySound = emptySound;
        return this;
    }

    public SimpleFluidProperties luminosity(int luminosity) {
        this.luminosity = luminosity;
        return this;
    }

    public SimpleFluidProperties temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public SimpleFluidProperties viscosity(int viscosity) {
        this.viscosity = viscosity;
        return this;
    }

    public SimpleFluidProperties lighterThanAir(boolean lighterThanAir) {
        this.lighterThanAir = lighterThanAir;
        return this;
    }

    public SimpleFluidProperties sourceTexture(ResourceLocation sourceTexture) {
        this.sourceTexture = sourceTexture;
        return this;
    }

    public SimpleFluidProperties flowingTexture(ResourceLocation flowingTexture) {
        this.flowingTexture = flowingTexture;
        return this;
    }

    public SimpleFluidProperties overlayTexture(ResourceLocation overlayTexture) {
        this.overlayTexture = overlayTexture;
        return this;
    }

    public SimpleFluidProperties color(int color) {
        this.color = color;
        return this;
    }

    public SimpleFluidProperties density(int density) {
        this.density = density;
        return this;
    }

    public SimpleFluidProperties rarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public SimpleFluidProperties fogColorRGBA(Vector4f color) {
        this.fogColorRGBA = color;
        this.modifyFogColor = true;
        return this;
    }

    public SimpleFluidProperties fogColorRGBA(int color) {

        float r = ((color >> 24) & 0xFF) / 255f;
        float g = ((color >> 16) & 0xFF) / 255f;
        float b = ((color >> 8) & 0xFF) / 255f;
        float a = (color & 0xFF) / 255f;

        return fogColorRGBA(new Vector4f(r, g, b, a));
    }

    public SimpleFluidProperties fogColorRGBA(ColorRGBA color) {
        return fogColorRGBA(color.rgba());
    }

    public SimpleFluidProperties fogData(CustomFogData fogData) {
        this.customFogData = fogData;
        this.modifyFogData = true;
        return this;
    }

    @Override
    public Fluid getFlowingFluid() {
        return flowingFluid.get();
    }

    @Override
    public Fluid getSourceFluid() {
        return sourceFluid.get();
    }

    @Override
    public boolean canConvertToSource() {
        return canConvertToSource;
    }

    @Override
    public int getSlopeFindDistance(LevelReader level) {
        return slopeFindDistance;
    }

    @Override
    public int getDropOff(LevelReader level) {
        return dropOff;
    }

    @Override
    public Item getBucket() {
        return bucket.get();
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return tickDelay;
    }

    @Override
    public float getExplosionResistance() {
        return explosionResistance;
    }

    @Override
    public LiquidBlock getBlock() {
        return block.get();
    }

    @Override
    public SoundEvent getFillSound() {
        return fillSound;
    }

    @Override
    public SoundEvent getEmptySound() {
        return emptySound;
    }

    @Override
    public int getLuminosity() {
        return luminosity;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public int getViscosity() {
        return viscosity;
    }

    @Override
    public boolean isLighterThanAir() {
        return lighterThanAir;
    }

    @Override
    public ResourceLocation getSourceTexture() {
        return sourceTexture;
    }

    @Override
    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    @Override
    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    @Override
    public int getTintColor() {
        return color;
    }

    @Override
    public int getDensity() {
        return density;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public Component getName() {
        return Component.translatable(getTranslationKey());
    }

    @Override
    public String getTranslationKey() {
        return defaultTranslationKey.get();
    }

    @Override
    public CustomFogData fogData(Camera camera, float renderDistance, float partialTick) {
        return customFogData;
    }

    @Override
    public boolean modifyFogData() {
        return modifyFogData;
    }

    @Override
    public Vector4f fogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount) {
        return fogColorRGBA;
    }

    @Override
    public boolean modifyFogColor() {
        return modifyFogColor;
    }
}
