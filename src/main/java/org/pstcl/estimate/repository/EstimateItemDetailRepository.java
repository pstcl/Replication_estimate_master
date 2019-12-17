package org.pstcl.estimate.repository;

import java.util.Set;

import org.pstcl.estimate.entity.EstimateItemCompositeKey;
import org.pstcl.estimate.entity.EstimateItemDetail;
import org.springframework.data.repository.CrudRepository;

public interface EstimateItemDetailRepository extends CrudRepository<EstimateItemDetail, EstimateItemCompositeKey>{
	
	public Set<EstimateItemDetail> findById_estimate_estimateCode(String estimate);
}
