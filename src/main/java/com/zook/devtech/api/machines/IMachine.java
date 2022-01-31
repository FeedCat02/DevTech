package com.zook.devtech.api.machines;

import com.zook.devtech.common.machines.CTMachine;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * loader crafttweaker
 */
@ZenClass("mods.gregtech.machine.Machine")
@ZenRegister
public interface IMachine {

    @ZenMethod
    static IMachine getMachine(String name) {
        return CTMachine.createFromName(name);
    }

    @ZenMethod
    static IMachine getMachine(int id) {
        return CTMachine.createForId(id);
    }

    @ZenGetter("id")
    int getIntId();

    @ZenGetter("name")
    String getName();

}
