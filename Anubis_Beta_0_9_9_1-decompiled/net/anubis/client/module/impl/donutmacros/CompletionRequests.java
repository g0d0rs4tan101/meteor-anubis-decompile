/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.suggestion.Suggestion
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2596
 *  net.minecraft.class_2639
 *  net.minecraft.class_2805
 *  net.minecraft.class_310
 *  net.minecraft.class_634
 */
package net.anubis.client.module.impl.donutmacros;

import com.mojang.brigadier.suggestion.Suggestion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2596;
import net.minecraft.class_2639;
import net.minecraft.class_2805;
import net.minecraft.class_310;
import net.minecraft.class_634;

@Environment(value=EnvType.CLIENT)
final class CompletionRequests {
    private static final int FIRST_ID = 10000;
    private static final int LAST_ID = 29999;
    private static final long FORGET_MS = 15000L;
    private final Map<Integer, Request> requests = new ConcurrentHashMap<Integer, Request>();
    private int nextId = 10000;
    private Form form = Form.BOTH;

    CompletionRequests() {
    }

    Form form() {
        return this.form;
    }

    void form(Form form) {
        this.form = form;
    }

    int fire(String partial) {
        return this.fire(partial, this.form);
    }

    int fire(String partial, Form using) {
        class_634 connection = class_310.method_1551().method_1562();
        if (connection == null) {
            return -1;
        }
        int id = this.nextId++;
        if (this.nextId > 29999) {
            this.nextId = 10000;
        }
        this.requests.put(id, new Request(System.currentTimeMillis()));
        try {
            if (using != Form.BARE) {
                connection.method_52787((class_2596)new class_2805(id, "/" + partial));
            }
            if (using != Form.SLASH) {
                connection.method_52787((class_2596)new class_2805(id, partial));
            }
        }
        catch (RuntimeException e) {
            this.requests.remove(id);
            return -1;
        }
        return id;
    }

    List<String> poll(int id) {
        Request request = this.requests.get(id);
        return request == null || request.done ? null : request.names;
    }

    void forget(int id) {
        Request request = this.requests.get(id);
        if (request != null) {
            request.done = true;
        }
    }

    void prune(long now) {
        this.requests.values().removeIf(request -> now - request.at > 15000L);
    }

    void clear() {
        this.requests.clear();
        this.form = Form.BOTH;
    }

    boolean ingest(class_2596<?> packet) {
        if (!(packet instanceof class_2639)) {
            return false;
        }
        class_2639 suggestions = (class_2639)packet;
        Request request = this.requests.get(suggestions.comp_2262());
        if (request == null) {
            return false;
        }
        if (request.names == null) {
            ArrayList<String> names = new ArrayList<String>();
            for (Suggestion suggestion : suggestions.method_11397().getList()) {
                String text = suggestion.getText();
                if (text == null || text.isBlank()) continue;
                names.add(text.trim());
            }
            request.names = List.copyOf(names);
        }
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    static enum Form {
        BOTH,
        SLASH,
        BARE;

    }

    @Environment(value=EnvType.CLIENT)
    private static final class Request {
        final long at;
        volatile List<String> names;
        volatile boolean done;

        Request(long at) {
            this.at = at;
        }
    }
}

