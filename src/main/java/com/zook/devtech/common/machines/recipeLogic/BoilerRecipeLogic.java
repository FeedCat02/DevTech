package com.zook.devtech.common.machines.recipeLogic;

import gregtech.api.capability.impl.RecipeLogicSteam;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.ParallelLogicType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nonnull;

public class BoilerRecipeLogic extends RecipeLogicSteam {

    private final boolean isHighPressure;
    private int currentTemperature;
    private boolean hasNoWater;
    private int timeBeforeCoolingDown;
    private final int steamOutput;
    private final int cooldownInterval;
    private final int cooldownRate;
    private final IFluidTank waterFluidTank;
    private final IFluidTank steamFluidTank;

    public BoilerRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean isHighPressure, int steamOutput, int cooldownInterval, int cooldownRate, IFluidTank steamFluidTank, IFluidTank waterFluidTank) {
        super(tileEntity, recipeMap, isHighPressure, steamFluidTank, 1);
        this.isHighPressure = isHighPressure;
        this.steamOutput = steamOutput;
        this.cooldownInterval = cooldownInterval;
        this.cooldownRate = cooldownRate;
        this.steamFluidTank = steamFluidTank;
        this.waterFluidTank = waterFluidTank;
    }

    @Override
    protected int[] runOverclockingLogic(IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int duration, int maxOverclocks) {
        // no overclocking happens other than parallelization,
        // so return the recipe's values, with EUt made positive for it to be made negative later
        return new int[]{-recipeEUt, duration};
    }

    @Override
    public Enum<ParallelLogicType> getParallelLogicType() {
        return ParallelLogicType.MULTIPLY; //TODO APPEND_FLUIDS
    }

    @Override
    protected boolean hasEnoughPower(@Nonnull int[] resultOverclock) {
        // generators always have enough power to run recipes
        return true;
    }

    @Override
    public void applyParallelBonus(@Nonnull RecipeBuilder<?> builder) {
        // the builder automatically multiplies by -1, so nothing extra is needed here
        builder.EUt(builder.getEUt());
    }

    @Override
    public int getParallelLimit() {
        // parallel is limited by voltage
        return 1;
    }

    @Override
    protected boolean drawEnergy(int recipeEUt, boolean simulate) {
        return true;
    }


    public World getWorld() {
        return metaTileEntity.getWorld();
    }

    // disable venting
    @Override
    public void setNeedsVenting(boolean needsVenting) {
    }

    @Override
    public boolean isNeedsVenting() {
        return false;
    }

    @Override
    public boolean isVentingStuck() {
        return false;
    }

    @Override
    public void tryDoVenting() {
    }

    @Override
    public void update() {
        super.update();
        World world = metaTileEntity.getWorld();
        if (world != null && !world.isRemote) {
            if (!isWorking()) {
                if (timeBeforeCoolingDown == 0) {
                    currentTemperature -= cooldownRate;
                    timeBeforeCoolingDown = cooldownInterval;
                } else {
                    --timeBeforeCoolingDown;
                }
            }
        }
    }

    public int getFuelBurnTimeLeft() {
        return maxProgressTime - progressTime;
    }

    @Override
    protected void updateRecipeProgress() {
        if (this.canRecipeProgress) {
            if (metaTileEntity.getOffsetTimer() % 10 == 0) {
                generateSteam();
            }
            if (metaTileEntity.getOffsetTimer() % 12 == 0) {
                if (currentTemperature < getMaxTemperate())
                    currentTemperature++;
            }
            //this.drawEnergy(this.recipeEUt, false);
            this.progressTime += isHighPressure ? 2 : 1;
            if (this.progressTime > this.maxProgressTime) {
                this.completeRecipe();
            }
        }
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        this.timeBeforeCoolingDown = this.cooldownInterval;
    }

    private void generateSteam() {
        if (currentTemperature >= 100) {
            int fillAmount = (int) (steamOutput * (currentTemperature / (getMaxTemperate() * 1.0)) / 2);
            boolean hasDrainedWater = waterFluidTank.drain(1, true) != null;
            int filledSteam = 0;
            if (hasDrainedWater) {
                filledSteam = steamFluidTank.fill(ModHandler.getSteam(fillAmount), true);
            }
            if (this.hasNoWater && hasDrainedWater) {
                metaTileEntity.doExplosion(2.0f);
            } else {
                this.hasNoWater = !hasDrainedWater;
            }
            if (filledSteam == 0 && hasDrainedWater) {
                getWorld().playSound(null, metaTileEntity.getPos().getX() + 0.5, metaTileEntity.getPos().getY() + 0.5, metaTileEntity.getPos().getZ() + 0.5,
                        SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                steamFluidTank.drain(4000, true);
            }
        } else {
            this.hasNoWater = false;
        }
    }

    public int getMaxTemperate() {
        return isHighPressure ? 1000 : 500;
    }

    public double getTemperaturePercent() {
        return currentTemperature / (getMaxTemperate() * 1.0);
    }

    public double getFuelLeftPercent() {
        return maxProgressTime == 0 ? 0.0 : getFuelBurnTimeLeft() / (maxProgressTime * 1.0);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound data = super.serializeNBT();
        data.setInteger("CurrentTemperature", currentTemperature);
        data.setBoolean("HasNoWater", hasNoWater);
        return data;
    }

    @Override
    public void deserializeNBT(NBTTagCompound data) {
        super.deserializeNBT(data);
        this.currentTemperature = data.getInteger("CurrentTemperature");
        this.hasNoWater = data.getBoolean("HasNoWater");
    }
}
