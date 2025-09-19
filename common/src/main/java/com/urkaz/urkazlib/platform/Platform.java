/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform;

public class Platform {
    public static boolean isClient() {
        return IPlatform.INSTANCE.isClient();
    }

    public static boolean isModLoaded(String modId) {
        return IPlatform.INSTANCE.isModLoaded(modId);
    }

    public static boolean isDevelopmentEnvironment() {
        return IPlatform.INSTANCE.isDevelopmentEnvironment();
    }
}
