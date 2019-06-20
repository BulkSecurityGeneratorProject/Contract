package test.contract.repository;

import test.contract.domain.InsuranceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InsuranceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {

}
