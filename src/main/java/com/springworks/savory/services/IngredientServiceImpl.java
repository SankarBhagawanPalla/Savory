package com.springworks.savory.services;

import com.springworks.savory.commands.IngredientCommand;
import com.springworks.savory.converters.IngredientToIngredientCommand;
import com.springworks.savory.domain.Recipe;
import com.springworks.savory.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {


    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;


    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.debug("Recipe not found");
        }
        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream()
                                                              .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                                              .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                                                              .findFirst();

        if(!ingredientCommand.isPresent()){
            log.debug("Ingredient not present");
        }

        return ingredientCommand.get();

    }
}
