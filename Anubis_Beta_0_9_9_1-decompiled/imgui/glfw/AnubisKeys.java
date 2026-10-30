/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.glfw.GLFW
 */
package imgui.glfw;

import imgui.AnubisAndroidNative;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;

@Environment(value=EnvType.CLIENT)
public final class AnubisKeys {
    private static final boolean ANDROID = AnubisAndroidNative.isAndroidRuntime();
    private static final int FIRST_KEY = 32;
    private static final int LAST_KEY = 348;
    private static final Map<Long, State> STATES = new HashMap<Long, State>();
    private static final KeyReader GLFW_READER = GLFW::glfwGetKey;

    private AnubisKeys() {
    }

    public static boolean isAndroid() {
        return ANDROID;
    }

    public static int getKey(long window, int key) {
        return AnubisKeys.getKey(window, key, ANDROID, GLFW_READER);
    }

    public static void record(long window, int key, int action) {
        AnubisKeys.record(window, key, action, ANDROID);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clear(long window) {
        Map<Long, State> map = STATES;
        synchronized (map) {
            STATES.remove(window);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int getKey(long window, int key, boolean android, KeyReader reader) {
        if (!android) {
            return reader.get(window, key);
        }
        if (key < 32 || key > 348) {
            return 0;
        }
        Map<Long, State> map = STATES;
        synchronized (map) {
            State state = STATES.get(window);
            if (state != null && state.unavailable[key]) {
                return state.down[key] ? 1 : 0;
            }
        }
        try {
            int action = reader.get(window, key);
            return action == 1 || action == 2 ? 1 : 0;
        }
        catch (IndexOutOfBoundsException missingSlot) {
            Map<Long, State> map2 = STATES;
            synchronized (map2) {
                State state = AnubisKeys.state(window);
                state.unavailable[key] = true;
                return state.down[key] ? 1 : 0;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void record(long window, int key, int action, boolean android) {
        if (!android || key < 32 || key > 348) {
            return;
        }
        if (action != 1 && action != 0 && action != 2) {
            return;
        }
        Map<Long, State> map = STATES;
        synchronized (map) {
            AnubisKeys.state((long)window).down[key] = action != 0;
        }
    }

    private static State state(long window) {
        State value = STATES.get(window);
        if (value == null) {
            value = new State();
            STATES.put(window, value);
        }
        return value;
    }

    @Environment(value=EnvType.CLIENT)
    static interface KeyReader {
        public int get(long var1, int var3);
    }

    @Environment(value=EnvType.CLIENT)
    private static final class State {
        final boolean[] down = new boolean[349];
        final boolean[] unavailable = new boolean[349];

        private State() {
        }
    }
}

