/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
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
