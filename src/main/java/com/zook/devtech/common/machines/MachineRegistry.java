package com.zook.devtech.common.machines;

import com.zook.devtech.DevTech;
import com.zook.devtech.api.machines.IMachineRenderer;
import crafttweaker.CraftTweakerAPI;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MachineRegistry {

    public static final Map<String, IMachineRenderer> RENDERER_MAP = new HashMap<>();

    public static void createSimple(int id, String name, RecipeMap<?> recipeMap, String renderer, int tier) {
        IMachineRenderer renderer1 = RENDERER_MAP.get(renderer);
        if (renderer1 == null) {
            CraftTweakerAPI.logError("Could not find renderer with name " + renderer);
            return;
        }
        createSimple(id, name, recipeMap, renderer1, tier);
    }

    public static void createSimple(int id, String name, RecipeMap<?> recipeMap, IMachineRenderer renderer, int tier) {
        if (tier < 0) {
            CraftTweakerAPI.logError("tier can't be lower than 0");
            return;
        }
        if (tier > 14) {
            CraftTweakerAPI.logError("tier can't be higher than 14");
            return;
        }
        SimpleMachineMetaTileEntity mte = new SimpleMachineMetaTileEntity(new ResourceLocation(DevTech.MODID, name), recipeMap, renderer.getActualRenderer(), tier, true);
        GregTechAPI.MTE_REGISTRY.register(id, mte.metaTileEntityId, mte);
    }

    public static void createSimpleGenerator(int id, String name, RecipeMap<?> recipeMap, String renderer, int tier, boolean canHandleOutputs) {
        IMachineRenderer renderer1 = RENDERER_MAP.get(renderer);
        if (renderer1 == null) {
            CraftTweakerAPI.logError("Could not find renderer with name " + renderer);
            return;
        }
        createSimple(id, name, recipeMap, renderer1, tier);
    }

    public static void createSimpleGenerator(int id, String name, RecipeMap<?> recipeMap, IMachineRenderer renderer, int tier, boolean canHandleOutputs) {
        if (tier < 0) {
            CraftTweakerAPI.logError("tier can't be lower than 0");
            return;
        }
        if (tier > 14) {
            CraftTweakerAPI.logError("tier can't be higher than 14");
            return;
        }
        SimpleGeneratorMetaTileEntity mte = new SimpleGeneratorMetaTileEntity(new ResourceLocation(DevTech.MODID, name), recipeMap, renderer.getActualRenderer(), tier, GTUtility.genericGeneratorTankSizeFunction, canHandleOutputs);
        ;
        GregTechAPI.MTE_REGISTRY.register(id, mte.metaTileEntityId, mte);
    }

    static {
        for (Map.Entry<String, ICubeRenderer> entry : Textures.CUBE_RENDERER_REGISTRY.entrySet()) {
            if (entry.getValue() instanceof OrientedOverlayRenderer) {
                RENDERER_MAP.put(entry.getKey(), new MachineRenderer(entry.getValue()));
            }
        }
    }
}
