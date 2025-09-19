/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge.platform.registry.client.rendering;


import com.urkaz.urkazlib.platform.registry.client.rendering.IPlatformRenderTypeRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class PlatformRenderTypeRegistryImpl implements IPlatformRenderTypeRegistry {

    @Override
    public void register(ChunkSectionLayer layer, Block... blocks) {
        for (Block block : blocks) {
            ItemBlockRenderTypes.setRenderLayer(block, layer);
        }
    }

    @Override
    public void register(ChunkSectionLayer layer, Fluid... fluids) {
        for (Fluid fluid : fluids) {
            ItemBlockRenderTypes.setRenderLayer(fluid, layer);
        }
    }
}
