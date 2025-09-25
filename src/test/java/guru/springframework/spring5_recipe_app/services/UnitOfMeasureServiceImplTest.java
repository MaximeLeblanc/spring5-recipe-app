package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.UnitOfMeasureCommand;
import guru.springframework.spring5_recipe_app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5_recipe_app.domain.UnitOfMeasure;
import guru.springframework.spring5_recipe_app.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUoms() {
        // Given
//        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");
//        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");
//        unitOfMeasures.add(uom2);

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        // When
        List<UnitOfMeasureCommand> commands = service.listAllUoms().collectList().block();

        // Then
        assertEquals(2, commands.size());
        verify(unitOfMeasureReactiveRepository, times(1)).findAll();
    }
}