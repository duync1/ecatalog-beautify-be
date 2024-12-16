package Beauty_ECatalog.Beauty_ECatalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store_reviews")
public class StoreReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private double productQuality;
    private double serviceQuality;
    private double deliveryQuality;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String comment;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String response;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
