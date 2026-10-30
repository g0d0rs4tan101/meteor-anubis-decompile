/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1657
 *  net.minecraft.class_1713
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_5251
 *  net.minecraft.class_746
 *  net.minecraft.class_7923
 *  net.minecraft.class_9290
 *  net.minecraft.class_9334
 */
package net.anubis.client.module.impl.donutmacros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.anubis.client.chat.ModuleChat;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.impl.donutmacros.AhSniperModule;
import net.anubis.client.module.impl.donutmacros.AuctionHouse;
import net.anubis.client.module.impl.donutmacros.Dialogs;
import net.anubis.client.module.impl.donutmacros.Gui;
import net.anubis.client.module.impl.donutmacros.MacroWorker;
import net.anubis.client.module.impl.donutmacros.ServerText;
import net.anubis.client.module.impl.donutmacros.SnipeTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5251;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import net.minecraft.class_9290;
import net.minecraft.class_9334;

@Environment(value=EnvType.CLIENT)
final class SniperBot {
    private static final Pattern RATE_LIMIT = Pattern.compile("you need to wait another ([\\d.]+) seconds?");
    private static final int LISTING_SLOTS = 45;
    private static final int DEFAULT_FILTER = 47;
    private static final int DEFAULT_REFRESH = 49;
    private static final long OPEN_TIMEOUT_MS = 10000L;
    private static final long BUY_TIMEOUT_MS = 4000L;
    private static final long FAILED_SKIP_MS = 60000L;
    private static final long BOUGHT_SKIP_MS = 15000L;
    private static final long RECOVERY_MS = 2000L;
    private final AhSniperModule module;
    private final MacroWorker worker;
    private final Gui gui;
    private final SnipeTarget target;
    private final long refreshMs;
    private final int maxBuys;
    private final AuctionHouse ah;
    private final AuctionHouse.Stock stock;
    private final List<AuctionHouse.Stock> stocks;
    private final Map<String, Long> skip = new HashMap<String, Long>();
    private volatile long boughtAt;
    private volatile long goneAt;
    private volatile long poorAt;
    private volatile long ownAt;
    private volatile long holdUntil;
    private int bought;
    private int unsold;
    private long spent;

    SniperBot(AhSniperModule module, MacroWorker worker, SnipeTarget target, long refreshMs, int maxBuys, long sellPrice, String ign) {
        this.module = module;
        this.worker = worker;
        this.gui = new Gui(worker);
        this.target = target;
        this.refreshMs = refreshMs;
        this.maxBuys = maxBuys;
        if (sellPrice > 0L) {
            this.ah = new AuctionHouse(module, worker, new AuctionHouse.Config(0L, 60000L, 20000L, 1, 1000, 0L, 3.0, 20.0, 36, 15000L), ign);
            this.stock = new AuctionHouse.Stock(target.product(sellPrice), true);
            this.stocks = List.of(this.stock);
        } else {
            this.ah = null;
            this.stock = null;
            this.stocks = List.of();
        }
    }

    private void say(ModuleChat.Body body) {
        ModuleChat.send((ClientModule)this.module, body);
    }

    void run() {
        this.say(ModuleChat.body().text("Sniping ").name(this.target.describe()).text(" under $").value(ServerText.shortMoney(this.target.maxPrice)).text((String)(this.target.minMinutes > 0 ? " with " + SniperBot.formatMinutes(this.target.minMinutes) + "+ left" : "")).text((String)(this.stock != null ? ", selling at $" + ServerText.shortMoney(this.stock.price) : "")).text("."));
        while (true) {
            this.worker.check();
            try {
                if (!this.worker.call(() -> Gui.mc().field_1724 != null && Gui.mc().method_1562() != null).booleanValue()) {
                    this.worker.sleep(2000L);
                    continue;
                }
                if (this.stock != null && this.unsold > 0) {
                    this.sellStock();
                }
                this.snipe();
                continue;
            }
            catch (MacroWorker.Cancelled cancelled) {
                throw cancelled;
            }
            catch (RuntimeException failure) {
                String reason = failure.getMessage() == null ? failure.getClass().getSimpleName() : failure.getMessage();
                this.say(ModuleChat.body().warn(reason).text(", trying again."));
                this.worker.check();
                this.gui.closeAll();
                this.worker.sleep(2000L);
                continue;
            }
            break;
        }
    }

