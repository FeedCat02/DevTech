package com.zook.devtech.api.unification;

import com.google.common.base.CaseFormat;
import com.zook.devtech.DevTech;
import com.zook.devtech.common.unification.MaterialRegistry;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import stanhebben.zenscript.annotations.*;

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
    static void addRecipeHandler(OrePrefix orePrefix, IOreRegistrationHandler oreRegistrationHandler) {
        orePrefix.addProcessingHandler(oreRegistrationHandler);
    }

    @ZenMethod
    static void createMaterialItem(OrePrefix orePrefix) {
        MetaPrefixItem prefixItem = new MetaPrefixItem(orePrefix);
        prefixItem.setRegistryName(DevTech.MODID, "meta_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orePrefix.name()));
    }
}
