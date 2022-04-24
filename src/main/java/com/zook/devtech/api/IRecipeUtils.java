package com.zook.devtech.api;

import com.zook.devtech.DevTech;
import com.zook.devtech.common.RecipeUtils;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.gregtech.recipe.IRecipeUtils")
@ZenRegister
public interface IRecipeUtils {

    @ZenProperty
    IRecipeUtils utils = new RecipeUtils();

    @ZenMethod("fluid")
    ILiquidStack getFluid(String name, @Optional(valueLong = 1) int amount);

    @ZenMethod("item")
    IItemStack getItem(String name, @Optional int meta, @Optional(valueLong = 1) int amount);

    @ZenMethod("ore")
    IIngredient getOreEntry(String name, @Optional(valueLong = 1) int amount);

    @ZenMethod("item")
    IItemStack getItem(OrePrefix orePrefix, Material material, @Optional(valueLong = 1) int amount);

    @ZenMethod("item")
    IItemStack getItem(String orePrefix, Material material, @Optional(valueLong = 1) int amount);

    @ZenMethod("item")
    IItemStack getItem(OrePrefix orePrefix, String material, @Optional(valueLong = 1) int amount);

    @ZenMethod("item")
    IItemStack getItem(String orePrefix, String material, @Optional(valueLong = 1) int amount);

    @ZenMethod("ore")
    IIngredient getOreEntry(OrePrefix orePrefix, Material material, @Optional(valueLong = 1) int amount);

    @ZenMethod("ore")
    IIngredient getOreEntry(String orePrefix, Material material, @Optional(valueLong = 1) int amount);

    @ZenMethod("ore")
    IIngredient getOreEntry(OrePrefix orePrefix, String material, @Optional(valueLong = 1) int amount);

    @ZenMethod("ore")
    IIngredient getOreEntry(String orePrefix, String material, @Optional(valueLong = 1) int amount);
}
