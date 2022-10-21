package recipes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDtoCreate {
    private String name;
    private String category;
    private String description;
    private LocalDateTime date;
    private List<String> ingredients;
    private List<String> directions;
}
