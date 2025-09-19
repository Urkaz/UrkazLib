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

package com.urkaz.urkazlib.lib.math;

import java.util.ArrayList;
import java.util.List;

public class MathHelper {

    // The matrix should be [Vertical layers][Rows][Columns]
    public static <T> List<List<List<T>>> rotateMatrixClockwise(List<List<List<T>>> matrix) {
        if (matrix.isEmpty()) return new ArrayList<>();

        List<List<List<T>>> rotated = new ArrayList<>();

        // Iterate each layer
        for (List<List<T>> layer : matrix) {
            int oldRows = layer.size();
            int oldCols = layer.getFirst().size();

            // Create the the new rotated matrix for this layer (MxN → NxM)
            List<List<T>> rotatedLayer = new ArrayList<>();
            for (int i = 0; i < oldCols; i++) {
                rotatedLayer.add(new ArrayList<>());
                for (int j = 0; j < oldRows; j++) {
                    rotatedLayer.get(i).add(null);
                }
            }

            // Fill the new matrix
            for (int i = 0; i < oldRows; i++) {
                for (int j = 0; j < oldCols; j++) {
                    rotatedLayer.get(j).set(oldRows - 1 - i, layer.get(i).get(j));
                }
            }

            rotated.add(rotatedLayer);
        }
        return rotated;
    }
}
