package guru.springframework.spring5_recipe_app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {

    private String id;

    private String notes;

    private Recipe recipe;
}
