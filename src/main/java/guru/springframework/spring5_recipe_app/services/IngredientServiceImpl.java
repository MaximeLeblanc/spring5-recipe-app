package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.IngredientCommand;
import guru.springframework.spring5_recipe_app.converters.IngredientCommandToIngredient;
import guru.springframework.spring5_recipe_app.converters.IngredientToIngredientCommand;
import guru.springframework.spring5_recipe_app.domain.Ingredient;
import guru.springframework.spring5_recipe_app.domain.Recipe;
import guru.springframework.spring5_recipe_app.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.spring5_recipe_app.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, RecipeReactiveRepository recipeReactiveRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeReactiveRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });
//        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
//
//        if (recipeOptional.isEmpty()) {
//            // TODO: implement error handling
//            log.error("recipe id not found. Id: {}", recipeId);
//        }
//
//        Recipe recipe = recipeOptional.get();
//
//        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
//                .filter(ingredient -> ingredient.getId().equals(ingredientId))
//                .map(ingredientToIngredientCommand::convert).findFirst();
//
//        if (ingredientCommandOptional.isEmpty()) {
//            // TODO: implement error handling
//            log.error("Ingredient id not found: {}", ingredientId);
//        }
//
//        return Mono.just(ingredientCommandOptional.get());
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId()).block();

        if (recipe != null) {
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureReactiveRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .block());
            } else {
                // Add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if (savedIngredientOptional.isEmpty()) {
                // Not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            // TODO: check for fail
            return Mono.just(ingredientToIngredientCommand.convert(savedIngredientOptional.get()));
        }
        return Mono.just(new IngredientCommand());

//        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
//
//        if (recipeOptional.isEmpty()) {
//            log.error("Recipe not found for id: {}", command.getRecipeId());
//            return Mono.just(new IngredientCommand());
//        } else {
//            Recipe recipe = recipeOptional.get();
//
//            Optional<Ingredient> ingredientOptional = recipe
//                    .getIngredients()
//                    .stream()
//                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
//                    .findFirst();
//
//            if (ingredientOptional.isPresent()) {
//                Ingredient ingredientFound = ingredientOptional.get();
//                ingredientFound.setDescription(command.getDescription());
//                ingredientFound.setAmount(command.getAmount());
//                ingredientFound.setUom(unitOfMeasureRepository
//                        .findById(command.getUnitOfMeasure().getId())
//                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
//            } else {
//                // Add new Ingredient
//                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
//                recipe.addIngredient(ingredient);
//            }
//
//            Recipe savedRecipe = recipeRepository.save(recipe);
//
//            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
//                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
//                    .findFirst();
//
//            if (savedIngredientOptional.isEmpty()) {
//                // Not totally safe... But best guess
//                savedIngredientOptional = savedRecipe.getIngredients().stream()
//                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
//                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
//                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUnitOfMeasure().getId()))
//                        .findFirst();
//            }
//
//            // TODO: check for fail
//            return Mono.just(ingredientToIngredientCommand.convert(savedIngredientOptional.get()));
//        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String id) {
        log.debug("Deleting ingredient: {}: {}", recipeId, id);

        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();

        if (recipe != null) {
            log.debug("Recipe found!");

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                log.debug("Ingredient found!");
                recipe.getIngredients().remove(ingredient);
                recipeReactiveRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe {} not found", recipeId);
        }
        return Mono.empty();

//        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
//
//        if (recipeOptional.isPresent()) {
//            Recipe recipe = recipeOptional.get();
//            log.debug("Recipe found!");
//
//            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
//                    .stream()
//                    .filter(ingredient -> ingredient.getId().equals(id))
//                    .findFirst();
//
//            if (ingredientOptional.isPresent()) {
//                Ingredient ingredient = ingredientOptional.get();
//                log.debug("Ingredient found!");
//                recipe.getIngredients().remove(ingredient);
//                recipeRepository.save(recipe);
//            }
//        } else {
//            log.debug("Recipe {} not found", recipeId);
//        }
//        return Mono.empty();
    }
}
