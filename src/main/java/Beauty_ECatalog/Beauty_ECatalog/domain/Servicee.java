package Beauty_ECatalog.Beauty_ECatalog.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="services")
public class Servicee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String shortDescription;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDescription;
    private String serviceImage;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ServiceTicketDetail> serviceTicketDetails;

}
