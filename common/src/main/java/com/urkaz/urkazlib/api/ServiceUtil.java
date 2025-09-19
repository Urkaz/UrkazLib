/*
 * UrkazLib (C) 2025 by Urkaz - Fran Sanchez is licensed under Creative Commons Attribution-NonCommercial 4.0 International.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc/4.0/
 */

package com.urkaz.urkazlib.api;

import org.jetbrains.annotations.Nullable;

import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ServiceUtil {
    public static <T> T findService(Class<T> clazz, @Nullable Supplier<T> defaultImpl) {
        var providers = ServiceLoader.load(clazz).stream().toList();
        if (providers.isEmpty() && defaultImpl != null) {
            return defaultImpl.get();
        } else if (providers.size() != 1) {
            var names = providers.stream().map(p -> p.type().getName()).collect(Collectors.joining(",", "[", "]"));
            var msg = "There should be exactly one implementation of %s on the classpath. Found: %s"
                    .formatted(clazz.getName(), names);
            throw new IllegalStateException(msg);
        } else {
            var provider = providers.getFirst();
            return provider.get();
        }
    }
}
