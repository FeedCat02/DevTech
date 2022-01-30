package com.zook.devtech.api.unification;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import stanhebben.zenscript.annotations.*;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

@FunctionalInterface
@ZenClass("mods.gregtech.material.IMaterialPredicate")
@ZenRegister
public interface IMaterialPredicate extends Predicate<Material> {

    @Nonnull
    @ZenMethod
    @ZenOperator(OperatorType.OR)
    default IMaterialPredicate or(@Nonnull IMaterialPredicate other) {
        return t -> test(t) || other.test(t);
    }

    @Nonnull
    @ZenMethod
    @ZenOperator(OperatorType.NEG)
    default IMaterialPredicate negate() {
        return t -> !test(t);
    }

    @Nonnull
    @ZenMethod
    @ZenOperator(OperatorType.AND)
    default IMaterialPredicate and(@Nonnull IMaterialPredicate other) {
        return t -> test(t) && other.test(t);
    }

    @ZenProperty
    IMaterialPredicate isFluid = mat -> mat.hasProperty(PropertyKey.FLUID);
    @ZenProperty
    IMaterialPredicate isDust = mat -> mat.hasProperty(PropertyKey.DUST);
    @ZenProperty
    IMaterialPredicate isGem = mat -> mat.hasProperty(PropertyKey.GEM);
    @ZenProperty
    IMaterialPredicate isIngot = mat -> mat.hasProperty(PropertyKey.INGOT);
}
