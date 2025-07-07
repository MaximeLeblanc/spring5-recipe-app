package guru.springframework.spring5_recipe_app.commands;

import guru.springframework.spring5_recipe_app.domain.Recipe;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {

    private Long id;
    private String notes;
}
