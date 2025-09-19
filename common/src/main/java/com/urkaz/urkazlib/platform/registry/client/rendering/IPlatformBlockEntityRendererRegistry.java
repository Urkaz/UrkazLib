/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform.registry.client.rendering;

import com.urkaz.urkazlib.api.ServiceUtil;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IPlatformBlockEntityRendererRegistry {

    <T extends BlockEntity> void register(BlockEntityType<T> type, BlockEntityRendererProvider<? super T> provider);

    IPlatformBlockEntityRendererRegistry INSTANCE = ServiceUtil.findService(IPlatformBlockEntityRendererRegistry.class, null);
}
