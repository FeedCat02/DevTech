package com.zook.devtech.common;

import com.zook.devtech.DevTech;
import com.zook.devtech.api.unification.ore.IOreRecipeHandler;
import com.zook.devtech.common.unification.MaterialRegistry;
import crafttweaker.mc1120.events.ScriptRunEvent;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = DevTech.MODID)
public class CommonProxy {

    public static final Map<OrePrefix, Set<Material>> GENERATED_MATERIALS = new HashMap<>();
    public static final Map<OrePrefix, List<IOreRecipeHandler>> REGISTRATION_HANDLERS = new HashMap<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (StoneType stoneType : MaterialRegistry.STONE_TYPE_LIST) {
            stoneType.processingPrefix.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        }
    }

    @SubscribeEvent
    public static void afterScript(ScriptRunEvent.Post event) {
        System.out.println("Run script post");
        for (Map.Entry<OrePrefix, List<IOreRecipeHandler>> handlers : REGISTRATION_HANDLERS.entrySet()) {
            for (Material material : GENERATED_MATERIALS.get(handlers.getKey())) {
                for (IOreRecipeHandler handler : handlers.getValue()) {
                    handler.processMaterial(handlers.getKey(), material);
                }
            }
        }
        GENERATED_MATERIALS.clear();
        REGISTRATION_HANDLERS.clear();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
}
