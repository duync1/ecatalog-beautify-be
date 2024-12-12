package Beauty_ECatalog.Beauty_ECatalog.domain.response;

import java.time.Instant;
import java.util.List;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.Servicee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResServiceTicketDetail {
    private ServiceTicket serviceTicket;
    private List<Servicee> listServices;
}
