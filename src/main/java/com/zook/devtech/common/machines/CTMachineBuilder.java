package com.zook.devtech.common.machines;

import com.zook.devtech.DevTech;
import com.zook.devtech.api.machines.IMachineBuilder;
import com.zook.devtech.api.machines.IMachineRenderer;
import com.zook.devtech.api.machines.IMoveType;
import com.zook.devtech.api.machines.ITankScalingFunction;
import crafttweaker.CraftTweakerAPI;
import eutros.multiblocktweaker.crafttweaker.gtwrap.interfaces.IICubeRenderer;
import eutros.multiblocktweaker.crafttweaker.gtwrap.interfaces.ITextureArea;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CTMachineBuilder implements IMachineBuilder {

    public static final Map<String, IMachineRenderer> RENDERER_MAP = new HashMap<>();

    static {
        for (Map.Entry<String, ICubeRenderer> entry : Textures.CUBE_RENDERER_REGISTRY.entrySet()) {
            RENDERER_MAP.put(entry.getKey(), new MachineRenderer(entry.getValue()));
        }
    }

    private final String name;
    private final int id;
    private RecipeMap<?> recipeMap;
    private IMachineRenderer renderer;
    private final Set<Integer> tiers = new HashSet<>();
    private boolean hasFrontFacing;
    private boolean canHandleOutputs;
    private Function<Integer, Integer> tankScalingFunction;
    private boolean generator;
    private SteamMachineData bronzeMachineData;
    private SteamMachineData steelMachineData;

    public CTMachineBuilder(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public IMachineBuilder setRecipeMap(RecipeMap<?> recipeMap) {
        this.recipeMap = recipeMap;
        return this;
    }

    @Override
    public IMachineBuilder setRenderer(String renderer) {
        this.renderer = RENDERER_MAP.get(renderer);
        if (this.renderer == null) {
            CraftTweakerAPI.logError("Could not find renderer with name " + renderer);
        }
        return this;
    }

    @Override
    public IMachineBuilder setRenderer(IMachineRenderer machineRenderer) {
        this.renderer = machineRenderer;
        return this;
    }

    @Override
    public IMachineBuilder setRenderer(IICubeRenderer machineRenderer) {
        this.renderer = new MachineRenderer(machineRenderer);
        return this;
    }

    @Override
    public IMachineBuilder setRenderer(String basePath, String... faces) {
        this.renderer = IMachineRenderer.create(basePath, faces);
        return this;
    }

    @Override
    public IMachineBuilder addTier(int... tier) {
        for (int t : tier) {
            if (t > 14) {
                CraftTweakerAPI.logError("Can't add tiers above 14 (MAX). Machine ID: " + name + ", " + id);
                continue;
            }
            if (t < 0) {
                CraftTweakerAPI.logError("Can't add tiers below 0. Machine ID: " + name + ", " + id);
                continue;
            }
            this.tiers.add(t);
        }
        return this;
    }

    @Override
    public IMachineBuilder addTierRange(int minTier, int maxTier) {
        if (maxTier > 14) {
            CraftTweakerAPI.logError("Can't add tiers above 14 (MAX). Machine ID: " + name + ", " + id);
            return this;
        }
        if (minTier < 0) {
            CraftTweakerAPI.logError("Can't add tiers below 0. Machine ID: " + name + ", " + id);
            return this;
        }
        maxTier++;
        for (int i = minTier; i < maxTier; i++) {
            this.tiers.add(i);
        }
        return this;
    }

    @Override
    public IMachineBuilder addSteamTier(boolean highPressure, boolean bricked) {
        checkSteamData(highPressure).bricked = bricked;
        return this;
    }

    @Override
    public IMachineBuilder setGenerator() {
        generator = true;
        return this;
    }

    @Override
    public IMachineBuilder setMachine() {
        generator = false;
        return this;
    }

    private SteamMachineData checkSteamData(boolean highPressure) {
        if (!highPressure) {
            if (bronzeMachineData == null) {
                bronzeMachineData = new SteamMachineData();
                bronzeMachineData.highPressure = false;
            }
            return bronzeMachineData;
        }
        if (steelMachineData == null) {
            steelMachineData = new SteamMachineData();
            steelMachineData.highPressure = true;
        }
        return steelMachineData;
    }

    @Override
    public IMachineBuilder setTankScalingFunction(ITankScalingFunction tankScalingFunction) {
        this.tankScalingFunction = tankScalingFunction;
        return this;
    }

    @Override
    public IMachineBuilder setHasFrontFacing(boolean hasFrontFacing) {
        this.hasFrontFacing = hasFrontFacing;
        return this;
    }

    @Override
    public IMachineBuilder setCanHandleOutputs(boolean canHandleOutputs) {
        this.canHandleOutputs = canHandleOutputs;
        return this;
    }

    @Override
    public IMachineBuilder setSteamProgressBar(boolean highPressure, String path, IMoveType moveType) {
        checkSteamData(highPressure).progressBar = TextureArea.fullImage(path);
        checkSteamData(highPressure).moveType = moveType.moveType;
        return this;
    }

    @Override
    public IMachineBuilder setSteamProgressBar(boolean highPressure, ITextureArea textureArea, IMoveType moveType) {
        checkSteamData(highPressure).progressBar = textureArea.getInternal();
        checkSteamData(highPressure).moveType = moveType.moveType;
        return this;
    }

    @Override
    public IMachineBuilder setSteamConversionRate(boolean highPressure, double conversionRate) {
        checkSteamData(highPressure).conversionRate = conversionRate;
        return this;
    }

    @Override
    public IMachineBuilder setBoilerValues(boolean highPressure, int steamOutput, int coolDownInterval, int coolDownRate) {
        checkSteamData(highPressure).steamOutput = steamOutput;
        checkSteamData(highPressure).cooldownInterval = coolDownInterval;
        checkSteamData(highPressure).cooldownRate = coolDownRate;
        return this;
    }

    @Override
    public IMachineBuilder setSteamTankSize(boolean highPressure, int tankSize) {
        checkSteamData(highPressure).tankSize = tankSize;
        return this;
    }

    @Override
    public IMachineBuilder setSlotOverlay(boolean highPressure, String path, boolean isOutput, boolean isFluid, boolean isLast) {
        checkSteamData(highPressure).setSlotOverlay(isOutput, isFluid, isLast, TextureArea.fullImage(path));
        return this;
    }

    @Override
    public IMachineBuilder setSlotOverlay(boolean highPressure, ITextureArea textureArea, boolean isOutput, boolean isFluid, boolean isLast) {
        checkSteamData(highPressure).setSlotOverlay(isOutput, isFluid, isLast, textureArea.getInternal());
        return this;
    }

    @Override
    public void buildAndRegister() {
        if (this.recipeMap == null) {
            CraftTweakerAPI.logError("Can't create Machine with null RecipeMap. Machine ID: " + name + ", " + id);
            return;
        }

        if (this.renderer == null) {
            CraftTweakerAPI.logError("Can't create Machine with null Renderer. Machine ID: " + name + ", " + id);
            return;
        }

        if (generator) {
            if (this.tankScalingFunction == null) {
                this.tankScalingFunction = GTUtility.genericGeneratorTankSizeFunction;
            }
            for (int tier : tiers) {
                registerGenerator(tier);
            }
            if (bronzeMachineData != null) {
                registerSteamBoiler(false);
            }
            if (steelMachineData != null) {
                registerSteamBoiler(true);
            }
        } else {
            if (this.tankScalingFunction == null) {
                this.tankScalingFunction = GTUtility.defaultTankSizeFunction;
            }
            for (int tier : tiers) {
                registerMachine(tier);
            }
            if (bronzeMachineData != null) {
                registerSteamMachine(false);
            }
            if (steelMachineData != null) {
                registerSteamMachine(true);
            }
        }
    }

    private void registerMachine(int tier) {
        ResourceLocation rl = new ResourceLocation(DevTech.MODID, name + "." + GTValues.VN[tier].toLowerCase());
        MetaTileEntity mte = new SimpleMachineMetaTileEntity(rl, recipeMap, renderer.getActualRenderer(), tier, this.hasFrontFacing, this.tankScalingFunction);
        GregTechAPI.MTE_REGISTRY.register(id + tier + 2, rl, mte);
    }

    private void registerGenerator(int tier) {
        ResourceLocation rl = new ResourceLocation(DevTech.MODID, name + "." + GTValues.VN[tier].toLowerCase());
        MetaTileEntity mte = new SimpleGeneratorMetaTileEntity(rl, recipeMap, renderer.getActualRenderer(), tier, this.tankScalingFunction, this.canHandleOutputs);
        GregTechAPI.MTE_REGISTRY.register(id + tier + 2, rl, mte);
    }

    private void registerSteamMachine(boolean highPressure) {
        ResourceLocation rl = new ResourceLocation(DevTech.MODID, name + "." + (highPressure ? "steel" : "bronze"));
        MetaTileEntity mte = new SimpleSteamMachine(rl, recipeMap, renderer.getActualRenderer(), highPressure ? steelMachineData : bronzeMachineData);
        GregTechAPI.MTE_REGISTRY.register(id + (highPressure ? 1 : 0), rl, mte);
    }

    private void registerSteamBoiler(boolean highPressure) {
        ResourceLocation rl = new ResourceLocation(DevTech.MODID, name + "." + (highPressure ? "steel" : "bronze"));
        MetaTileEntity mte = new SimpleSteamBoiler(rl, recipeMap, renderer.getActualRenderer(), highPressure ? steelMachineData : bronzeMachineData);
        GregTechAPI.MTE_REGISTRY.register(id + (highPressure ? 1 : 0), rl, mte);
    }
}
