package com.springworks.savory.controllers;


import com.springworks.savory.services.IngredientService;
import com.springworks.savory.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IngredientController {


    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String getIngredientsList(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";

    }

    @GetMapping
    @RequestMapping("recipe/{rid}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String rid, @PathVariable String id, Model model){

        model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(rid), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }
}
