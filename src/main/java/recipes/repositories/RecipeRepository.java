package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import recipes.entities.Recipe;

import java.util.List;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Transactional(readOnly = true)
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

    @Transactional(readOnly = true)
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    @Transactional(readOnly = true)
    List<Recipe> findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(String name, String category);
}
