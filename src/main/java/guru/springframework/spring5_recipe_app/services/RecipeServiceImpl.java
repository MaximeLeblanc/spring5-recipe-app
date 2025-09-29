package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.RecipeCommand;
import guru.springframework.spring5_recipe_app.converters.RecipeCommandToRecipe;
import guru.springframework.spring5_recipe_app.converters.RecipeToRecipeCommand;
import guru.springframework.spring5_recipe_app.domain.Recipe;
import guru.springframework.spring5_recipe_app.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("getRecipes() - start");

        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
//        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
//        Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe).block();
//        log.debug("Saved recipeId: " + savedRecipe.getId());
//
//        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipe.getId()));

                    return recipeCommand;
                });
//        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id).block());
//
//        if (recipeCommand.getIngredients() != null && !recipeCommand.getIngredients().isEmpty()) {
//            recipeCommand.getIngredients().forEach(ingredient -> {
//                ingredient.setRecipeId(recipeCommand.getId());
//            });
//        }
//
//        return recipeCommand;
    }

    @Override
    public void deleteById(String id) {
        recipeReactiveRepository.deleteById(id).block();
    }
}
