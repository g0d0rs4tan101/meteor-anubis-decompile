/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.UserApiService
 *  com.mojang.authlib.minecraft.UserApiService$UserProperties
 *  com.mojang.authlib.yggdrasil.ProfileResult
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_310
 *  net.minecraft.class_320
 *  net.minecraft.class_5520
 *  net.minecraft.class_7574
 *  net.minecraft.class_7853
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.example.client.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_320;
import net.minecraft.class_5520;
import net.minecraft.class_7574;
import net.minecraft.class_7853;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_310.class})
public interface AccessorAccountsMinecraft {
    @Mutable
    @Accessor(value="field_1726")
    public void anubis$setUser(class_320 var1);

    @Mutable
    @Accessor(value="field_26902")
    public void anubis$setUserApiService(UserApiService var1);

    @Mutable
    @Accessor(value="field_47680")
    public void anubis$setUserPropertiesFuture(CompletableFuture<UserApiService.UserProperties> var1);

    @Mutable
    @Accessor(value="field_45899")
    public void anubis$setProfileFuture(CompletableFuture<ProfileResult> var1);

    @Mutable
    @Accessor(value="field_26842")
    public void anubis$setPlayerSocialManager(class_5520 var1);

    @Mutable
    @Accessor(value="field_39068")
    public void anubis$setProfileKeyPairManager(class_7853 var1);

    @Accessor(value="field_39492")
    public void anubis$setReportingContext(class_7574 var1);
}

