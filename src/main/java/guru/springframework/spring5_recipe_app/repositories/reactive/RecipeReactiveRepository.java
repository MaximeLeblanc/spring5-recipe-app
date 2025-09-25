package guru.springframework.spring5_recipe_app.repositories.reactive;

import guru.springframework.spring5_recipe_app.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
