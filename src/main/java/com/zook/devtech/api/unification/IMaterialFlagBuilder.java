package com.zook.devtech.api.unification;

import com.zook.devtech.common.unification.MaterialFlagBuilderImpl;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * loader gregtech
 */
@ZenClass("mods.gregtech.MaterialFlagBuilder")
@ZenRegister
public interface IMaterialFlagBuilder {

    @ZenMethod
    static IMaterialFlagBuilder create(String name) {
        return new MaterialFlagBuilderImpl(name);
    }

    @ZenMethod
    default IMaterialFlagBuilder requireFlag(String flag) {
        return requireFlag(MaterialFlag.getByName(flag));
    }

    @ZenMethod
    IMaterialFlagBuilder requireFlag(MaterialFlag flag);

    @ZenMethod
    IMaterialFlagBuilder requireDust();

    @ZenMethod
    IMaterialFlagBuilder requireIngot();

    @ZenMethod
    IMaterialFlagBuilder requireGem();

    @ZenMethod
    IMaterialFlagBuilder requireFluid();

    @ZenMethod
    IMaterialFlagBuilder requirePlasma();

    @ZenMethod
    IMaterialFlagBuilder requireTool();

    @ZenMethod
    MaterialFlag build();
}
