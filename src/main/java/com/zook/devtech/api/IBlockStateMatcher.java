package com.zook.devtech.api;

import com.zook.devtech.common.BlockStateMatcher;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

@ZenClass("mods.devtech.IBlockStateMatcher")
@ZenRegister
@FunctionalInterface
public interface IBlockStateMatcher extends Predicate<IBlockState> {

    @Override
    default boolean test(IBlockState iBlockState) {
        return matches(iBlockState);
    }

    boolean matches(IBlockState iBlockState);

    static IBlockStateMatcher of(String... blockStates) {
        return BlockStateMatcher.of(Arrays.asList(blockStates));
    }

    static IBlockStateMatcher of(Collection<String> blockStates) {
        return BlockStateMatcher.of(blockStates);
    }
}
