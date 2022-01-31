package com.zook.devtech.api.machines;

import com.zook.devtech.common.machines.CTMachineBuilder;
import com.zook.devtech.common.machines.MachineRenderer;
import crafttweaker.annotations.ZenRegister;
import gregtech.client.renderer.ICubeRenderer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenClass("mods.gregtech.machine.Renderer")
@ZenRegister
public interface IMachineRenderer {

    @ZenMethod
    static IMachineRenderer create(String basePath, String... faces) {
        return MachineRenderer.createOrientedRenderer(basePath, faces);
    }

    @Nullable
    @ZenMethod
    static IMachineRenderer get(String name) {
        return CTMachineBuilder.RENDERER_MAP.get(name);
    }

    ICubeRenderer getActualRenderer();
}
