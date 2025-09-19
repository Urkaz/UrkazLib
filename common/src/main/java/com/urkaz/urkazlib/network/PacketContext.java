/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.network;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;

public interface PacketContext {
    Player getPlayer();

    void queue(Runnable runnable);

    boolean isClient();

    RegistryAccess registryAccess();
}
