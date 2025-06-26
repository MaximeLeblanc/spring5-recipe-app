package guru.springframework.spring5_recipe_app.controllers;

import guru.springframework.spring5_recipe_app.domain.Category;
import guru.springframework.spring5_recipe_app.domain.UnitOfMeasure;
import guru.springframework.spring5_recipe_app.repositories.CategoryRepository;
import guru.springframework.spring5_recipe_app.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        Optional<Category> category = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category Id is:" + category.get().getId());
        System.out.println("UOM Id is:" + unitOfMeasure.get().getId());

        return "index";
    }
}
