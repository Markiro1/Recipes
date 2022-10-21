package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.RecipeDtoCreate;
import recipes.dto.RecipeDtoRead;
import recipes.entities.Recipe;
import recipes.entities.User;
import recipes.exceptions.RecipeNotFoundException;
import recipes.mapper.RecipeCreateMapper;
import recipes.mapper.RecipeReadMapper;
import recipes.repositories.RecipeRepository;
import recipes.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    private Recipe findEntityById(long id) {
        return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }
    private UserDetails getLoggedUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public RecipeDtoRead save(Recipe recipe) {
        System.out.println("user from db - save : " + recipe.getUser());
        System.out.println("user from Logged - save : " + getLoggedUser().getUsername());
        User user = userRepository.findByEmailIgnoreCase(getLoggedUser().getUsername()).orElseThrow();
        recipe.setUser(user);
        return RecipeReadMapper.ToDto(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDtoCreate findById(long id) {
        return RecipeCreateMapper.ToDto(findEntityById(id));
    }

    @Override
    public void deleteById(long id) {
        Recipe recipe = findEntityById(id);
        System.out.println("user from db - delete : " + recipe.getUser());
        System.out.println("user from Logged - delete : " + getLoggedUser().getUsername());
            if (recipe.getUser().getEmail().equals(getLoggedUser().getUsername())) {
                recipeRepository.delete(recipe);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
    }

    @Override
    public void updateById(long id, Recipe recipe) {
        Recipe update = findEntityById(id);

        if (update.getUser().getEmail().equals(getLoggedUser().getUsername())) {
            update.setName(recipe.getName());
            update.setCategory(recipe.getCategory());
            update.setDescription(recipe.getDescription());
            update.setIngredients(recipe.getIngredients());
            update.setDirections(recipe.getDirections());
            recipeRepository.save(update);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public List<RecipeDtoCreate> searchByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category).stream()
                .map(RecipeCreateMapper::ToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDtoCreate> searchByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name).stream()
                .map(RecipeCreateMapper::ToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDtoCreate> searchByNameAndCategory(String name, String category) {
        return recipeRepository.findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(name, category).stream()
                .map(RecipeCreateMapper::ToDto)
                .collect(Collectors.toList());
    }
}
