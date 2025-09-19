/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric.platform.registry.client.rendering;

import com.urkaz.urkazlib.platform.registry.client.rendering.IPlatformRenderTypeRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class PlatformRenderTypeRegistryImpl implements IPlatformRenderTypeRegistry {

    @Override
    public void register(ChunkSectionLayer layer, Block... blocks) {
        BlockRenderLayerMap.putBlocks(layer, blocks);
    }

    @Override
    public void register(ChunkSectionLayer layer, Fluid... fluids) {
        BlockRenderLayerMap.putFluids(layer, fluids);
    }
}
