/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 */
package net.anubis.client.esp;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.function.LongFunction;
import net.anubis.client.util.BlockBoxHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import net.minecraft.class_238;

@Environment(value=EnvType.CLIENT)
public final class BlockBoxCache {
    private final int capacity;
    private final LongFunction<class_238> factory;
    private final Long2ObjectLinkedOpenHashMap<Entry> boxes = new Long2ObjectLinkedOpenHashMap();
    private float size = Float.NaN;
    private long frame;

    BlockBoxCache(int capacity) {
        this(capacity, null);
    }

    public BlockBoxCache(int capacity, LongFunction<class_238> factory) {
        this.capacity = Math.max(1, capacity);
        this.factory = factory;
    }

    public void beginFrame(float size) {
        if (Float.floatToIntBits(this.size) != Float.floatToIntBits(size)) {
            this.boxes.clear();
            this.size = size;
        }
        ++this.frame;
    }

    public class_238 box(long packed) {
        class_238 made;
        Entry hit = (Entry)this.boxes.getAndMoveToLast(packed);
        if (hit != null) {
            hit.frame = this.frame;
            return hit.box;
        }
        class_238 class_2382 = made = this.factory == null ? BlockBoxHelper.scaledBlockBox(class_2338.method_10092((long)packed), this.size) : this.factory.apply(packed);
        if (this.boxes.size() == this.capacity) {
            if (((Entry)this.boxes.get((long)this.boxes.firstLongKey())).frame == this.frame) {
                return made;
            }
            this.boxes.removeFirst();
        }
        this.boxes.put(packed, (Object)new Entry(made, this.frame));
        return made;
    }

    int size() {
        return this.boxes.size();
    }

    @Environment(value=EnvType.CLIENT)
    private static final class Entry {
        final class_238 box;
        long frame;

        Entry(class_238 box, long frame) {
            this.box = box;
            this.frame = frame;
        }
    }
}

