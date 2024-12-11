package Beauty_ECatalog.Beauty_ECatalog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.Servicee;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.ServiceRepository;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final String uploadDir = "src/main/resources/static/uploads/";
    public ServiceService(ServiceRepository serviceRepository){
        this.serviceRepository = serviceRepository;
    }

    public Servicee getService(long id){
        Optional<Servicee> service = this.serviceRepository.findById(id);
        if(service.isPresent()){
            return service.get();
        }
        return null;
    }

    public Servicee createService(Servicee servicee, MultipartFile image) throws IOException{
        String imagePath = saveImage(image);
        servicee.setServiceImage(imagePath);
        return this.serviceRepository.save(servicee);
    }

    public boolean checkExistService(String name){
        boolean check = this.serviceRepository.existsByName(name);
        if(check) return true;
        else return false;
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null; // Hoặc trả về giá trị mặc định nếu không có ảnh mới
        }
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.write(filePath, imageFile.getBytes());
    
        return "/uploads/" + fileName;
    }

    public Servicee updateService(Servicee servicee, MultipartFile multipartFile) throws IOException{
        Servicee currentService = this.getService(servicee.getId());
        if(currentService != null){
            currentService.setName(servicee.getName());
            currentService.setPrice(servicee.getPrice());
            currentService.setShortDescription(servicee.getShortDescription());
            currentService.setDetailDescription(servicee.getDetailDescription());
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String imagePath = saveImage(multipartFile);
                currentService.setServiceImage(imagePath);
            }
            return this.serviceRepository.save(currentService);
        }
        return null;
    }

    public void deleteService(long id){
        Servicee servicee = this.getService(id);
        servicee.setDeleted(true);
        this.serviceRepository.save(servicee);
    }

    public void backService(long id){
        Servicee servicee = this.getService(id);
        servicee.setDeleted(false);
        this.serviceRepository.save(servicee);
    }
    
    public ResultPaginationDTO fetchAllServices(Specification<Servicee> spec, Pageable pageable) {
        Page<Servicee> page = this.serviceRepository.findAll(spec, pageable);
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

    public Servicee findByName(String name){
        return this.serviceRepository.findByName(name);
    }
}
