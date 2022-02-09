package com.zook.devtech.common.unification;

import com.zook.devtech.api.unification.IMaterialFlagBuilder;
import com.zook.devtech.api.unification.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

public class MaterialFlagBuilderImpl implements IMaterialFlagBuilder {

    private final gregtech.api.unification.material.info.MaterialFlag.Builder builder;

    public MaterialFlagBuilderImpl(String name) {
        this.builder = new gregtech.api.unification.material.info.MaterialFlag.Builder(name);
    }

    @Override
    public IMaterialFlagBuilder requireFlag(MaterialFlag flag) {
        if (flag != null) {
            builder.requireFlags(flag.getInternal());
        }
        return this;
    }

    @Override
    public IMaterialFlagBuilder requireDust() {
        builder.requireProps(PropertyKey.DUST);
        return this;
    }

    @Override
    public IMaterialFlagBuilder requireIngot() {
        builder.requireProps(PropertyKey.INGOT);
        return this;
    }

    @Override
    public IMaterialFlagBuilder requireGem() {
        builder.requireProps(PropertyKey.GEM);
        return this;
    }

    @Override
    public IMaterialFlagBuilder requireFluid() {
        builder.requireProps(PropertyKey.FLUID);
        return this;
    }

    @Override
    public IMaterialFlagBuilder requirePlasma() {
        builder.requireProps(PropertyKey.PLASMA);
        return this;
    }

    @Override
    public IMaterialFlagBuilder requireTool() {
        builder.requireProps(PropertyKey.TOOL);
        return this;
    }

    @Override
    public MaterialFlag build() {
        return new MaterialFlag(builder.build());
    }
}
