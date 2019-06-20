package test.contract.repository;

import test.contract.domain.DriveArea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DriveArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriveAreaRepository extends JpaRepository<DriveArea, Long> {

}
