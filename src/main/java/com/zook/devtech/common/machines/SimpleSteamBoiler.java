package com.zook.devtech.common.machines;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.zook.devtech.common.machines.recipeLogic.BoilerRecipeLogic;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.DoubleSupplier;

public class SimpleSteamBoiler extends MetaTileEntity {

    private final SteamMachineData data;
    private final RecipeMap<?> recipeMap;
    private final ICubeRenderer overlayRenderer;
    private BoilerRecipeLogic workable;
    private IFluidTank waterTank;
    private IFluidTank steamTank;

    public SimpleSteamBoiler(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, SteamMachineData data) {
        super(metaTileEntityId);
        this.data = data;
        this.recipeMap = recipeMap;
        this.overlayRenderer = renderer;
        if (data.progressBar == null) {
            data.progressBar = GuiTextures.PROGRESS_BAR_ARROW_STEAM.get(data.highPressure);
        }
        if (data.moveType == null) {
            data.moveType = ProgressWidget.MoveType.HORIZONTAL;
        }
        if (data.steamOutput < 0) {
            data.steamOutput = data.highPressure ? 240 : 100;
        }
        if (data.cooldownInterval < 0) {
            data.cooldownInterval = data.highPressure ? 40 : 45;
        }
        if (data.cooldownRate < 0) {
            data.cooldownRate = 1;
        }
        initializeInventory();
        this.workable = new BoilerRecipeLogic(this, recipeMap, data.highPressure, data.steamOutput, data.cooldownInterval, data.cooldownRate, steamTank, waterTank);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new SimpleSteamBoiler(metaTileEntityId, recipeMap, overlayRenderer, data);
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        if (data.highPressure) {
            return data.bricked ? Textures.STEAM_BRICKED_CASING_STEEL : Textures.STEAM_CASING_STEEL;
        } else {
            return data.bricked ? Textures.STEAM_BRICKED_CASING_BRONZE : Textures.STEAM_CASING_BRONZE;
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        this.getBaseRenderer().render(renderState, translation, colouredPipeline);
        this.overlayRenderer.renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.workable.isActive(), this.workable.isWorkingEnabled());
    }

    public int getDefaultPaintingColor() {
        return 0xFFFFFF;
    }

