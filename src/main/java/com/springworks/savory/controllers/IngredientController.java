package com.springworks.savory.controllers;


import com.springworks.savory.commands.IngredientCommand;
import com.springworks.savory.services.IngredientService;
import com.springworks.savory.services.RecipeService;
import com.springworks.savory.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {


    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

    @GetMapping
    @RequestMapping("recipe/{rid}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String rid, @PathVariable String id, Model model){

        model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(rid), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.listofAllUoms());
        return "recipe/ingredient/ingredientform";

    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        log.debug(command.toString());
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/"+ savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() +"/show";
    }
}
