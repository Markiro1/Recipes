package recipes.mapper;

import recipes.dto.RecipeDtoRead;
import recipes.entities.Recipe;

public class RecipeReadMapper {

    public static RecipeDtoRead ToDto(Recipe recipe) {
        RecipeDtoRead recipeDtoRead = new RecipeDtoRead();
        recipeDtoRead.setId(recipe.getId());

        return recipeDtoRead;
    }
}
