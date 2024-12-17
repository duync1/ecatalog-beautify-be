package Beauty_ECatalog.Beauty_ECatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    boolean existsByApiPathAndMethodAndModule(String apiPath, String method, String module);

    boolean existsById(long id);

    List<Permission> findByIdIn(List<Long> list);
}
