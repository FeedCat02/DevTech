package com.zook.devtech.api.items;

import com.zook.devtech.common.items.DTMetaItem;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.items.metaitem.FoodStats;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

/**
 * loader crafttweaker
 */
@ZenClass("mods.gregtech.MetaItem")
@ZenRegister
public class MetaItem {

    private final gregtech.api.items.metaitem.MetaItem<?>.MetaValueItem item;

    public MetaItem(gregtech.api.items.metaitem.MetaItem<?>.MetaValueItem item) {
        this.item = item;
    }

    @Nullable
    @ZenMethod
    public static MetaItem getByName(String name) {
        for (gregtech.api.items.metaitem.MetaItem<?> metaItem : gregtech.api.items.metaitem.MetaItem.getMetaItems()) {
            gregtech.api.items.metaitem.MetaItem<?>.MetaValueItem metaValueItem = metaItem.getItem(name);
            if (metaValueItem != null) {
                return new MetaItem(metaValueItem);
            }
        }
        return null;
    }

    @ZenMethod("create")
    public static MetaItem addItem(short id, String name) {
        return new MetaItem(DTMetaItem.META_ITEM.addItem(id, name));
    }

    @ZenMethod
    public MetaItem setModel(String location) {
        // TODO
        return this;
    }

    @ZenMethod
    public MetaItem foodStats(int foodLevel, float saturation, @Optional boolean isDrink) {
        item.addComponents(new FoodStats(foodLevel, saturation, isDrink));
        return this;
    }

    @ZenMethod
    public MetaItem electricItem(long maxCharge,
                                 int tier,
                                 @Optional(valueBoolean = true) boolean rechargeable,
                                 @Optional(valueBoolean = true) boolean dischrgeable) {
        item.addComponents(new ElectricStats(maxCharge, tier, rechargeable, dischrgeable));
        return this;
    }

    @ZenMethod
    public MetaItem fluidItem(int maxCapacity,
                              @Optional boolean gasProof,
                              @Optional boolean acidProof,
                              @Optional boolean cryoProof,
                              @Optional boolean plasmaProof,
                              @Optional(valueBoolean = true) boolean allowPartlyFill,
                              @Optional(valueLong = Integer.MAX_VALUE) int maxFluidTemperature) {
        item.addComponents(new FluidStats(maxCapacity, maxFluidTemperature, gasProof, acidProof, cryoProof, plasmaProof, allowPartlyFill));
        return this;
    }

    @ZenMethod
    public MetaItem oreDict(String oreDictName) {
        item.addOreDict(oreDictName);
        return this;
    }

    @ZenMethod
    public MetaItem invisible() {
        item.setInvisible();
        return this;
    }

    @ZenMethod
    public MetaItem stackSize(int maxStackSize) {
        item.setMaxStackSize(maxStackSize);
        return this;
    }

    @ZenMethod
    public MetaItem burnTime(int burnValue) {
        item.setBurnValue(burnValue);
        return this;
    }
}
