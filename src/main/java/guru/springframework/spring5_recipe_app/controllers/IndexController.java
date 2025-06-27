package guru.springframework.spring5_recipe_app.controllers;

import guru.springframework.spring5_recipe_app.domain.Category;
import guru.springframework.spring5_recipe_app.domain.UnitOfMeasure;
import guru.springframework.spring5_recipe_app.repositories.CategoryRepository;
import guru.springframework.spring5_recipe_app.repositories.UnitOfMeasureRepository;
import guru.springframework.spring5_recipe_app.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
