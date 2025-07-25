package guru.springframework.spring5_recipe_app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;

        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
        String stringValue = "This is a description";

        category.setDescription(stringValue);

        assertEquals(stringValue, category.getDescription());
    }

    @Test
    void getRecipes() {
    }
}