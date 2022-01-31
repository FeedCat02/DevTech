package com.zook.devtech.common.machines.recipe;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.util.ValidationResult;

import java.util.HashMap;
import java.util.Map;

public class CTRecipeBuilder extends RecipeBuilder<CTRecipeBuilder> {

    private final Map<String, Object> properties;

    public CTRecipeBuilder() {
        super();
        properties = new HashMap<>();
    }

    public CTRecipeBuilder(CTRecipeBuilder recipeBuilder) {
        super(recipeBuilder);
        properties = recipeBuilder.properties;
    }

    public CTRecipeBuilder copy() {
        return new CTRecipeBuilder(this);
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(this.finalizeAndValidate(), new Recipe(this.inputs, this.outputs, this.chancedOutputs, this.fluidInputs, this.fluidOutputs, this.duration, this.EUt, this.hidden));
    }

    @Override
    public boolean applyProperty(String key, Object value) {
        properties.put(key, value);
        return true;
    }
}
