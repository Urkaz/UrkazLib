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

package com.urkaz.urkazlib.lib.item;

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
}
