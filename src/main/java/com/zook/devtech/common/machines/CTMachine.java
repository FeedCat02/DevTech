package com.zook.devtech.common.machines;

import com.zook.devtech.api.machines.IMachine;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.util.ResourceLocation;

public class CTMachine implements IMachine {

    public static CTMachine createFromName(String name) {
        if (name.split(":").length == 1) {
            name = "gregtech:" + name;
        }
        MetaTileEntity mte = GregTechAPI.MTE_REGISTRY.getObject(new ResourceLocation(name));
        if (mte == null) {
            return null;
        }
        return new CTMachine(mte);
    }

    public static CTMachine createForId(int id) {
        MetaTileEntity mte = GregTechAPI.MTE_REGISTRY.getObjectById(id);
        if (mte == null) {
            return null;
        }
        return new CTMachine(mte);
    }

    private final MetaTileEntity mte;
    private final int id;

    public CTMachine(MetaTileEntity mte) {
        this.mte = mte;
        this.id = GregTechAPI.MTE_REGISTRY.getIdByObjectName(mte.metaTileEntityId);
    }

    @Override
    public int getIntId() {
        return id;
    }

    @Override
    public String getName() {
        return mte.metaTileEntityId.toString();
    }
}
