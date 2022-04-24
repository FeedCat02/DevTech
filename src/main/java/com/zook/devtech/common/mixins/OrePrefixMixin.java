package com.zook.devtech.common.mixins;

import com.zook.devtech.common.CommonProxy;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(value = OrePrefix.class, remap = false)
public abstract class OrePrefixMixin {

    @Inject(method = "processOreRegistration", at = @At("RETURN"))
    public void processOre(Material material, CallbackInfo ci) {
        if (material != null) {
            CommonProxy.GENERATED_MATERIALS.computeIfAbsent((OrePrefix) (Object) this, key -> new HashSet<>()).add(material);
        }
    }
}
