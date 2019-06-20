package test.contract.repository;

import test.contract.domain.FuelType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FuelType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

}
