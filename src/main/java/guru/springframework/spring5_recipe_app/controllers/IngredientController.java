package guru.springframework.spring5_recipe_app.controllers;

import guru.springframework.spring5_recipe_app.commands.IngredientCommand;
import guru.springframework.spring5_recipe_app.commands.RecipeCommand;
import guru.springframework.spring5_recipe_app.commands.UnitOfMeasureCommand;
import guru.springframework.spring5_recipe_app.services.IngredientService;
import guru.springframework.spring5_recipe_app.services.RecipeService;
import guru.springframework.spring5_recipe_app.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model) {
        // Make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        // TODO: raise exception if null

        // Need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        // Init uom
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id,
                                         Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:{}", savedCommand.getRecipeId());
        log.debug("saved ingredient id:{}", savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id) {
        log.debug("Deleting ingredient id: {}", id);

        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
