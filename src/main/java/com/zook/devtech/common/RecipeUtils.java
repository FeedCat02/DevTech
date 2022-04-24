package com.zook.devtech.common;

import com.zook.devtech.api.IRecipeUtils;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.brackets.BracketHandlerLiquid;
import crafttweaker.mc1120.brackets.BracketHandlerOre;
import crafttweaker.mc1120.item.MCItemStack;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import stanhebben.zenscript.annotations.Optional;

public class RecipeUtils implements IRecipeUtils {

    @Override
    public ILiquidStack getFluid(String name, int amount) {
        if (amount < 0)
            amount = 0;
        ILiquidStack fluid = BracketHandlerLiquid.getLiquid(name);
        if (fluid == null) {
            CraftTweakerAPI.logError("Can't find fluid for " + name);
            return null;
        }
        return fluid.withAmount(amount);
    }

    @Override
    public IItemStack getItem(String name, int meta, int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        if (meta < 0)
            meta = 32767;
        IItemStack item = BracketHandlerItem.getItem(name, meta);
        if (item == null)
            return MCItemStack.EMPTY;
        return item.withAmount(amount);
    }

    @Override
    public IIngredient getOreEntry(String name, int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        return BracketHandlerOre.getOre(name).amount(amount);
    }

    @Override
    public IItemStack getItem(OrePrefix orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        return new MCItemStack(OreDictUnifier.get(orePrefix, material, amount));
    }

    @Override
    public IItemStack getItem(String orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix1, material, amount));
    }

    @Override
    public IItemStack getItem(OrePrefix orePrefix, String material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        Material material1 = GregTechAPI.MaterialRegistry.get(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix, material1, amount));
    }

    @Override
    public IItemStack getItem(String orePrefix, String material, @Optional(valueLong = 1) int amount) {
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

    @Override
    public IIngredient getOreEntry(OrePrefix orePrefix, Material material, @Optional(valueLong = 1) int amount) {
        if (amount < 1)
            return MCItemStack.EMPTY;
        UnificationEntry entry = new UnificationEntry(orePrefix, material);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), amount);
    }

    @Override
    public IIngredient getOreEntry(String orePrefix, Material material, @Optional(valueLong = 1) int amount) {
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

    @Override
    public IIngredient getOreEntry(OrePrefix orePrefix, String material, @Optional(valueLong = 1) int amount) {
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

    @Override
    public IIngredient getOreEntry(String orePrefix, String material, @Optional(valueLong = 1) int amount) {
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
