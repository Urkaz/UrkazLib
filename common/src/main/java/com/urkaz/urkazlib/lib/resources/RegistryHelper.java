/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.lib.resources;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryHelper {

    public static ResourceLocation getRegistryName(Item i) {
        return BuiltInRegistries.ITEM.getKey(i);
    }

    public static ResourceLocation getRegistryName(Block b) {
        return BuiltInRegistries.BLOCK.getKey(b);
    }

    public static ResourceLocation getRegistryName(EntityType<?> i) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(i);
    }
}
