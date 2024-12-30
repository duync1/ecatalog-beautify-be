package Beauty_ECatalog.Beauty_ECatalog.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.Role;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.RoleService;

import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;
import jakarta.validation.Valid;

@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createNewRole(@Valid @RequestBody Role role) throws IdInvalidException {
        boolean isExist = this.roleService.existsByName(role.getName());
        if (isExist) {
            throw new IdInvalidException("Role voi name = " + role.getName() + " da ton tai");
        } else {
            return ResponseEntity.ok().body(this.roleService.handleCreateRole(role));
        }
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) throws IdInvalidException {
        Role currentRole = this.roleService.handleUpdateRole(role);
        // boolean isExist = this.roleService.existsByName(role.getName());
        // if (isExist) {
        // throw new IdInvalidException("Role voi name = " + role.getName() + " da ton
        // tai");
        // }
        if (currentRole == null) {
            throw new IdInvalidException("Role voi id = " + role.getId() + " khong ton tai");
        } else {
            return ResponseEntity.ok().body(currentRole);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<ResultPaginationDTO> getAllRoles(@Filter Specification<Role> spec, Pageable pageable) {
        ResultPaginationDTO resultPaginationDTO = this.roleService.fetchAllRoles(spec, pageable);
        return ResponseEntity.ok().body(resultPaginationDTO);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") long id) throws IdInvalidException {
        boolean isExist = this.roleService.isExistById(id);
        if (!isExist) {
            throw new IdInvalidException("Role voi id = " + id + " khong ton tai");
        } else {
            this.roleService.handleDeleteRole(id);
            return ResponseEntity.ok().body(null);
        }
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getById(@PathVariable("id") long id) throws IdInvalidException {
        Role role = this.roleService.fetchById(id);
        if (role == null) {
            throw new IdInvalidException("Role voi id = " + id + " khong ton tai");
        } else {
            return ResponseEntity.ok().body(role);
        }
    }

    @GetMapping("/roles/disable/{id}")
    public ResponseEntity<Role> disableRole(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(this.roleService.disableRole(id));
    }

    @GetMapping("/roles/active/{id}")
    public ResponseEntity<Role> activeRole(@PathVariable("id") long id){
        return ResponseEntity.ok().body(this.roleService.activeRole(id));
    }
}
