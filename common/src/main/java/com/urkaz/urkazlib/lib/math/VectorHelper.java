/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.lib.math;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class VectorHelper {
    public static BlockHitResult getLookingAt(Player player, ItemStack tool, int range) {
        return getLookingAt(player, ClipContext.Fluid.NONE, range);
    }

    public static BlockHitResult getLookingAt(Player player, ClipContext.Fluid rayTraceFluid, int range) {
        Level world = player.level();

        Vec3 look = player.getLookAngle();
        Vec3 start = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());

        Vec3 end = new Vec3(player.getX() + look.x * (double) range, player.getY() + player.getEyeHeight() + look.y * (double) range, player.getZ() + look.z * (double) range);
        ClipContext context = new ClipContext(start, end, ClipContext.Block.OUTLINE, rayTraceFluid, player);
        return world.clip(context);
    }
}