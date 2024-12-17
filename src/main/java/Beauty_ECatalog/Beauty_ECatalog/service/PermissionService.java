package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Permission;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.PermissionRepository;

@Service
public class PermissionService {
     private final PermissionRepository permissionRepository;

     public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public boolean isPermissionExist(Permission permission) {
        return permissionRepository.existsByApiPathAndMethodAndModule(permission.getApiPath(), permission.getMethod(),
                permission.getModule());
    }

    public boolean existsById(long id) {
        return this.permissionRepository.existsById(id);
    }

    public Permission handleCreatePermission(Permission permission) {
        return this.permissionRepository.save(permission);
    }

    public Permission handleUpdatePermission(Permission permission) {
        Optional<Permission> currentPermission = this.permissionRepository.findById(permission.getId());
        if (currentPermission.isPresent()) {
            Permission savePermission = currentPermission.get();
            savePermission.setName(permission.getName());
            savePermission.setApiPath(permission.getApiPath());
            savePermission.setMethod(permission.getMethod());
            savePermission.setModule(permission.getModule());
            return this.permissionRepository.save(savePermission);
        }
        return null;
    }

    public ResultPaginationDTO fetchAllPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> page = this.permissionRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        List<Permission> list = new ArrayList<>();
        for (Permission permission : page.getContent()) {
            list.add(permission);
        }
        resultPaginationDTO.setResult(list);
        return resultPaginationDTO;
    }

    public void delete(long id) {
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        Permission currentPermission = permissionOptional.get();
        currentPermission.getRoles().forEach(role -> role.getPermissions().remove(currentPermission));
        this.permissionRepository.delete(currentPermission);
    }
}
