/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.util.UndashedUuid
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.account;

import com.mojang.util.UndashedUuid;
import net.anubis.client.account.AccountException;
import net.anubis.client.account.AuthHttp;
import net.anubis.client.account.Credentials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
final class SessionLogin {
    private SessionLogin() {
    }

    static Credentials signIn(String accessToken) throws InterruptedException {
        AuthHttp.Reply profile = AuthHttp.get("https://api.minecraftservices.com/minecraft/profile", accessToken);
        if (profile.status() == 401) {
            throw new AccountException("This session token is invalid or has expired");
        }
        if (profile.status() == 404) {
            throw new AccountException("This token's account doesn't own Minecraft: Java Edition");
        }
        String name = profile.text("name");
        String id = profile.text("id");
        if (!profile.ok() || name == null || id == null) {
            throw new AccountException("Couldn't load the profile for this token (HTTP " + profile.status() + ")");
        }
        return new Credentials(name, UndashedUuid.fromStringLenient((String)id), accessToken, accessToken);
    }
}

