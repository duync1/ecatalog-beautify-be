package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.repository.ServiceTicketDetailRepository;

@Service
public class ServiceTicketDetailService {
    private final ServiceTicketDetailRepository serviceTicketDetailRepository;
    public ServiceTicketDetailService(ServiceTicketDetailRepository serviceTicketDetailRepository){
        this.serviceTicketDetailRepository = serviceTicketDetailRepository;
    }

    public ServiceTicketDetail createServiceTicketDetail(ServiceTicketDetail serviceTicketDetail){
        return this.serviceTicketDetailRepository.save(serviceTicketDetail);
    }

    public List<ServiceTicketDetail> getDetailByServiceTicket(ServiceTicket serviceTicket){
        return this.serviceTicketDetailRepository.findByServiceTicket(serviceTicket);
    }
}
