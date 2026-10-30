/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1657
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_636
 *  net.minecraft.class_746
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.example.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.anubis.client.module.impl.misc.SpeedMineModule;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_636.class})
public abstract class MixinSpeedMineMultiPlayerGameMode {
    @Shadow
    @Final
    private class_310 field_3712;
    @Shadow
    private int field_3716;

    @Inject(method={"method_2902"}, at={@At(value="HEAD")})
    private void anubis$speedMineDelay(class_2338 pos, class_2350 direction, CallbackInfoReturnable<Boolean> cir) {
        class_746 player = this.field_3712.field_1724;
        if (this.field_3716 <= 0 || player == null || this.field_3712.field_1687 == null || player.method_31549().field_7477) {
            return;
        }
        class_2680 state = this.field_3712.field_1687.method_8320(pos);
        if (SpeedMineModule.dropsDelay(state, state.method_26165((class_1657)player, (class_1922)this.field_3712.field_1687, pos))) {
            this.field_3716 = 0;
        }
    }

    @WrapOperation(method={"method_2902"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_2680;method_26165(Lnet/minecraft/class_1657;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;)F")})
    private float anubis$speedMineProgress(class_2680 state, class_1657 player, class_1922 level, class_2338 pos, Operation<Float> original) {
        return SpeedMineModule.scaleProgress(state, ((Float)original.call(new Object[]{state, player, level, pos})).floatValue());
    }
}

