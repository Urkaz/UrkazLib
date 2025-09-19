/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.neoforge;

import com.urkaz.urkazlib.UrkazLib;
import net.neoforged.fml.common.Mod;

@Mod(UrkazLib.MOD_ID)
public class UrkazLibNeoForge {
    public UrkazLibNeoForge() {
        UrkazLib.init();
    }
}
