/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform.registry.client.rendering;

import com.urkaz.urkazlib.api.ServiceUtil;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IPlatformRenderTypeRegistry {

    void register(ChunkSectionLayer layer, Block... blocks);
    void register(ChunkSectionLayer layer, Fluid... fluids);

    IPlatformRenderTypeRegistry INSTANCE = ServiceUtil.findService(IPlatformRenderTypeRegistry.class, null);
}
