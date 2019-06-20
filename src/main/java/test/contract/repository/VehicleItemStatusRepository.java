package test.contract.repository;

import test.contract.domain.VehicleItemStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VehicleItemStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleItemStatusRepository extends JpaRepository<VehicleItemStatus, Long> {

}
