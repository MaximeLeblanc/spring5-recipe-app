package guru.springframework.spring5_recipe_app.services;

import guru.springframework.spring5_recipe_app.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