    private void snipe() {
        this.openAuction();
        this.ensureRecent();
        long nextRefresh = System.currentTimeMillis() + this.refreshMs;
        while (true) {
            this.worker.check();
            if (!this.worker.call(SniperBot::auctionOpenNow).booleanValue()) {
                return;
            }
            long mark = System.currentTimeMillis();
            Hit hit = this.worker.call(this::scanAndClick);
            if (hit != null) {
                if (this.handleBuy(hit, mark)) {
                    return;
                }
                nextRefresh = System.currentTimeMillis() + this.refreshMs;
                continue;
            }
            long now = System.currentTimeMillis();
            if (now >= nextRefresh) {
                this.worker.run(this::clickRefresh);
                nextRefresh = now + this.refreshMs;
            }
            this.worker.sleep(50L);
        }
    }

    private void openAuction() {
        this.gui.closeAll();
        long wait = this.holdUntil - System.currentTimeMillis();
        if (wait > 0L) {
            this.worker.sleep(wait);
        }
        this.gui.command("ah " + this.target.search);
        if (!this.worker.waitFor(SniperBot::auctionOpenNow, 10000L)) {
            throw new AuctionHouse.Problem("The AH did not open");
        }
        this.worker.sleep(300L);
    }

    private static boolean auctionOpenNow() {
        if (!Gui.containerOpenNow()) {
            return false;
        }
        String title = ServerText.normalize(Gui.mc().field_1755.method_25440());
        if (title.contains("order")) {
            return false;
        }
        return title.contains("auction") || SniperBot.refreshSlot(Gui.containerNow()) >= 0;
    }

    private void ensureRecent() {
        for (int attempt = 0; attempt < 4; ++attempt) {
            int[] state = this.worker.call(() -> {
                List<class_1799> stacks = Gui.containerNow();
                int slot = SniperBot.filterSlot(stacks);
                return new int[]{slot, slot < 0 ? Sort.UNKNOWN.ordinal() : SniperBot.selectedSort(stacks.get(slot)).ordinal()};
            });
            if (state[0] < 0) {
                this.say(ModuleChat.body().warn("No Filter button found").text(", sniping with the current sorting."));
                return;
            }
            Sort sort = Sort.values()[state[1]];
            if (sort == Sort.RECENT) {
                return;
            }
            if (sort == Sort.UNKNOWN && attempt > 0) {
                this.say(ModuleChat.body().warn("Could not read the Filter").text(", make sure it is on Recently Listed."));
                return;
            }
            String before = this.gui.fingerprint();
            this.gui.click(state[0], 0, class_1713.field_7790);
            this.gui.waitChange(before, 2000L);
            this.worker.sleep(250L);
        }
    }

    private static int filterSlot(List<class_1799> stacks) {
        for (int i = 45; i < stacks.size(); ++i) {
            class_1799 stack = stacks.get(i);
            if (stack.method_7960() || !stack.method_31574(class_1802.field_8239) && !Gui.nameOf(stack).contains("filter")) continue;
            return i;
        }
        return stacks.size() > 47 && !stacks.get(47).method_7960() ? 47 : -1;
    }

    private static int refreshSlot(List<class_1799> stacks) {
        for (int i = 45; i < stacks.size(); ++i) {
            class_1799 stack = stacks.get(i);
            if (stack.method_7960() || !stack.method_31574(class_1802.field_8782)) continue;
            return i;
        }
        return stacks.size() > 49 && stacks.get(49).method_31574(class_1802.field_8782) ? 49 : -1;
    }

