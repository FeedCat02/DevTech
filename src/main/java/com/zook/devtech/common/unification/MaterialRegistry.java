package com.zook.devtech.common.unification;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IBlockStateMatcher;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.brackets.BracketHandlerBlockState;
import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import net.minecraft.block.SoundType;
import stanhebben.zenscript.annotations.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MaterialRegistry {

    public static final List<StoneType> STONE_TYPE_LIST = new ArrayList<>();

    public static OrePrefix registerOrePrefix(String name, float amount, @Optional String iconType, @Optional long flags) {
        if (OrePrefix.getPrefix(name) != null) {
            CraftTweakerAPI.logError("OrePrefix with name " + name + " is already registered.");
            return null;
        }
        if (iconType == null || iconType.isEmpty()) {
            iconType = name;
        }
        MaterialIconType materialIconType = MaterialIconType.ICON_TYPES.get(iconType);
        if (materialIconType == null) {
            materialIconType = new MaterialIconType(iconType);
        }
        return new OrePrefix(name, (long) (GTValues.M * amount), null, materialIconType, flags, null);
    }

    public static void registerStoneType(int id, String name, String orePrefix, Material material, String blockState, IBlockStateMatcher stateMatcher, boolean shouldBeDroppedAsItem) {
        OrePrefix prefix = OrePrefix.getPrefix(orePrefix);
        if (prefix == null) {
            prefix = new OrePrefix(orePrefix, -1, null, MaterialIconType.ore, OrePrefix.Flags.ENABLE_UNIFICATION, OrePrefix.Conditions.hasOreProperty);
        }
        Supplier<net.minecraft.block.state.IBlockState> stateSupplier = () -> {
            IBlockState ctBlockState = getCtBlockState(blockState);
            if (ctBlockState == null) {
                return null;
            }
            return CraftTweakerMC.getBlockState(ctBlockState);
        };
        Predicate<net.minecraft.block.state.IBlockState> spawnPredicate;
        if (stateMatcher == null) {
            spawnPredicate = state -> {
                IBlockState ctBlockState = getCtBlockState(blockState);
                if (ctBlockState == null)
                    return false;
                return state.equals(CraftTweakerMC.getBlockState(ctBlockState));
            };
        } else {
            spawnPredicate = state -> stateMatcher.matches(CraftTweakerMC.getBlockState(state));
        }

        StoneType stoneType = new StoneType(id, name, SoundType.STONE, prefix, material, stateSupplier, spawnPredicate, shouldBeDroppedAsItem);
        STONE_TYPE_LIST.add(stoneType);
        //UNBAKED_STONE_TYPES.add(new UnbakedStoneType(id, name, prefix, material, blockState, stateMatcher, shouldBeDroppedAsItem));
    }

    private static IBlockState getCtBlockState(String string) {
        String[] parts = string.split(":", 3);
        String properties = "";
        if (parts.length > 2) {
            properties = parts[2];
        }
        return BracketHandlerBlockState.getBlockState(parts[0] + ":" + parts[1], properties);
    }
}
