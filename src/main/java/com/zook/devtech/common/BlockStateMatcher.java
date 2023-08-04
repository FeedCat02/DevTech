package com.zook.devtech.common;

import com.zook.devtech.api.IBlockStateMatcher;
import com.zook.devtech.common.unification.MaterialRegistry;
import crafttweaker.api.block.IBlockState;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class BlockStateMatcher implements IBlockStateMatcher {

    public static BlockStateMatcher of(Collection<String> unloadedStates) {
        BlockStateMatcher stateMatcher = new BlockStateMatcher();
        stateMatcher.unloadedStates = unloadedStates;
        stateMatcher.loaded = false;
        return stateMatcher;
    }

    private final Set<IBlockState> matches = new ObjectOpenHashSet<>();
    private Collection<String> unloadedStates;
    private boolean loaded = true;

    public BlockStateMatcher(IBlockState... states) {
        Collections.addAll(matches, states);
    }

    public BlockStateMatcher(Collection<IBlockState> states) {
        this.matches.addAll(states);
    }

    @Override
    public boolean matches(IBlockState iBlockState) {
        if (matches.isEmpty()) {
            if (!loaded && unloadedStates != null && !unloadedStates.isEmpty()) {
                for (String unloadedState : unloadedStates) {
                    IBlockState blockState = MaterialRegistry.getCtBlockState(unloadedState);
                    if (blockState != null) {
                        matches.add(blockState);
                    }
                }
                loaded = true;
                unloadedStates = null;
                return !matches.isEmpty() && matches.contains(iBlockState);
            }
            return false;
        }
        return matches.contains(iBlockState);
    }
}