    public ModularUI.Builder createUITemplate(EntityPlayer player) {
        RecipeMap<?> workableRecipeMap = workable.getRecipeMap();
        int yOffset = 0;
        if (workableRecipeMap.getMaxInputs() >= 6 || workableRecipeMap.getMaxFluidInputs() >= 7 || workableRecipeMap.getMaxOutputs() >= 6 || workableRecipeMap.getMaxFluidOutputs() >= 6) {
            yOffset = 9;
        }
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND_STEAM.get(data.highPressure), 176, 166 + yOffset)
                .label(5, 5, getMetaFullName())
                .widget(new TankWidget(waterTank, 83, 46, 10, 34)
                        .setBackgroundTexture(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(data.highPressure)))
                .widget(new TankWidget(steamTank, 70, 46, 10, 34)
                        .setBackgroundTexture(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(data.highPressure)))
                .widget(new ProgressWidget(workable::getTemperaturePercent, 96, 46, 10, 34)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(data.highPressure),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT,
                                ProgressWidget.MoveType.VERTICAL))
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT_STEAM.get(data.highPressure), yOffset);
        buildRecipeUI(builder, workable::getProgressPercent, getImportItems(), getExportItems(), getImportFluids(), getExportFluids(), yOffset);
        return builder;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer).build(getHolder(), entityPlayer);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        if (recipeMap == null) return new ItemStackHandler(0);
        return new NotifiableItemStackHandler(recipeMap.getMaxInputs(), this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        if (recipeMap == null) return new ItemStackHandler(0);
        return new NotifiableItemStackHandler(recipeMap.getMaxOutputs(), this, true);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        if (recipeMap == null) {
            return new FluidTankList(false);
        }
        this.waterTank = new FilteredFluidHandler(16000).setFilter(CommonFluidFilters.BOILER_FLUID);
        FilteredFluidHandler[] fluidImports = new FilteredFluidHandler[recipeMap.getMaxFluidInputs() + 1];
        fluidImports[0] = (FilteredFluidHandler) this.waterTank;
        for (int i = 1; i < fluidImports.length; i++) {
            NotifiableFilteredFluidHandler filteredFluidHandler = new NotifiableFilteredFluidHandler(data.tankSize, this, false);
            fluidImports[i] = filteredFluidHandler;
        }
        return new FluidTankList(false, fluidImports);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        if (recipeMap == null)
            return new FluidTankList(false);
        this.steamTank = new FilteredFluidHandler(16000).setFilter(CommonFluidFilters.STEAM);
        FluidTank[] fluidExports = new FluidTank[recipeMap.getMaxFluidOutputs() + 1];
        fluidExports[0] = (FluidTank) this.steamTank;
        for (int i = 0; i < fluidExports.length; i++) {
            fluidExports[i] = new NotifiableFluidTank(data.tankSize, this, true);
        }
        return new FluidTankList(false, fluidExports);
    }


    public void buildRecipeUI(ModularUI.Builder builder, DoubleSupplier progressSupplier, IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        builder.widget(new RecipeProgressWidget(progressSupplier, 78, 23 + yOffset, 20, 20, data.progressBar, data.moveType, recipeMap));
        addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
    }

    //this DOES NOT include machine control widgets or binds player inventory
    public void buildRecipeUI(ModularUI.Builder builder, DoubleSupplier progressSupplier, IItemHandlerModifiable importItems, int yOffset) {
        builder.widget(new RecipeProgressWidget(progressSupplier, 78, 23 + yOffset, 20, 20, data.progressBar, data.moveType, recipeMap));
        addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
    }

    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler, FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int itemInputsCount = itemHandler.getSlots();
        int fluidInputsCount = fluidHandler.getTanks() - 1;
        boolean invertFluids = false;
        if (itemInputsCount == 0) {
            int tmp = itemInputsCount;
            itemInputsCount = fluidInputsCount;
            fluidInputsCount = tmp;
            invertFluids = true;
        }
        int[] inputSlotGrid = determineSlotsGrid(itemInputsCount);
        int itemSlotsToLeft = inputSlotGrid[0];
        int itemSlotsToDown = inputSlotGrid[1];
        int startInputsX = isOutputs ? 106 : 70 - itemSlotsToLeft * 18;
        int startInputsY = 33 - (int) (itemSlotsToDown / 2.0 * 18) + yOffset;
        boolean wasGroup = itemHandler.getSlots() + fluidInputsCount == 12;
        if (wasGroup) startInputsY -= 9;
        else if (itemHandler.getSlots() >= 6 && fluidInputsCount >= 2 && !isOutputs) startInputsY -= 9;
        for (int i = 0; i < itemSlotsToDown; i++) {
            for (int j = 0; j < itemSlotsToLeft; j++) {
                int slotIndex = i * itemSlotsToLeft + j;
                if (slotIndex >= itemInputsCount) break;
                int x = startInputsX + 18 * j;
                int y = startInputsY + 18 * i;
                addSlot(builder, x, y, slotIndex, itemHandler, fluidHandler, invertFluids, isOutputs);
            }
        }
        if (wasGroup) startInputsY += 2;
        if (fluidInputsCount > 0 || invertFluids) {
            if (itemSlotsToDown >= fluidInputsCount && itemSlotsToLeft < 3) {
                int startSpecX = isOutputs ? startInputsX + itemSlotsToLeft * 18 : startInputsX - 18;
                for (int i = 0; i < fluidInputsCount; i++) {
                    int y = startInputsY + 18 * i;
                    addSlot(builder, startSpecX, y, i, itemHandler, fluidHandler, !invertFluids, isOutputs);
                }
            } else {
                int startSpecY = startInputsY + itemSlotsToDown * 18;
                for (int i = 0; i < fluidInputsCount; i++) {
                    int x = isOutputs ? startInputsX + 18 * (i % 3) : startInputsX + itemSlotsToLeft * 18 - 18 - 18 * (i % 3);
                    int y = startSpecY + (i / 3) * 18;
                    addSlot(builder, x, y, i, itemHandler, fluidHandler, !invertFluids, isOutputs);
                }
            }
        }
    }

    protected void addSlot(ModularUI.Builder builder, int x, int y, int slotIndex, IItemHandlerModifiable itemHandler, FluidTankList fluidHandler, boolean isFluid, boolean isOutputs) {
        if (!isFluid) {
            builder.widget(new SlotWidget(itemHandler, slotIndex, x, y, true, !isOutputs)
                    .setBackgroundTexture(getOverlaysForSlot(isOutputs, false, slotIndex == itemHandler.getSlots() - 1)));
        } else {
            slotIndex++;
            builder.widget(new TankWidget(fluidHandler.getTankAt(slotIndex), x, y, 18, 18)
                    .setAlwaysShowFull(true)
                    .setBackgroundTexture(getOverlaysForSlot(isOutputs, true, slotIndex == fluidHandler.getTanks() - 1))
                    .setContainerClicking(true, !isOutputs));
        }
    }

    protected TextureArea[] getOverlaysForSlot(boolean isOutput, boolean isFluid, boolean isLast) {
        TextureArea base = isFluid ? GuiTextures.SLOT_STEAM.get(data.highPressure) : GuiTextures.SLOT_STEAM.get(data.highPressure);
        byte overlayKey = (byte) ((isOutput ? 2 : 0) + (isFluid ? 1 : 0) + (isLast ? 4 : 0));
        if (data.slotOverlays.containsKey(overlayKey)) {
            return new TextureArea[]{base, data.slotOverlays.get(overlayKey)};
        }
        return new TextureArea[]{base};
    }

    protected static int[] determineSlotsGrid(int itemInputsCount) {
        int itemSlotsToLeft;
        int itemSlotsToDown;
        double sqrt = Math.sqrt(itemInputsCount);
        //if the number of input has an integer root
        //return it.
        if (sqrt % 1 == 0) {
            itemSlotsToLeft = itemSlotsToDown = (int) sqrt;
        } else if (itemInputsCount == 3) {
            itemSlotsToLeft = 3;
            itemSlotsToDown = 1;
        } else {
            //if we couldn't fit all into a perfect square,
            //increase the amount of slots to the left
            itemSlotsToLeft = (int) Math.ceil(sqrt);
            itemSlotsToDown = itemSlotsToLeft - 1;
            //if we still can't fit all the slots in a grid,
            //increase the amount of slots on the bottom
            if (itemInputsCount > itemSlotsToLeft * itemSlotsToDown) {
                itemSlotsToDown = itemSlotsToLeft;
            }
        }
        return new int[]{itemSlotsToLeft, itemSlotsToDown};
    }
}