    static Sort selectedSort(class_1799 filter) {
        ArrayList<Sort> options = new ArrayList<Sort>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (class_2561 line : ((class_9290)filter.method_58695(class_9334.field_49632, (Object)class_9290.field_49340)).comp_2400()) {
            Sort option;
            String text = ServerText.normalize(Dialogs.visibleText(line));
            Sort sort = text.contains("recent") ? Sort.RECENT : (text.contains("lowest") ? Sort.LOWEST : (option = text.contains("highest") ? Sort.HIGHEST : Sort.UNKNOWN));
            if (option == Sort.UNKNOWN) continue;
            options.add(option);
            colors.add(SniperBot.color(line));
        }
        if (options.size() < 2) {
            return Sort.UNKNOWN;
        }
        for (int i = 0; i < options.size(); ++i) {
            boolean unique = true;
            boolean othersAgree = true;
            int other = -2;
            for (int j = 0; j < options.size(); ++j) {
                if (j == i) continue;
                if (((Integer)colors.get(j)).equals(colors.get(i))) {
                    unique = false;
                }
                if (other == -2) {
                    other = (Integer)colors.get(j);
                    continue;
                }
                if (other == (Integer)colors.get(j)) continue;
                othersAgree = false;
            }
            if (!unique || !othersAgree) continue;
            return (Sort)((Object)options.get(i));
        }
        int best = -1;
        Sort pick = Sort.UNKNOWN;
        for (int i = 0; i < options.size(); ++i) {
            int rgb = (Integer)colors.get(i);
            int brightness = (rgb >> 16 & 0xFF) + (rgb >> 8 & 0xFF) + (rgb & 0xFF);
            if (brightness > best) {
                best = brightness;
                pick = (Sort)((Object)options.get(i));
                continue;
            }
            if (brightness != best) continue;
            pick = Sort.UNKNOWN;
        }
        return pick;
    }

    private static int color(class_2561 line) {
        int[] value = new int[]{-1};
        line.method_27658((style, text) -> {
            if (value[0] >= 0 || text.isBlank() || text.trim().equals("\u2022") || text.trim().equals("\u25aa")) {
                return Optional.empty();
            }
            class_5251 color = style.method_10973();
            value[0] = color == null ? 0xAA00AA : color.method_27716();
            return Optional.empty();
        }, class_2583.field_24360);
        return value[0];
    }

    private void clickRefresh() {
        class_746 player = Gui.mc().field_1724;
        if (player == null || !SniperBot.auctionOpenNow()) {
            return;
        }
        int slot = SniperBot.refreshSlot(Gui.containerNow());
        if (slot >= 0) {
            Gui.mc().field_1761.method_2906(player.field_7512.field_7763, slot, 0, class_1713.field_7790, (class_1657)player);
        }
    }

    private Hit scanAndClick() {
        class_746 player = Gui.mc().field_1724;
        if (player == null || !SniperBot.auctionOpenNow()) {
            return null;
        }
        String title = ServerText.normalize(Gui.mc().field_1755.method_25440());
        if (title.contains("(page") && !title.contains("(page 1)")) {
            return null;
        }
        long now = System.currentTimeMillis();
        this.skip.values().removeIf(until -> until < now);
        List<class_1799> stacks = Gui.containerNow();
        for (int i = 0; i < 45 && i < stacks.size(); ++i) {
            String fingerprint;
            long unit;
            double total;
            class_1799 stack = stacks.get(i);
            if (stack.method_7960() || !this.target.matches(stack) || (total = ServerText.listingPrice(stack)) <= 0.0 || (unit = Math.round(total / (double)Math.max(1, stack.method_7947()))) > this.target.maxPrice || this.skip.containsKey(fingerprint = SniperBot.fingerprint(stack, total))) continue;
            Gui.mc().field_1761.method_2906(player.field_7512.field_7763, i, 0, class_1713.field_7790, (class_1657)player);
            return new Hit(i, unit, fingerprint, stack.method_7964().getString());
        }
        return null;
    }

    private static String fingerprint(class_1799 stack, double total) {
        return String.valueOf(class_7923.field_41178.method_10221((Object)stack.method_7909())) + "|" + stack.method_7964().getString() + "|" + Math.round(total) + "|" + stack.method_7919() + "|" + String.join((CharSequence)"/", ServerText.lore(stack));
    }

