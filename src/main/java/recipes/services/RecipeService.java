package recipes.services;

import recipes.dto.RecipeDtoCreate;
import recipes.dto.RecipeDtoRead;
import recipes.entities.Recipe;

import java.util.List;

public interface RecipeService {

    RecipeDtoRead save(Recipe recipe); //Принять RecipeDtoCreate
    RecipeDtoCreate findById(long id);
    void deleteById(long id);
    void updateById(long id, Recipe recipe);
    List<RecipeDtoCreate> searchByCategory(String category);
    List<RecipeDtoCreate> searchByName(String name);

    List<RecipeDtoCreate> searchByNameAndCategory(String name, String category);
}
