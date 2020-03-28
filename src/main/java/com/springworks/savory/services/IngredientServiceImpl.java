package com.springworks.savory.services;

import com.springworks.savory.commands.IngredientCommand;
import com.springworks.savory.converters.IngredientCommandToIngredient;
import com.springworks.savory.converters.IngredientToIngredientCommand;
import com.springworks.savory.domain.Ingredient;
import com.springworks.savory.domain.Recipe;
import com.springworks.savory.repositories.RecipeRepository;
import com.springworks.savory.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {


    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand){

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            return new IngredientCommand();
        }else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(i -> i.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                          .orElseThrow(() -> new RuntimeException("UOM NOt FOUND")));

            }else{
                recipe.addIngredients(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
            .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                    .findFirst().get());

        }
    }
}
