package com.springworks.savory.services;

import com.springworks.savory.commands.RecipeCommand;
import com.springworks.savory.converters.RecipeCommandToRecipe;
import com.springworks.savory.converters.RecipeToRecipeCommand;
import com.springworks.savory.domain.Recipe;
import com.springworks.savory.exceptions.NotFoundException;
import com.springworks.savory.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand ) {

        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the Service");
        Set<Recipe> recipeset = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeset::add);
        return recipeset;
    }

    @Override
    public Recipe findByID(Long id){

        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(!recipe.isPresent()){
            //throw new RuntimeException("Recipe Not Found!");
            throw new NotFoundException("Recipe Not Found!");
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l){
        return recipeToRecipeCommand.convert(findByID(l));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id){
        recipeRepository.deleteById(id);
    }
}
