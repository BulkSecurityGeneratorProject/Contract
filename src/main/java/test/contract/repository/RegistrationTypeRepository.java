package test.contract.repository;

import test.contract.domain.RegistrationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegistrationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationTypeRepository extends JpaRepository<RegistrationType, Long> {

}
