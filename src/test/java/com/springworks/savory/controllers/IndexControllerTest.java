package com.springworks.savory.controllers;

import com.springworks.savory.domain.Recipe;
import com.springworks.savory.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);


        when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String value = indexController.getIndexPage(model);

        //then
        assertEquals("index",value);
        verify(model, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        verify(recipeService, times(1)).getRecipes();
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(3, setInController.size());

    }
}