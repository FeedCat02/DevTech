package com.zook.devtech.api.machines;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.gui.widgets.ProgressWidget;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.gregtech.machine.MoveType")
@ZenRegister
public enum IMoveType {

    @ZenProperty
    RIGHT(ProgressWidget.MoveType.HORIZONTAL),
    @ZenProperty
    UP(ProgressWidget.MoveType.VERTICAL),
    @ZenProperty
    DOWN(ProgressWidget.MoveType.VERTICAL_INVERTED);



    public final ProgressWidget.MoveType moveType;

    IMoveType(ProgressWidget.MoveType moveType) {
        this.moveType = moveType;
    }
}
