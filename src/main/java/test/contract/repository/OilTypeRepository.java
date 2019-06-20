package test.contract.repository;

import test.contract.domain.OilType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OilType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OilTypeRepository extends JpaRepository<OilType, Long> {

}
