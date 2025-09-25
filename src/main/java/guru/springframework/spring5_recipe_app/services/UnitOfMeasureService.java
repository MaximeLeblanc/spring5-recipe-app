package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();
}
