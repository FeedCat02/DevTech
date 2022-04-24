package com.zook.devtech.api.unification;

import com.google.common.base.CaseFormat;
import com.zook.devtech.DevTech;
import com.zook.devtech.common.CommonProxy;
import com.zook.devtech.common.unification.MaterialRegistry;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.ArrayList;

/**
 * loader gregtech
 */
@ZenExpansion("mods.gregtech.ore.OrePrefix")
@ZenRegister
public interface IOrePrefix {

    @ZenMethodStatic
    static OrePrefix registerOrePrefix(String name, float amount, @Optional String iconType, @Optional long flags) {
        return MaterialRegistry.registerOrePrefix(name, amount, iconType, flags);
    }

    @ZenMethod
    static void setGenerationPredicate(OrePrefix orePrefix, IMaterialPredicate materialPredicate) {
        orePrefix.setGenerationCondition(materialPredicate);
    }

    @ZenMethod
    static void generateRecipes(OrePrefix orePrefix, IOreRegistrationHandler oreRegistrationHandler) {
        CommonProxy.REGISTRATION_HANDLERS.computeIfAbsent(orePrefix, key -> new ArrayList<>()).add(oreRegistrationHandler);
    }

    @ZenMethod
    static void addSecondaryMaterial(OrePrefix orePrefix, Material material, @Optional(valueDouble = 1) float amount) {
        orePrefix.addSecondaryMaterial(new MaterialStack(material, (long) (GTValues.M * amount)));
    }

    @ZenMethod
    static void createMaterialItem(OrePrefix orePrefix) {
        MetaPrefixItem prefixItem = new MetaPrefixItem(orePrefix);
        prefixItem.setRegistryName(DevTech.MODID, "meta_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orePrefix.name()));
    }
}
