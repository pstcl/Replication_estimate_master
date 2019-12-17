package org.pstcl.estimate.repository;

import java.util.Set;

import org.pstcl.estimate.entity.EstimateCostCompositeKey;
import org.pstcl.estimate.entity.EstimateCostDetail;
import org.springframework.data.repository.CrudRepository;

public interface EstimateCostDetailRepository extends CrudRepository<EstimateCostDetail, EstimateCostCompositeKey>{

	public Set<EstimateCostDetail> findById_estimate_estimateCode(String estimate);

}
