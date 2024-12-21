package Beauty_ECatalog.Beauty_ECatalog.domain;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Beauty_ECatalog.Beauty_ECatalog.util.constant.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "sale_tickets")
public class SaleTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Instant date;
    private long total;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"role"})
    private User user;
    @OneToMany(mappedBy = "saleTicket", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SaleTicketDetail> sale_ticket_details;
    
    @ManyToOne 
    @JoinColumn(name = "discount_id", nullable = true)
    private Voucher discount;
}
