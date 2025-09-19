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
