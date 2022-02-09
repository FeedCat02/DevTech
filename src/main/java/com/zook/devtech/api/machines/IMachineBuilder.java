package com.zook.devtech.api.machines;

import com.zook.devtech.common.machines.CTMachineBuilder;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import eutros.multiblocktweaker.MultiblockTweaker;
import eutros.multiblocktweaker.crafttweaker.gtwrap.interfaces.IICubeRenderer;
import eutros.multiblocktweaker.crafttweaker.gtwrap.interfaces.ITextureArea;
import gregtech.api.recipes.RecipeMap;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * loader gregtech
 */
@ZenClass("mods.gregtech.machine.MachineBuilder")
@ZenRegister
public interface IMachineBuilder {

    @ZenMethod
    static IMachineBuilder create(int id, String name) {
        return new CTMachineBuilder(id, name);
    }

    @ZenMethod
    IMachineBuilder setRecipeMap(RecipeMap<?> recipeMap);

    @ZenMethod
    IMachineBuilder setRenderer(String renderer);

    @ZenMethod
    IMachineBuilder setRenderer(IMachineRenderer machineRenderer);

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    IMachineBuilder setRenderer(IICubeRenderer machineRenderer);

    @ZenMethod
    IMachineBuilder setRenderer(String basePath, String... faces);

    // ULV = 0
    // MAX = 14
    @ZenMethod
    IMachineBuilder addTier(int... tier);

    // ULV = 0
    // MAX = 14
    @ZenMethod
    IMachineBuilder addTierRange(int minTier, int maxTier);

    @ZenMethod
    IMachineBuilder addSteamTier(boolean highPressure, @Optional boolean bricked);

    @ZenMethod
    IMachineBuilder setGenerator();

    @ZenMethod
    IMachineBuilder setMachine();

    @ZenMethod
    IMachineBuilder setTankScalingFunction(ITankScalingFunction tankScalingFunction);

    @ZenMethod // Machines only
    IMachineBuilder setHasFrontFacing(boolean hasFrontFacing);

    @ZenMethod // Generators only
    IMachineBuilder setCanHandleOutputs(boolean canHandleOutputs);

    @ZenMethod
    default IMachineBuilder setSteamProgressBar(boolean highPressure, String path) {
        return setSteamProgressBar(highPressure, path, IMoveType.RIGHT);
    }

    @ZenMethod
    IMachineBuilder setSteamProgressBar(boolean highPressure, String path, IMoveType moveType);

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    default IMachineBuilder setSteamProgressBar(boolean highPressure, ITextureArea textureArea) {
        return setSteamProgressBar(highPressure, textureArea, IMoveType.RIGHT);
    }

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    IMachineBuilder setSteamProgressBar(boolean highPressure, ITextureArea textureArea, IMoveType moveType);

    @ZenMethod
    IMachineBuilder setSteamConversionRate(boolean highPressure, double conversionRate);

    @ZenMethod
    IMachineBuilder setBoilerValues(boolean highPressure, int steamOutput, @Optional(valueLong = -1) int coolDownInterval, @Optional(valueLong = -1) int coolDownRate);

    @ZenMethod
    IMachineBuilder setSteamTankSize(boolean highPressure, int tankSize);

    @ZenMethod
    IMachineBuilder setSlotOverlay(boolean highPressure, String path, boolean isOutput, @Optional boolean isFluid, @Optional boolean isLast);

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    IMachineBuilder setSlotOverlay(boolean highPressure, ITextureArea textureArea, boolean isOutput, @Optional boolean isFluid, @Optional boolean isLast);

    @ZenMethod
    void buildAndRegister();
}
