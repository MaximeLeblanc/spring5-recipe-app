package guru.springframework.spring5_recipe_app.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Notes {

    @Id
    private String id;

    private String notes;

    private Recipe recipe;
}
