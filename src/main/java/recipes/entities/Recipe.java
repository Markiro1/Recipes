package recipes.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name ="RECIPES")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    private String category;

    @UpdateTimestamp
    private LocalDateTime date = LocalDateTime.now();

    @NotBlank
    private String description;

    @Size(min = 1)
    @NotEmpty
    @ElementCollection
    private List<String> ingredients;

    @Size(min = 1)
    @NotEmpty
    @ElementCollection
    private List<String> directions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe(String name, String category,String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
