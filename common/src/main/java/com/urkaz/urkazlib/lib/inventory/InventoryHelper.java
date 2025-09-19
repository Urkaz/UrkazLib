/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.lib.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class InventoryHelper {

    public static int removeItemFromInventory(ItemStack item, Inventory playerInventory) {

        int removedCount = 0;
        int itemCount = item.getCount();

        for (int j = 0; j < playerInventory.getNonEquipmentItems().size(); j++) {
            ItemStack invItem = playerInventory.getNonEquipmentItems().get(j);

            if (ItemStack.isSameItem(invItem, item)) {
                int invItemCount = invItem.getCount();
                int quantityToShrink = Math.min(invItemCount, itemCount);
                removedCount += quantityToShrink;

                invItem.shrink(quantityToShrink);
                if (invItem.isEmpty()) {
                    playerInventory.setItem(j, ItemStack.EMPTY);
                }

                if (removedCount == itemCount) {
                    break;
                }
            }
        }
        return removedCount;
    }
}
