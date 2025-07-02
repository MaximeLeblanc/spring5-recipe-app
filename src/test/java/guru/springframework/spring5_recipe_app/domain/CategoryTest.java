package guru.springframework.spring5_recipe_app.domain;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryTest {

    Category category;

    @Before
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