package com.zook.devtech;

import codechicken.lib.CodeChickenLib;
import gregtech.GTInternalTags;
import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DevTech.MODID,
        name = DevTech.NAME,
        version = DevTech.VERSION,
        dependencies = "required:forge@[14.23.5.2847,);" + CodeChickenLib.MOD_VERSION_DEP + GTInternalTags.DEP_VERSION_STRING + "after:crafttweaker")
public class DevTech {

    public static final String MODID = Tags.ID;
    public static final String NAME = "DevTech";
    public static final String VERSION = Tags.VERSION;

    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(modId = GTValues.MODID, clientSide = "com.zook.devtech.client.ClientProxy", serverSide = "com.zook.devtech.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(RecipeMaps.ASSEMBLER_RECIPES.unlocalizedName);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
