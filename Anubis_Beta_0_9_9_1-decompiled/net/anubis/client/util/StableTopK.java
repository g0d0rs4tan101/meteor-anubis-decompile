/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class StableTopK<T> {
    private final Comparator<? super T> priority;
    private final Comparator<Entry<T>> ranked;
    private final PriorityQueue<Entry<T>> heap;
    private final ArrayList<Entry<T>> ordered = new ArrayList();
    private final ArrayList<T> result = new ArrayList();
    private int limit;
    private long encountered;
    private boolean overflow;

    public StableTopK(Comparator<? super T> priority) {
        this.priority = priority;
        this.ranked = (a, b) -> {
            int comparison = priority.compare((Object)a.value(), (Object)b.value());
            return comparison != 0 ? comparison : Long.compare(a.order(), b.order());
        };
        this.heap = new PriorityQueue<Entry<T>>(this.ranked.reversed());
    }

    public void reset(int limit) {
        this.limit = Math.max(0, limit);
        this.encountered = 0L;
        this.overflow = false;
        this.heap.clear();
        this.ordered.clear();
        this.result.clear();
    }

    public void offer(T value) {
        long order = this.encountered++;
        if (this.heap.size() < this.limit) {
            this.heap.add(new Entry<T>(value, order));
            this.result.add(value);
            return;
        }
        this.overflow = true;
        if (this.limit == 0 || this.priority.compare(value, this.heap.peek().value()) >= 0) {
            return;
        }
        this.heap.poll();
        this.heap.add(new Entry<T>(value, order));
    }

    public T worst() {
        return this.limit > 0 && this.heap.size() == this.limit ? (T)this.heap.peek().value() : null;
    }

    public List<T> result() {
        if (this.overflow) {
            this.ordered.clear();
            this.ordered.addAll(this.heap);
            this.ordered.sort(this.ranked);
            this.result.clear();
            for (Entry<T> entry : this.ordered) {
                this.result.add(entry.value());
            }
        }
        return this.result;
    }

    @Environment(value=EnvType.CLIENT)
    private record Entry<T>(T value, long order) {
    }
}

