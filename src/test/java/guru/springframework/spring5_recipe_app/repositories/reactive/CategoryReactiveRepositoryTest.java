package guru.springframework.spring5_recipe_app.repositories.reactive;

import guru.springframework.spring5_recipe_app.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() {
        Category category = new Category();
        category.setDescription("Foo");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals(1L, count);
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription("Foo");

        categoryReactiveRepository.save(category).then().block();

        Category fetchedCategory = categoryReactiveRepository.findByDescription("Foo").block();

        assertNotNull(fetchedCategory);
    }
}