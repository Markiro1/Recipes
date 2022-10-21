package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.RecipeDtoCreate;
import recipes.entities.Recipe;
import recipes.services.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequestMapping("/api/recipe")
@RestController
@Validated
public class RecipeController {

    private final RecipeService recipeService;


    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe) {
        long id = recipeService.save(recipe).getId();

        return Map.of("id", id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDtoCreate> getRecipe(@PathVariable long id) {
        RecipeDtoCreate recipeDto = recipeService.findById(id);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable long id) {
        recipeService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        recipeService.updateById(id, recipe);
    }

    @GetMapping("/search")
    public List<RecipeDtoCreate> searchByCategory(@RequestParam(required = false) String category,
                                            @RequestParam(required = false) String name) {
        if (category == null && name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (name == null) {
            return recipeService.searchByCategory(category);

        } else if (category == null) {
            return recipeService.searchByName(name);
        } else {
            return recipeService.searchByNameAndCategory(name, category);
        }
    }
}
