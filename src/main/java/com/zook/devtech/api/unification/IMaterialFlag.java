package com.zook.devtech.api.unification;

import com.zook.devtech.common.unification.MaterialFlagImpl;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * loader gregtech
 */
@ZenClass("mods.gregtech.MaterialFlag")
@ZenRegister
public interface IMaterialFlag {

    @ZenMethod
    static IMaterialFlag builder(String name) {
        return new MaterialFlagImpl(name);
    }

    @ZenMethod
    IMaterialFlag requireFlag(String flag);

    @ZenMethod
    IMaterialFlag requireDust();

    @ZenMethod
    IMaterialFlag requireIngot();

    @ZenMethod
    IMaterialFlag requireGem();

    @ZenMethod
    IMaterialFlag requireFluid();

    @ZenMethod
    IMaterialFlag requirePlasma();

    @ZenMethod
    IMaterialFlag requireTool();

    @ZenMethod
    void build();
}
