package com.zook.devtech.api;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.brackets.BracketHandlerLiquid;
import crafttweaker.mc1120.brackets.BracketHandlerOre;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.integration.crafttweaker.recipe.MetaItemBracketHandler;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenClass("mods.gregtech.recipe.Utils")
@ZenRegister
public class RecipeUtils {

    @ZenMethod("fluid")
    public static ILiquidStack getFluid(String name) {
        ILiquidStack fluid = BracketHandlerLiquid.getLiquid(name);
        if (fluid == null) {
            CraftTweakerAPI.logError("Can't find fluid for " + name);
            return null;
        }
        return fluid;
    }

    @ZenMethod("fluid")
    public static ILiquidStack getFluid(Material material) {
        return new MCLiquidStack(material.getFluid(1));
    }

    @ZenMethod("item")
    public static IItemStack getItem(String name, @Optional int meta) {
        if (meta < 0)
            meta = 32767;
        IItemStack item = BracketHandlerItem.getItem(name, meta);
        if (item == null)
            return MCItemStack.EMPTY;
        return item;
    }

    @ZenMethod("metaitem")
    public static IItemStack getMetaItem(String name) {
        return MetaItemBracketHandler.getCtMetaItem(name);
    }

    @Nullable
    @ZenMethod("material")
    public static Material getMaterial(String name) {
        return GregTechAPI.materialManager.getMaterial(name);
    }

    @ZenMethod("ore")
    public IIngredient getOreEntry(String name) {
        return BracketHandlerOre.getOre(name);
    }

    @ZenMethod("item")
    public static IItemStack getItem(OrePrefix orePrefix, Material material) {
        return new MCItemStack(OreDictUnifier.get(orePrefix, material));
    }

    @ZenMethod("item")
    public static IItemStack getItem(String orePrefix, Material material) {
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix1, material));
    }

    @ZenMethod("item")
    public static IItemStack getItem(OrePrefix orePrefix, String material) {
        Material material1 = GregTechAPI.materialManager.getMaterial(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix, material1));
    }

    @ZenMethod("item")
    public static IItemStack getItem(String orePrefix, String material) {
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        Material material1 = GregTechAPI.materialManager.getMaterial(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        return new MCItemStack(OreDictUnifier.get(orePrefix1, material1));
    }

    @ZenMethod("ore")
    public static IIngredient getOreEntry(OrePrefix orePrefix, Material material) {
        UnificationEntry entry = new UnificationEntry(orePrefix, material);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), 1);
    }

    @ZenMethod("ore")
    public static IIngredient getOreEntry(String orePrefix, Material material) {
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        UnificationEntry entry = new UnificationEntry(orePrefix1, material);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), 1);
    }

    @ZenMethod("ore")
    public static IIngredient getOreEntry(OrePrefix orePrefix, String material) {
        Material material1 = GregTechAPI.materialManager.getMaterial(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        UnificationEntry entry = new UnificationEntry(orePrefix, material1);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), 1);
    }

    @ZenMethod("ore")
    public static IIngredient getOreEntry(String orePrefix, String material) {
        OrePrefix orePrefix1 = OrePrefix.getPrefix(orePrefix);
        if (orePrefix1 == null) {
            CraftTweakerAPI.logError("Can't find ore prefix " + orePrefix);
            return MCItemStack.EMPTY;
        }
        Material material1 = GregTechAPI.materialManager.getMaterial(material);
        if (material1 == null) {
            CraftTweakerAPI.logError("Can't find material " + orePrefix);
            return MCItemStack.EMPTY;
        }
        UnificationEntry entry = new UnificationEntry(orePrefix1, material1);
        return new IngredientStack(CraftTweakerMC.getOreDict(entry.toString()), 1);
    }
}
