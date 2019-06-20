package test.contract.repository;

import test.contract.domain.RentalAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RentalAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalAccountRepository extends JpaRepository<RentalAccount, Long> {

}
