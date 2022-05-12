package com.zook.devtech.api.unification.material;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

/**
 * loader gregtech
 */
@ZenClass("mods.gregtech.MaterialFlag")
@ZenRegister
public class MaterialFlag {

    @Nullable
    @ZenMethod("get")
    public static MaterialFlag getByName(String name) {
        gregtech.api.unification.material.info.MaterialFlag flag = gregtech.api.unification.material.info.MaterialFlag.getByName(name);
        if (flag == null) {
            CraftTweakerAPI.logError("Can't find MaterialFlag with name " + name);
            return null;
        }
        return new MaterialFlag(flag);
    }

    private final gregtech.api.unification.material.info.MaterialFlag internal;

    public MaterialFlag(gregtech.api.unification.material.info.MaterialFlag internal) {
        this.internal = internal;
    }

    public gregtech.api.unification.material.info.MaterialFlag getInternal() {
        return internal;
    }
}
