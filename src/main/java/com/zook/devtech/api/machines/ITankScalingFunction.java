package com.zook.devtech.api.machines;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Function;

@FunctionalInterface
@ZenClass("mods.gregtech.machine.TankScalingFunction")
@ZenRegister
public interface ITankScalingFunction extends Function<Integer, Integer> {

    @ZenMethod
    int scale(int tier);

    @Override
    default Integer apply(Integer integer) {
        return scale(integer);
    }
}
