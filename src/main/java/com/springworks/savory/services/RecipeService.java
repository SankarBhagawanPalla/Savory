package com.springworks.savory.services;

import com.springworks.savory.commands.RecipeCommand;
import com.springworks.savory.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findByID(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
