/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.platform;

import com.urkaz.urkazlib.api.ServiceUtil;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IPlatform {

    boolean isClient();
    boolean isModLoaded(String modId);
    boolean isDevelopmentEnvironment();

    IPlatform INSTANCE = ServiceUtil.findService(IPlatform.class, null);
}
