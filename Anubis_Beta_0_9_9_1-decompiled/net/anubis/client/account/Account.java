/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.account;

import net.anubis.client.account.AccountType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class Account {
    private final String id;
    private final AccountType type;
    private final String name;
    private final String uuid;
    private final String secret;

    Account(String id, AccountType type, String name, String uuid, String secret) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.uuid = uuid;
        this.secret = secret;
    }

    public AccountType type() {
        return this.type;
    }

    public String name() {
        return this.name;
    }

    public String uuid() {
        return this.uuid;
    }

    public String id() {
        return this.id;
    }

    String secret() {
        return this.secret;
    }

    Account withLogin(String name, String uuid, String secret) {
        return new Account(this.id, this.type, name, uuid, secret);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object other) {
        if (!(other instanceof Account)) return false;
        Account account = (Account)other;
        if (!account.id.equals(this.id)) return false;
        return true;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return this.type.label() + " " + this.name;
    }
}

