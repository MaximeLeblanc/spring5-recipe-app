package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.IngredientCommand;
import guru.springframework.spring5_recipe_app.converters.IngredientCommandToIngredient;
import guru.springframework.spring5_recipe_app.converters.IngredientToIngredientCommand;
import guru.springframework.spring5_recipe_app.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.spring5_recipe_app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5_recipe_app.domain.Ingredient;
import guru.springframework.spring5_recipe_app.domain.Recipe;
import guru.springframework.spring5_recipe_app.repositories.RecipeRepository;
import guru.springframework.spring5_recipe_app.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // When
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        // Then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveRecipeCommand() throws Exception {
        // Given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        // When
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // Then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void deleteById() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // When
        ingredientService.deleteById(1L, 3L);

        // Then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}