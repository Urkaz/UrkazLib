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

package com.urkaz.urkazlib.lib.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockHelper {

    public static void playerDestroyBlock(Level level, BlockPos blockPos, BlockState blockState, Player player, boolean dropItems) {
        blockState.getBlock().playerWillDestroy(level, blockPos, blockState, player);
        blockState.getBlock().destroy(level, blockPos, blockState);
        level.destroyBlock(blockPos, dropItems);
    }

    public static float getDestroyProgressIgnoringTool(BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
        float blockStateDestroySpeed = blockState.getDestroySpeed(blockGetter, blockPos);
        if (blockStateDestroySpeed == -1.0F) {
            return 0.0F;
        } else {
            int playerSpeedModifier = player.hasCorrectToolForDrops(blockState) ? 30 : 100;
            float playerDestroySpeed = player.getDestroySpeed(blockState);
            return playerDestroySpeed / blockStateDestroySpeed / (float)playerSpeedModifier;
        }
    }
}
