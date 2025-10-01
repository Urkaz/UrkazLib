/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.lib.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackHelper {

    public static boolean containsItem(List<ItemStack> list, ItemStack item) {
        for (ItemStack itemStack : list) {
            if (itemStack.is(item.getItem())) {
                return true;
            }
        }
        return false;
    }

    public static int findItemIndex(List<ItemStack> list, ItemStack item) {
        for (int i = 0; i < list.size(); ++i) {
            if (ItemStack.isSameItem(list.get(i), item)) {
                return i;
            }
        }
        return -1;
    }

    public static List<ItemStack> collectItems(List<ItemStack> list, ItemStack item) {
        List<ItemStack> output = new ArrayList<>();
        for (ItemStack itemStack : list) {
            if (ItemStack.isSameItem(item, itemStack)) {
                output.add(itemStack);
            }
        }
        return output;
    }

    public static int getItemCount(List<ItemStack> list) {
        int count = 0;
        for (ItemStack item : list) {
            count += item.getCount();
        }
        return count;
    }

    public static ItemStack getItemStackInHand(Player player, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND)
            return player.getMainHandItem();
        return player.getOffhandItem();
    }
}
