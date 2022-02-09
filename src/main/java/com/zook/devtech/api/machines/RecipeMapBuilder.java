package com.zook.devtech.api.machines;

import com.zook.devtech.common.machines.recipe.CTRecipeBuilder;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.recipe.RecipeMapBuilder")
@ZenRegister
public class RecipeMapBuilder {

    private int minInputs = 0, maxInputs = 0, minOutputs = 0, maxOutputs = 0, minFluidInputs = 0, maxFluidInputs = 0, minFluidOutputs = 0, maxFluidOutputs = 0;
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
        if (min < 0)
            min = 0;
        if (max < 0)
            max = 0;
        if (min > max)
            min = max;
        this.maxInputs = max;
        this.minInputs = min;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setOutputs(int max, @Optional int min) {
        if (min < 0)
            min = 0;
        if (max < 0)
            max = 0;
        if (min > max)
            min = max;
        this.maxOutputs = max;
        this.minOutputs = min;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setFluidInputs(int max, @Optional int min) {
        if (min < 0)
            min = 0;
        if (max < 0)
            max = 0;
        if (min > max)
            min = max;
        this.maxFluidInputs = max;
        this.minFluidInputs = min;
        return this;
    }

    @ZenMethod
    public RecipeMapBuilder setFluidOutputs(int max, @Optional int min) {
        if (min < 0)
            min = 0;
        if (max < 0)
            max = 0;
        if (min > max)
            min = max;
        this.maxFluidOutputs = max;
        this.minFluidOutputs = min;
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

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setDurationBar(ITextureArea textureArea, @Optional IMoveType moveType) {
        progressBarTexture = textureArea.getInternal();
        if (moveType != null)
            this.moveType = moveType.moveType;
        return this;
    }

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setSlotOverlay(boolean isOutput, boolean isFluid, ITextureArea slotOverlay) {
        return this.setSlotOverlay(isOutput, isFluid, false, slotOverlay).setSlotOverlay(isOutput, isFluid, true, slotOverlay);
    }

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

    @ModOnly(MultiblockTweaker.MOD_ID)
    @ZenMethod
    public RecipeMapBuilder setSound(ISound sound) {
        this.sound = sound.getInternal();
        return this;
    }

    @ZenMethod
    public RecipeMap<?> build() {
        RecipeMap<?> recipeMap = new RecipeMap<>(name,
                minInputs,
                maxInputs,
                minOutputs,
                maxOutputs,
                minFluidInputs,
                maxFluidInputs,
                minFluidOutputs,
                maxFluidOutputs,
                new CTRecipeBuilder().duration(100).EUt(1),
                isHidden);
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
