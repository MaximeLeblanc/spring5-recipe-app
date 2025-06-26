package guru.springframework.spring5_recipe_app.repositories;

import guru.springframework.spring5_recipe_app.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
