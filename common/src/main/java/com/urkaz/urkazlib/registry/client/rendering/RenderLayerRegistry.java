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
