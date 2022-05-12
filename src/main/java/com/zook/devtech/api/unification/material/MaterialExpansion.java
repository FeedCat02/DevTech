package com.zook.devtech.api.unification.material;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenExpansion("mods.gregtech.material.Material")
@ZenRegister
public class MaterialExpansion {

    @ZenMethod
    public static boolean hasFlag(Material material, MaterialFlag materialFlag) {
        return material.hasFlag(materialFlag.getInternal());
    }

    @ZenMethod
    public static void addFlag(Material material, MaterialFlag materialFlag) {
        material.addFlags(materialFlag.getInternal());
    }

    @ZenMethod
    public static void addFlag(Material material, String materialFlag) {
        material.addFlags(materialFlag);
    }

    @ZenMethod
    public static void addFlags(Material material, MaterialFlag... materialFlag) {
        material.addFlags(Arrays.stream(materialFlag).map(MaterialFlag::getInternal).toArray(gregtech.api.unification.material.info.MaterialFlag[]::new));
    }
}
