package recipes.mapper;

import recipes.dto.RecipeDtoCreate;
import recipes.entities.Recipe;

public class RecipeCreateMapper {

    public static RecipeDtoCreate ToDto(Recipe recipe) {
        RecipeDtoCreate recipeDtoCreate = new RecipeDtoCreate();
        recipeDtoCreate.setName(recipe.getName());
        recipeDtoCreate.setCategory(recipe.getCategory());
        recipeDtoCreate.setDescription(recipe.getDescription());
        recipeDtoCreate.setDate(recipe.getDate());
        recipeDtoCreate.setDirections(recipe.getDirections());
        recipeDtoCreate.setIngredients(recipe.getIngredients());

        return recipeDtoCreate;
    }
}
