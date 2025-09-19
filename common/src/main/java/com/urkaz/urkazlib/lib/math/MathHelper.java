/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
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

            // Create the new rotated matrix for this layer (MxN -> NxM)
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
