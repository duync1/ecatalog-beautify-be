package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.Servicee;


@Repository
public interface ServiceRepository extends JpaRepository<Servicee, Long>, JpaSpecificationExecutor<Servicee> {
    public boolean existsByName(String name);
    public Servicee findByName(String name);
}
