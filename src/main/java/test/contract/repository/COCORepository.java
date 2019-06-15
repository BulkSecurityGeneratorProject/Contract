package test.contract.repository;

import test.contract.domain.COCO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the COCO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface COCORepository extends JpaRepository<COCO, Long> {

}
