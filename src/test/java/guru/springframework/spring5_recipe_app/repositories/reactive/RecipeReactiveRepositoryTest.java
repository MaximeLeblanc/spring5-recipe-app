package guru.springframework.spring5_recipe_app.repositories.reactive;

import guru.springframework.spring5_recipe_app.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeReactiveRepositoryTest {
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Yummy!");

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();

        assertEquals(1L, count);
    }
}