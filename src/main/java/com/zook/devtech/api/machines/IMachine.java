package com.zook.devtech.api.machines;

import com.zook.devtech.common.machines.MachineRegistry;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.FuelRecipeBuilder;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * loader gregtech
 */
@ZenClass("mods.gregtech.Machine")
@ZenRegister
public interface IMachine {

    @ZenMethod
    static void createSimple(int id, String name, RecipeMap<?> recipeMap, String renderer, int tier) {
        MachineRegistry.createSimple(id, name, recipeMap, renderer, tier);
    }

    @ZenMethod
    static void createSimple(int id, String name, RecipeMap<?> recipeMap, IMachineRenderer renderer, int tier) {
        MachineRegistry.createSimple(id, name, recipeMap, renderer, tier);
    }

    @ZenMethod
    static void createSimpleGenerator(int id, String name, RecipeMap<FuelRecipeBuilder> recipeMap, String renderer, int tier, @Optional boolean canHandleOutputs) {
        MachineRegistry.createSimpleGenerator(id, name, recipeMap, renderer, tier, canHandleOutputs);
    }

    @ZenMethod
    static void createSimpleGenerator(int id, String name, RecipeMap<FuelRecipeBuilder> recipeMap, IMachineRenderer renderer, int tier, @Optional boolean canHandleOutputs) {
        MachineRegistry.createSimpleGenerator(id, name, recipeMap, renderer, tier, canHandleOutputs);
    }
}
