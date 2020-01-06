package org.pstcl.estimate.repository;

import java.util.List;

import org.pstcl.estimate.util.entity.EstimateReplicationLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EstimateReplicationLogRepository extends CrudRepository<EstimateReplicationLog, Integer>{


	public EstimateReplicationLog findFirstByUserNameOrderByLastAccessTimeDesc( String userName);

	@Query("select e.estimate.estimateCode from EstimateReplicationLog e where e.clientIp=?1 and e.userName = ?2")
	public List<String> findEstimatesByClientIpAndUserName(String clientIp, String userName);

	@Query("select e.estimate.estimateCode from EstimateReplicationLog e where  e.userName = ?1")
	public List<String> findEstimatesByUserName(String userName);


	//public EstimateReplicationLog findByEstimateAndUserName(String estimate,String userName);

	public List<EstimateReplicationLog> findAll();

	public EstimateReplicationLog findByEstimate_EstimateCodeAndUserName(String estimateInRequest, String remoteUser);



}
