/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.core.fluid;

import com.urkaz.urkazlib.core.fluid.IFluidProperties;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


class ModdedFluidAttributesHandler implements FluidVariantAttributeHandler {
    private final IFluidProperties properties;

    public ModdedFluidAttributesHandler(IFluidProperties properties) {
        this.properties = properties;
    }

    @Override
    public Component getName(FluidVariant variant) {
        return properties.getName();
    }

    @Override
    public Optional<SoundEvent> getFillSound(FluidVariant variant) {
        return Optional.ofNullable(properties.getFillSound());
    }

    @Override
    public Optional<SoundEvent> getEmptySound(FluidVariant variant) {
        return Optional.ofNullable(properties.getEmptySound());
    }

    @Override
    public int getLuminance(FluidVariant variant) {
        return properties.getLuminosity();
    }

    @Override
    public int getTemperature(FluidVariant variant) {
        return properties.getTemperature();
    }

    @Override
    public int getViscosity(FluidVariant variant, @Nullable Level world) {
        return properties.getViscosity();
    }

    @Override
    public boolean isLighterThanAir(FluidVariant variant) {
        return properties.isLighterThanAir();
    }
}
