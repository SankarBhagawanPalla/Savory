package com.springworks.savory.controllers;

import com.springworks.savory.domain.Category;
import com.springworks.savory.domain.UnitOfMeasure;
import com.springworks.savory.repositories.CategoryRepository;
import com.springworks.savory.repositories.UnitOfMeasureRepository;
import com.springworks.savory.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
        log.debug("Getting Index Page");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
