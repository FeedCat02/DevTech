package com.zook.devtech.api.machines;

import com.zook.devtech.common.machines.MachineRegistry;
import com.zook.devtech.common.machines.MachineRenderer;
import crafttweaker.annotations.ZenRegister;
import gregtech.client.renderer.ICubeRenderer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenClass("mods.gregtech.MachineRenderer")
@ZenRegister
public interface IMachineRenderer {

    @ZenMethod
    static IMachineRenderer create(String basePath, String... faces) {
        return MachineRenderer.createOrientedRenderer(basePath, faces);
    }

    @Nullable
    @ZenMethod
    static IMachineRenderer get(String name) {
        return MachineRegistry.RENDERER_MAP.get(name);
    }

    ICubeRenderer getActualRenderer();
}
