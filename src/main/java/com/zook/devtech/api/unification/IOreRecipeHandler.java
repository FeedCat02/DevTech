package com.zook.devtech.api.unification;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.ore.IOreRecipeHandler")
@ZenRegister
public interface IOreRecipeHandler extends IOreRegistrationHandler {

    @ZenMethod
    void processMaterial(OrePrefix orePrefix, Material material);
}
