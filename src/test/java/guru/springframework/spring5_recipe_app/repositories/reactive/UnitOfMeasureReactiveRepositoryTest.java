package guru.springframework.spring5_recipe_app.repositories.reactive;

import guru.springframework.spring5_recipe_app.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureReactiveRepositoryTest {

    public static final String EACH = "Each";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    
    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(EACH);

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        Long count = unitOfMeasureReactiveRepository.count().block();

        assertEquals(1L, count);
    }

    @Test
    public void testFindByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(EACH);

        unitOfMeasureReactiveRepository.save(unitOfMeasure).then().block();

        UnitOfMeasure fetchedUnitOfMeasure = unitOfMeasureReactiveRepository.findByDescription("Foo").block();

        assertEquals(EACH, fetchedUnitOfMeasure.getDescription());
    }
}