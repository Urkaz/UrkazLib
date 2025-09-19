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
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
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
