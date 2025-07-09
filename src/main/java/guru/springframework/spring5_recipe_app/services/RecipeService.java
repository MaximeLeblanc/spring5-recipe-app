package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.RecipeCommand;
import guru.springframework.spring5_recipe_app.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
