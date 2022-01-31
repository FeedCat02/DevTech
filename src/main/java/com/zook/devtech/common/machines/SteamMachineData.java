package com.zook.devtech.common.machines;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ProgressWidget;

public class SteamMachineData {

    public boolean bricked;
    public boolean highPressure;
    public TextureArea progressBar;
    public ProgressWidget.MoveType moveType;
    public final TByteObjectMap<TextureArea> slotOverlays = new TByteObjectHashMap<>();
    public int tankSize = 8000;
    public double conversionRate = 1.0;
    public int steamOutput = -1;
    public int cooldownInterval = -1;
    public int cooldownRate = -1;

    public void setSlotOverlay(boolean isOutput, boolean isFluid, TextureArea slotOverlay) {
        setSlotOverlay(isOutput, isFluid, false, slotOverlay);
        setSlotOverlay(isOutput, isFluid, true, slotOverlay);
    }

    public void setSlotOverlay(boolean isOutput, boolean isFluid, boolean isLast, TextureArea slotOverlay) {
        this.slotOverlays.put((byte) ((isOutput ? 2 : 0) + (isFluid ? 1 : 0) + (isLast ? 4 : 0)), slotOverlay);
    }
}
