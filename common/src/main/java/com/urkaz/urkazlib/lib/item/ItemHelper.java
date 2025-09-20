/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.lib.item;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class ItemHelper {

    @SuppressWarnings("deprecation")
    public static Holder<Item> getHolder(Item item) {
        return item.builtInRegistryHolder();
    }

    @Nullable
    public static ResourceLocation getRegistryName(Item item) {
        return getHolder(item).unwrapKey().map(ResourceKey::location).orElse(null);
    }
}
