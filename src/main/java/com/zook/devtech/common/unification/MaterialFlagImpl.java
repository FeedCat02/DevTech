package com.zook.devtech.common.unification;

import com.zook.devtech.api.unification.IMaterialFlag;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

public class MaterialFlagImpl implements IMaterialFlag {

    private MaterialFlag.Builder builder;

    public MaterialFlagImpl(String name) {
        this.builder = new MaterialFlag.Builder(name);
    }

    @Override
    public IMaterialFlag requireFlag(String flag) {
        builder.requireFlags(MaterialFlag.getByName(flag));
        return this;
    }

    @Override
    public IMaterialFlag requireDust() {
        builder.requireProps(PropertyKey.DUST);
        return this;
    }

    @Override
    public IMaterialFlag requireIngot() {
        builder.requireProps(PropertyKey.INGOT);
        return this;
    }

    @Override
    public IMaterialFlag requireGem() {
        builder.requireProps(PropertyKey.GEM);
        return this;
    }

    @Override
    public IMaterialFlag requireFluid() {
        builder.requireProps(PropertyKey.FLUID);
        return this;
    }

    @Override
    public IMaterialFlag requirePlasma() {
        builder.requireProps(PropertyKey.PLASMA);
        return this;
    }

    @Override
    public IMaterialFlag requireTool() {
        builder.requireProps(PropertyKey.TOOL);
        return this;
    }

    @Override
    public void build() {
        builder.build();
    }
}
