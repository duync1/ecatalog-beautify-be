package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.repository.ImportTicketDetailRepository;

@Service
public class ImportTicketDetailService {
    private final ImportTicketDetailRepository importTicketDetailRepository;
    public ImportTicketDetailService(ImportTicketDetailRepository importTicketDetailRepository){
        this.importTicketDetailRepository = importTicketDetailRepository;
    }

    public ImportTicketDetail createImportTicketDetail(ImportTicketDetail importTicketDetail){
        return this.importTicketDetailRepository.save(importTicketDetail);
    }

    public void deleteImportTicketDetail(List<ImportTicketDetail> lists){
        for(ImportTicketDetail ticket : lists){
            this.importTicketDetailRepository.delete(ticket);
        }
    }

    public List<ImportTicketDetail> getDetailByImportTicket(ImportTicket importTicket){
        return this.importTicketDetailRepository.findByImportTicket(importTicket);
    }
}
