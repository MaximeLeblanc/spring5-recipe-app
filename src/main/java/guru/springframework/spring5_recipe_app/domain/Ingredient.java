package guru.springframework.spring5_recipe_app.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Ingredient {

    private String id;

    private String description;

    private BigDecimal amount;

    private UnitOfMeasure uom;

    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(Recipe recipe, UnitOfMeasure uom, BigDecimal amount, String description) {
        this.recipe = recipe;
        this.uom = uom;
        this.amount = amount;
        this.description = description;
    }
}
