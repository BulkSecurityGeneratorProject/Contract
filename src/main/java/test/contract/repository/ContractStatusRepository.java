package test.contract.repository;

import test.contract.domain.ContractStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContractStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractStatusRepository extends JpaRepository<ContractStatus, Long> {

}
