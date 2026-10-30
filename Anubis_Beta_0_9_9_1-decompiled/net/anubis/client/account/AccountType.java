/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.account;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public enum AccountType {
    CRACKED("Cracked"),
    SESSION("Session"),
    MICROSOFT("Microsoft");

    private final String label;

    private AccountType(String label) {
        this.label = label;
    }

    public String label() {
        return this.label;
    }
}

