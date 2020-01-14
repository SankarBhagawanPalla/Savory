package com.springworks.savory.services;

import com.springworks.savory.domain.Recipe;
import com.springworks.savory.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
            throw new RuntimeException("Recipe Not Found!");
        }
        return recipe.get();


    }
}
