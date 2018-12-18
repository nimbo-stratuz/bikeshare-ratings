package si.nimbostratuz.bikeshare.models.entities;
import lombok.Data;

import javax.persistence.*;


@Entity(name = "rating")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="bicycle_id", nullable=false)
    private Integer bicycleId;

    @Column(name="user_id", nullable=false)
    private Integer userId;

    @Column(nullable=false)
    private Integer rating;

    // Optional comment
    private String comment;
}
