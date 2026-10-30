/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.util;

import imgui.AnubisAndroidNative;
import net.anubis.client.module.impl.client.PerformanceModule;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class MobilePerformance {
    private static final boolean ANDROID = AnubisAndroidNative.isAndroidRuntime();

    private MobilePerformance() {
    }

    public static boolean isAndroid() {
        return ANDROID;
    }

    public static boolean active() {
        return ANDROID && PerformanceModule.mobileEffectsEnabled();
    }

    public static int segments(int original, int minimum) {
        return MobilePerformance.reduce(original, minimum);
    }

    public static int particles(int original, int minimum) {
        return MobilePerformance.reduce(original, minimum);
    }

    public static int trailSamples(int original, int minimum) {
        return MobilePerformance.reduce(original, minimum);
    }

    public static int uiSegments(int original, float radiusPixels) {
        if (!MobilePerformance.active() || original <= 12 || !Float.isFinite(radiusPixels) || radiusPixels <= 0.0f) {
            return original;
        }
        double angle = Math.acos(Math.max(-1.0, 1.0 - 0.35 / (double)radiusPixels));
        if (angle <= 0.0) {
            return original;
        }
        int needed = (int)Math.ceil(Math.PI / angle);
        if (needed >= original) {
            return original;
        }
        return Math.min(original, Math.max(12, (needed + 3) / 4 * 4));
    }

    private static int reduce(int original, int minimum) {
        if (!MobilePerformance.active() || original <= 0) {
            return original;
        }
        return Math.min(original, Math.max(minimum, original / 2 + original % 2));
    }
}

