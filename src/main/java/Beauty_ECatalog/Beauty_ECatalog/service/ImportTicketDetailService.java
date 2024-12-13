package Beauty_ECatalog.Beauty_ECatalog.service;

import org.springframework.stereotype.Service;

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
}
