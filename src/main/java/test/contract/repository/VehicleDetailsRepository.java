package test.contract.repository;

import test.contract.domain.VehicleDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VehicleDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, Long> {

}
