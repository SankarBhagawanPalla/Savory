package com.springworks.savory.services;

import com.springworks.savory.domain.Recipe;
import com.springworks.savory.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeset = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeset::add);
        return recipeset;
    }
}
