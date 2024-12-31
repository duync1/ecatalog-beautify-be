package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.Servicee;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResServiceTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;

import Beauty_ECatalog.Beauty_ECatalog.repository.ServiceTicketRepository;

@Service
public class ServiceTicketService {
    private final ServiceTicketRepository serviceTicketRepository;
    private final ServiceService serviceService;
    private final ServiceTicketDetailService serviceTicketDetailService;
    private final UserService userService;
    private final RoleService roleService;
    public ServiceTicketService(ServiceTicketRepository serviceTicketRepository, ServiceService serviceService, ServiceTicketDetailService serviceTicketDetailService, UserService userService, RoleService roleService){
        this.serviceTicketRepository = serviceTicketRepository;
        this.serviceService = serviceService;
        this.serviceTicketDetailService = serviceTicketDetailService;
        this.userService = userService;
        this.roleService = roleService;
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

    public ServiceTicket getServiceTicket(long id){
        Optional<ServiceTicket> serviceTicket = this.serviceTicketRepository.findById(id);
        if(serviceTicket.isPresent()){
            return serviceTicket.get();
        }
        return null;
    }

    public ServiceTicket confirmServiceTicket(ServiceTicket serviceTicket){
        ServiceTicket currentTicket = this.getServiceTicket(serviceTicket.getId());
        currentTicket.setStatus(true);
        return this.serviceTicketRepository.save(currentTicket);
    }

    public ServiceTicket confirmCheckout(ServiceTicket serviceTicket){
        ServiceTicket currentTicket = this.getServiceTicket(serviceTicket.getId());
        currentTicket.setCheckout(true);
        return this.serviceTicketRepository.save(currentTicket);
    }

    public ResServiceTicketDetail getDetailOfSericeTicket(ServiceTicket serviceTicket){
        List<ServiceTicketDetail> listServiceTicketDetail = this.serviceTicketDetailService.getDetailByServiceTicket(serviceTicket);
        List<Servicee> listServices = new ArrayList<>();
        for(ServiceTicketDetail serviceTicketDetail : listServiceTicketDetail){
            listServices.add(serviceTicketDetail.getService());
        }
        ResServiceTicketDetail resServiceTicketDetail = new ResServiceTicketDetail(serviceTicket, listServices);
        return resServiceTicketDetail;
    }

    public ServiceTicket createServiceTicketAdmin(ServiceTicket serviceTicket, String cusName, String cusPhone, List<String> serviceName){
        User user = this.userService.getUserByName(cusName);
        User updateUser = new User();
        if(user == null){
            updateUser.setName(cusName);
            updateUser.setPhoneNumber(cusPhone);
            updateUser.setRole(this.roleService.getRoleByName("CUSTOMER"));
            updateUser = this.userService.handleCreateUserBase(updateUser);
            serviceTicket.setUser(updateUser);
        }
        if(user != null){
            if(user.getPhoneNumber() == null || !user.getPhoneNumber().equals(cusPhone)){
                user.setPhoneNumber(cusPhone);
                user = this.userService.handleUpdateUser(user);
            }
            serviceTicket.setUser(user);
        }
        serviceTicket.setCheckout(false);
        serviceTicket.setStatus(false);
        ServiceTicket saveServiceTicket = this.serviceTicketRepository.save(serviceTicket);
        for(String serName : serviceName){
            Servicee servicee = this.serviceService.findByName(serName);
            ServiceTicketDetail serviceTicketDetail = new ServiceTicketDetail();
            serviceTicketDetail.setService(servicee);
            serviceTicketDetail.setServiceTicket(saveServiceTicket);
            serviceTicketDetail = this.serviceTicketDetailService.createServiceTicketDetail(serviceTicketDetail);
        }
        return saveServiceTicket;
    }
}
