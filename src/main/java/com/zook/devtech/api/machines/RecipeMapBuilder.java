package com.zook.devtech.api.machines;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import eutros.multiblocktweaker.MultiblockTweaker;
import eutros.multiblocktweaker.crafttweaker.gtwrap.interfaces.ISound;
import eutros.multiblocktweaker.crafttweaker.gtwrap.interfaces.ITextureArea;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.recipe.RecipeMapBuilder")
@ZenRegister
public class RecipeMapBuilder {

    private int maxInputs = 0;
    private int maxOutputs = 0;
    private int maxFluidInputs = 0;
    private int maxFluidOutputs = 0;
    private final String name;
    private boolean isHidden = false;
    private final TByteObjectMap<TextureArea> slotOverlays = new TByteObjectHashMap<>();
    private ProgressWidget.MoveType moveType = ProgressWidget.MoveType.HORIZONTAL;
    private TextureArea progressBarTexture = GuiTextures.PROGRESS_BAR_ARROW;
    private RecipeMap.IChanceFunction chanceFunction;
    private SoundEvent sound;

    public RecipeMapBuilder(String name) {
        this.name = name;
    }

    @ZenMethod
    public static RecipeMapBuilder create(String name) {
        return new RecipeMapBuilder(name);
    }

    @ZenMethod
    public RecipeMapBuilder setInputs(int max, @Optional int min) {
        if (max < 0)
            max = 0;
        this.maxInputs = max;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setOutputs(int max, @Optional int min) {
        if (max < 0)
            max = 0;
        this.maxOutputs = max;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setFluidInputs(int max, @Optional int min) {
        if (max < 0)
            max = 0;
        this.maxFluidInputs = max;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setFluidOutputs(int max, @Optional int min) {
        if (max < 0)
            max = 0;
        this.maxFluidOutputs = max;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setHidden(boolean hidden) {
        isHidden = hidden;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setDurationBar(String path, @Optional IMoveType moveType) {
        progressBarTexture = TextureArea.fullImage(path);
        if (moveType != null)
            this.moveType = moveType.moveType;
        return this;
    }

    @net.minecraftforge.fml.common.Optional.Method(modid = MultiblockTweaker.MOD_ID)
    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setDurationBar(ITextureArea textureArea, @Optional IMoveType moveType) {
        progressBarTexture = textureArea.getInternal();
        if (moveType != null)
            this.moveType = moveType.moveType;
        return this;
    }

    @net.minecraftforge.fml.common.Optional.Method(modid = MultiblockTweaker.MOD_ID)
    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setSlotOverlay(boolean isOutput, boolean isFluid, ITextureArea slotOverlay) {
        return this.setSlotOverlay(isOutput, isFluid, false, slotOverlay).setSlotOverlay(isOutput, isFluid, true, slotOverlay);
    }

    @net.minecraftforge.fml.common.Optional.Method(modid = MultiblockTweaker.MOD_ID)
    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setSlotOverlay(boolean isOutput, boolean isFluid, boolean isLast, ITextureArea slotOverlay) {
        slotOverlays.put((byte) ((isOutput ? 2 : 0) + (isFluid ? 1 : 0) + (isLast ? 4 : 0)), slotOverlay.getInternal());
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setChanceFunction(RecipeMap.IChanceFunction chanceFunction) {
        this.chanceFunction = chanceFunction;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setSound(String name) {
        ResourceLocation loc = new ResourceLocation(name);
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(loc);
        if (sound != null)
            this.sound = sound;
        return this;
    }

    @net.minecraftforge.fml.common.Optional.Method(modid = MultiblockTweaker.MOD_ID)
    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setSound(ISound sound) {
        this.sound = sound.getInternal();
        return this;
    }

    @ZenMethod
    public RecipeMap<?> build() {
        RecipeMap<?> recipeMap = new RecipeMap<>(name,
                maxInputs,
                maxOutputs,
                maxFluidInputs,
                maxFluidOutputs,
                new SimpleRecipeBuilder().duration(100).EUt(1),
                isHidden);
        if (this.maxOutputs == 0 && this.maxFluidOutputs == 0) {
            recipeMap.allowEmptyOutput();
        }
        for (byte key : slotOverlays.keys()) {
            recipeMap.setSlotOverlay((key & 2) != 0, (key & 1) != 0, (key & 4) != 0, slotOverlays.get(key));
        }
        if (progressBarTexture != null && moveType != null) {
            recipeMap.setProgressBar(progressBarTexture, moveType);
        }
        if (chanceFunction != null) {
            recipeMap.chanceFunction = chanceFunction;
        }
        if (sound != null) {
            recipeMap.setSound(sound);
        }
        return recipeMap;
    }
}