    private boolean handleBuy(Hit hit, long mark) {
        Object[] pressed = new Object[]{null};
        int[] pressedChest = new int[]{-1};
        long end = System.currentTimeMillis() + 4000L;
        while (System.currentTimeMillis() < end) {
            if (this.boughtAt >= mark) {
                return this.bought(hit);
            }
            if (this.poorAt >= mark) {
                this.say(ModuleChat.body().danger("Not enough money").text(" for ").name(hit.name()).text(" at $").value(ServerText.shortMoney(hit.price())).text(", stopping."));
                this.module.stopFromBot(this);
                throw new MacroWorker.Cancelled();
            }
            if (this.goneAt >= mark || this.ownAt >= mark) {
                this.skip.put(hit.fingerprint(), System.currentTimeMillis() + 60000L);
                return false;
            }
            this.worker.run(() -> {
                Dialogs.View view = Dialogs.current(Gui.mc());
                if (view != null) {
                    Dialogs.Button yes;
                    if (view.dialog() != pressed[0] && (view.titled("buy") || view.titled("sure") || view.titled("purchase")) && (yes = view.labelled("yes", "confirm", "buy")) != null && Dialogs.press(Gui.mc(), view, yes, Map.of())) {
                        pressed[0] = view.dialog();
                    }
                    return;
                }
                class_746 player = Gui.mc().field_1724;
                if (player == null || !Gui.containerOpenNow() || SniperBot.auctionOpenNow()) {
                    return;
                }
                int sync = player.field_7512.field_7763;
                if (sync == pressedChest[0]) {
                    return;
                }
                List<class_1799> stacks = Gui.containerNow();
                for (int i = 0; i < stacks.size(); ++i) {
                    class_1799 stack = stacks.get(i);
                    if (stack.method_7960() || !class_7923.field_41178.method_10221((Object)stack.method_7909()).method_12832().contains("lime")) continue;
                    Gui.mc().field_1761.method_2906(sync, i, 0, class_1713.field_7790, (class_1657)player);
                    pressedChest[0] = sync;
                    return;
                }
            });
            this.worker.sleep(50L);
        }
        this.skip.put(hit.fingerprint(), System.currentTimeMillis() + 60000L);
        return this.worker.call(SniperBot::auctionOpenNow) == false;
    }

    private boolean bought(Hit hit) {
        this.skip.put(hit.fingerprint(), System.currentTimeMillis() + 15000L);
        ++this.bought;
        ++this.unsold;
        this.spent += hit.price();
        this.say(ModuleChat.body().good("Sniped").text(" ").name(hit.name()).text(" for $").value(ServerText.shortMoney(hit.price())).text(" (").value(this.bought).text(this.bought == 1 ? " buy" : " buys").text(", $").value(ServerText.shortMoney(this.spent)).text(" spent)."));
        if (this.maxBuys > 0 && this.bought >= this.maxBuys) {
            if (this.stock != null) {
                this.worker.sleep(300L);
                this.sellStock();
            }
            this.say(ModuleChat.body().text("Reached ").value(this.maxBuys).text(this.maxBuys == 1 ? " buy" : " buys").text(", stopping."));
            this.module.stopFromBot(this);
            throw new MacroWorker.Cancelled();
        }
        if (this.stock == null) {
            return this.worker.call(SniperBot::auctionOpenNow) == false;
        }
        this.worker.sleep(300L);
        this.sellStock();
        return true;
    }

    private void sellStock() {
        int count = Math.min(this.unsold, this.ah.countInventory(this.stock.product));
        if (count <= 0) {
            return;
        }
        this.unsold -= this.ah.listSome(this.stock, Math.min(count, 9), new int[]{0});
    }

    void onChat(String raw) {
        String line = ServerText.normalize(raw);
        if (line.isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        Matcher wait = RATE_LIMIT.matcher(line);
        if (wait.find()) {
            try {
                this.holdUntil = now + (long)Math.ceil(Double.parseDouble(wait.group(1)) * 1000.0) + 100L;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        if (line.startsWith("you bought")) {
            this.boughtAt = now;
        }
        if (line.contains("already bought") || line.contains("no longer available") || line.contains("not available")) {
            this.goneAt = now;
        }
        if (line.contains("enough money")) {
            this.poorAt = now;
        }
        if (line.contains("your own")) {
            this.ownAt = now;
        }
        if (this.ah != null) {
            this.ah.onChat(line, this.stocks);
        }
    }

    String summary() {
        return "Sniped " + this.bought + (this.bought == 1 ? " item" : " items") + " for $" + ServerText.shortMoney(this.spent) + (String)(this.stock != null ? ". " + AuctionHouse.summary(this.stocks) : ".");
    }

    private static String formatMinutes(int minutes) {
        return minutes % 60 == 0 ? minutes / 60 + "h" : minutes / 60 + "h " + minutes % 60 + "m";
    }

    @Environment(value=EnvType.CLIENT)
    private record Hit(int slot, long price, String fingerprint, String name) {
    }

    @Environment(value=EnvType.CLIENT)
    static enum Sort {
        UNKNOWN,
        LOWEST,
        HIGHEST,
        RECENT;

    }
}

