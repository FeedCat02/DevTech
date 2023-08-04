package com.zook.devtech.api.unification.stonetype;

import com.zook.devtech.api.IBlockStateMatcher;
import com.zook.devtech.common.unification.MaterialRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
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
     * @param id                    int id
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

    @ZenMethod
    static Builder builder() {
        return new Builder();
    }

    @ZenClass("mods.gregtech.StoneTypeBuilder")
    @ZenRegister
    class Builder {
        private int id;
        private String name;
        private String orePrefix;
        private Material material;
        private String blockState;
        private IBlockStateMatcher matcher;
        private boolean shouldBeDroppedAsItem = false;

        @ZenMethod
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        @ZenMethod
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @ZenMethod
        public Builder orePrefix(String orePrefix) {
            this.orePrefix = orePrefix;
            return this;
        }

        @ZenMethod
        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        @ZenMethod
        public Builder blockState(String blockState) {
            this.blockState = blockState;
            return this;
        }

        @ZenMethod
        public Builder blockStateMatcher(IBlockStateMatcher stateMatcher) {
            this.matcher = stateMatcher;
            return this;
        }

        @ZenMethod
        public Builder registerItem() {
            this.shouldBeDroppedAsItem = true;
            return this;
        }

        @ZenMethod
        public void buildAndRegister() {
            boolean e = false;
            if (id >= 0 && id <= 11) {
                CraftTweakerAPI.logError("Ids 0 - 11 are reserved for GTCEu Stonetypes!");
                e = true;
            }
            if (name == null) {
                CraftTweakerAPI.logError("Stonetype needs a name!");
                e = true;
            }
            if (orePrefix == null) {
                CraftTweakerAPI.logError("Stonetype needs a ore prefix!");
                e = true;
            }
            if (material == null) {
                CraftTweakerAPI.logError("Stonetype needs a material!");
                e = true;
            }
            if (blockState == null) {
                CraftTweakerAPI.logError("Stonetype needs a block state!");
                e = true;
            }
            if (e) return;
            MaterialRegistry.registerStoneType(id, name, orePrefix, material, blockState, matcher, shouldBeDroppedAsItem);
        }
    }
}
