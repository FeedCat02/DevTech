package com.zook.devtech;

import codechicken.lib.CodeChickenLib;
import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DevTech.MODID,
        name = DevTech.NAME,
        version = DevTech.VERSION,
        dependencies = "required:forge@[14.23.5.2847,);" + CodeChickenLib.MOD_VERSION_DEP + GTValues.MOD_VERSION_DEP + "after:crafttweaker")
public class DevTech {
    public static final String MODID = "devtech";
    public static final String NAME = "DevTech";
    public static final String VERSION = "2.0.0";

    @SidedProxy(modId = GTValues.MODID, clientSide = "com.zook.devtech.client.ClientProxy", serverSide = "com.zook.devtech.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // init recipe maps, so they are ready when ct asks for it
        RecipeMap<?> recipeMap = RecipeMaps.ASSEMBLER_RECIPES;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
