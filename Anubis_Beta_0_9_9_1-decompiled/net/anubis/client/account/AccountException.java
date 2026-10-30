/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.account;

import java.util.concurrent.CompletionException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
final class AccountException
extends CompletionException {
    AccountException(String message) {
        super(message);
    }

    AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}

