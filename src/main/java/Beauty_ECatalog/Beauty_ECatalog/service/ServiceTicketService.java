package Beauty_ECatalog.Beauty_ECatalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.Servicee;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;

import Beauty_ECatalog.Beauty_ECatalog.repository.ServiceTicketRepository;

@Service
public class ServiceTicketService {
    private final ServiceTicketRepository serviceTicketRepository;
    private final ServiceService serviceService;
    private final ServiceTicketDetailService serviceTicketDetailService;
    private final UserService userService;
    public ServiceTicketService(ServiceTicketRepository serviceTicketRepository, ServiceService serviceService, ServiceTicketDetailService serviceTicketDetailService, UserService userService){
        this.serviceTicketRepository = serviceTicketRepository;
        this.serviceService = serviceService;
        this.serviceTicketDetailService = serviceTicketDetailService;
        this.userService = userService;
    }

    public ResultPaginationDTO fetchAllServiceTickets(Specification<ServiceTicket> spec, Pageable pageable) {
        Page<ServiceTicket> page = this.serviceTicketRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(page.getContent());
        return resultPaginationDTO;
    }

    public ServiceTicket createServiceTicketClient(ServiceTicket serviceTicket, String cusName, String cusPhone,long cusId, String serviceName){
        User customer = this.userService.fetchUserById(cusId);
        if(!customer.getName().equals(cusName)){
            customer.setName(cusName);
        }
        if(customer.getPhoneNumber() == null || !customer.getPhoneNumber().equals(cusPhone)){
            customer.setPhoneNumber(cusPhone);
        }
        customer = this.userService.handleUpdateUser(customer);
        serviceTicket.setUser(customer);
        ServiceTicket saveServiceTicket = this.serviceTicketRepository.save(serviceTicket);
        Servicee servicee = this.serviceService.findByName(serviceName);
        ServiceTicketDetail serviceTicketDetail = new ServiceTicketDetail();
        serviceTicketDetail.setService(servicee);
        serviceTicketDetail.setServiceTicket(saveServiceTicket);
        serviceTicketDetail = this.serviceTicketDetailService.createServiceTicketDetail(serviceTicketDetail);
        return saveServiceTicket;
    }
}
