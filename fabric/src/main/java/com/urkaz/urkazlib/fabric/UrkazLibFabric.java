/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.fabric;

import com.urkaz.urkazlib.UrkazLib;
import net.fabricmc.api.ModInitializer;

public class UrkazLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UrkazLib.init();
    }
}
