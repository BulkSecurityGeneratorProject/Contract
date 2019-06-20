package test.contract.repository;

import test.contract.domain.RentContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RentContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentContractRepository extends JpaRepository<RentContract, Long> {

}
