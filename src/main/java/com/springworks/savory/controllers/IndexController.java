package com.springworks.savory.controllers;

import com.springworks.savory.domain.Category;
import com.springworks.savory.domain.UnitOfMeasure;
import com.springworks.savory.repositories.CategoryRepository;
import com.springworks.savory.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }
    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){

        Optional<Category> category = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("Cat id is:"+ category.get().getId());
        System.out.println("UOM id is:"+ uom.get().getId());
        return "index";
    }
}
