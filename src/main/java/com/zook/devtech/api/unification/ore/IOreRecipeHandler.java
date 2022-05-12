package com.zook.devtech.api.unification.ore;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.ore.IOreRecipeHandler")
@ZenRegister
@FunctionalInterface
public interface IOreRecipeHandler {

    @ZenMethod
    void processMaterial(OrePrefix orePrefix, Material material);
}
