package test.contract.repository;

import test.contract.domain.PersonInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Long> {

}
