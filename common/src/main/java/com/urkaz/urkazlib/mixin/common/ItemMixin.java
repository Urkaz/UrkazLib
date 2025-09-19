/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.mixin.common;

import com.urkaz.urkazlib.api.item.IUrkazLibItemExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements IUrkazLibItemExtension {
    @Override
    public boolean urkazlib$canAttackBlock(ItemStack itemStack, BlockPos blockPos, Player player) {
        return true;
    }
}
