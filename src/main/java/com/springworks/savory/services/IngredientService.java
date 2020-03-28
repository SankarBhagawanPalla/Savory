package com.springworks.savory.services;

import com.springworks.savory.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long ingredienID);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
