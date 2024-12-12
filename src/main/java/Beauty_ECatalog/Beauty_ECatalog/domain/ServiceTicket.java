package Beauty_ECatalog.Beauty_ECatalog.domain;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="service_tickets")
public class ServiceTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Instant date;
    private long total;
    private boolean status;
    private boolean checkout;
    @OneToMany(mappedBy = "serviceTicket", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ServiceTicketDetail> serviceTicketDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
