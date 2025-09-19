/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.registry.client.rendering;

import com.urkaz.urkazlib.platform.registry.client.rendering.IPlatformRenderTypeRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public final class RenderLayerRegistry {
    private RenderLayerRegistry() {
    }

    public static void register(ChunkSectionLayer layer, Block block) {
        IPlatformRenderTypeRegistry.INSTANCE.register(layer, block);
    }

    public static void register(ChunkSectionLayer layer, Fluid fluid) {
        IPlatformRenderTypeRegistry.INSTANCE.register(layer, fluid);
    }
}
