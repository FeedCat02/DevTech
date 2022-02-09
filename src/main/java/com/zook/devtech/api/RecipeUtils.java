package com.zook.devtech.api;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("mods.gregtech.recipe.helpers")
@ZenRegister
public class RecipeUtils {

    @ZenMethodStatic
    public static IItemStack getItem(OrePrefix orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        return new MCItemStack(OreDictUnifier.get(orePrefix, material, amount));
    }

    @ZenMethodStatic
    public static IItemStack getItem(String orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix1, material, amount));
    }

    @ZenMethodStatic
    public static IItemStack getItem(OrePrefix orePrefix, String material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        Material material1 = GregTechAPI.MaterialRegistry.get(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix, material1, amount));
    }

    @ZenMethodStatic
    public static IItemStack getItem(String orePrefix, String material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        Material material1 = GregTechAPI.MaterialRegistry.get(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix1, material1, amount));
    }

    @ZenMethodStatic
    public static IIngredient getOreEntry(OrePrefix orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        UnificationEntry entry = new UnificationEntry(orePrefix, material);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), amount);
    }

    @ZenMethodStatic
    public static IIngredient getOreEntry(String orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        UnificationEntry entry = new UnificationEntry(orePrefix1, material);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), amount);
    }

    @ZenMethodStatic
    public static IIngredient getOreEntry(OrePrefix orePrefix, String material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        Material material1 = GregTechAPI.MaterialRegistry.get(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        UnificationEntry entry = new UnificationEntry(orePrefix, material1);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), amount);
    }

    @ZenMethodStatic
    public static IIngredient getOreEntry(String orePrefix, String material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        Material material1 = GregTechAPI.MaterialRegistry.get(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        UnificationEntry entry = new UnificationEntry(orePrefix1, material1);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), amount);
    }
}
