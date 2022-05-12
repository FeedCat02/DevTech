package com.zook.devtech.api.unification.stonetype;

import com.zook.devtech.common.unification.MaterialRegistry;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockStateMatcher;
import gregtech.api.unification.material.Material;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * WIP!
 * <p>
 * loader gregtech
 */
@ZenClass("mods.gregtech.StoneType")
@ZenRegister
public interface IStoneType {

    /**
     * Creates a ore stone type
     *
     * @param id                    int id. Starting at 100 should cause no issues
     * @param name                  name of the stone type. Should be lower underscore camel case f.e. "black_granite"
     * @param orePrefix             the name of the oreprefix to use. If it can't find one, a default will be created.
     * @param material              The material of the ore block. Defines max harvest level.
     * @param blockState            The block state of the block.
     * @param stateMatcher          The states that matches this will can be replaced in generation. Can be omitted.
     * @param shouldBeDroppedAsItem If the ore should drop itself or the stone variant (default)
     */
    @ZenMethod
    static void create(int id, String name, String orePrefix, Material material, String blockState, @Optional IBlockStateMatcher stateMatcher, @Optional boolean shouldBeDroppedAsItem) {
        MaterialRegistry.registerStoneType(id, name, orePrefix, material, blockState, stateMatcher, shouldBeDroppedAsItem);
    }
}
