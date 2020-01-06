package org.pstcl.estimate.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.model.EstimateDetailsModel;
import org.pstcl.estimate.model.EstimateModel;
import org.pstcl.estimate.repository.EstimateCostDetailRepository;
import org.pstcl.estimate.repository.EstimateItemDetailRepository;
import org.pstcl.estimate.repository.EstimateReplicationLogRepository;
import org.pstcl.estimate.repository.EstimateRepository;
import org.pstcl.estimate.util.entity.EstimateReplicationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EstimateService {

	@Autowired
	private EstimateRepository estimateRepository;

	@Autowired
	private EstimateReplicationLogRepository replicationLogRepository;




	@Autowired
	private EstimateCostDetailRepository costDetailRepository;

	@Autowired
	private EstimateItemDetailRepository estimateItemDetailRepository;


	//	public EstimateModel getLatestEstimatesList(HttpServletRequest request)
	//	{
	//		EstimateModel estimateModel=new EstimateModel();
	//
	//		EstimateReplicationLog latestReplicatedEstimate=replicationLogRepository.findFirstByUserNameOrderByLastAccessTimeDesc(request.getUserPrincipal().getName());
	//		List<Estimate> list =null;
	//
	//		LocalDateTime lastAccessTimeByIpAndUserName=null;
	//		if(null!=latestReplicatedEstimate)
	//		{
	//			lastAccessTimeByIpAndUserName=latestReplicatedEstimate.getLastAccessTime();
	//			list =estimateRepository.findAllWithdtUpdatedAfter(lastAccessTimeByIpAndUserName);
	//
	//		}
	//		else
	//		{
	//			list= estimateRepository.findAll();
	//
	//		}
	//
	//		estimateModel.setNewEstimates(list);
	//		return estimateModel;
	//	}

	@Scheduled(fixedRate = 60 * 1000)
	public void scanForUpdates() {
		try {
			List<EstimateReplicationLog> list= replicationLogRepository.findAll();
			List<EstimateReplicationLog> listToBeDeleted=new ArrayList<EstimateReplicationLog>();
			for (EstimateReplicationLog estimateReplicationLog : list) {
				if(null==estimateReplicationLog.getUpdateDateTime()||null==estimateReplicationLog.getEstimate().getDtUpdated())
				{
					listToBeDeleted.add(estimateReplicationLog);
				}
				else if(estimateReplicationLog.getUpdateDateTime().isBefore(estimateReplicationLog.getEstimate().getDtUpdated()))
				{
					listToBeDeleted.add(estimateReplicationLog);
				}
			}
			list.clear();
			replicationLogRepository.deleteAll(listToBeDeleted);

		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
	}


	public EstimateModel getLatestEstimatesList(HttpServletRequest request)
	{
		EstimateModel estimateModel=new EstimateModel();

		List<String> estimates= replicationLogRepository.findEstimatesByUserName(request.getUserPrincipal().getName());
		List<Estimate> list;
		if(CollectionUtils.isNotEmpty(estimates))
		{
			list= estimateRepository.findNotInList(estimates);
		}
		else
		{
			list= estimateRepository.findAll();
		}

		estimateModel.setNewEstimates(list);
		return estimateModel;
	}


	public EstimateDetailsModel getEstimatesDetails(String estimateInRequest, HttpServletRequest request) {
		EstimateDetailsModel estimateModel=new EstimateDetailsModel();
		Estimate estimate=null;
		if(estimateInRequest!=null)
		{
			estimate=estimateRepository.findById(estimateInRequest).get();

			estimateModel.setEstimate(estimate);
			estimateModel.setEstimateCostDetails(costDetailRepository.findById_estimate_estimateCode(estimate.getEstimateCode()));
			estimateModel.setEstimateItemDetails(estimateItemDetailRepository.findById_estimate_estimateCode(estimate.getEstimateCode()));
		}


		return estimateModel;
	}

	public EstimateDetailsModel getEstimatesDetails(Estimate estimateInRequest, HttpServletRequest request) {
		EstimateDetailsModel estimateModel=new EstimateDetailsModel();
		Estimate estimate=null;
		if(estimateInRequest!=null)
		{
			estimate=estimateRepository.findById(estimateInRequest.getEstimateCode()).get();

			estimateModel.setEstimate(estimate);
			estimateModel.setEstimateCostDetails(costDetailRepository.findById_estimate_estimateCode(estimate.getEstimateCode()));
			estimateModel.setEstimateItemDetails(estimateItemDetailRepository.findById_estimate_estimateCode(estimate.getEstimateCode()));
		}


		return estimateModel;
	}

	public Estimate confirmReplication(String estimateInRequest, HttpServletRequest request) 
	{
		Estimate estimate=null;

		if(estimateInRequest!=null)
		{
			estimate=estimateRepository.findById(estimateInRequest).get();

			if(estimate!=null)
			{
				EstimateReplicationLog estimateReplicationLog=replicationLogRepository.findByEstimate_EstimateCodeAndUserName(estimateInRequest,request.getRemoteUser());
				if(null==estimateReplicationLog)
				{
					estimateReplicationLog=new EstimateReplicationLog();
				}
				estimateReplicationLog.setEstimate(estimate);
				estimateReplicationLog.setClientIp(request.getRemoteAddr());
				estimateReplicationLog.setClientName(request.getRemoteHost());
				estimateReplicationLog.setUserName(request.getUserPrincipal().getName());
				estimateReplicationLog.setUpdateDateTime(estimate.getDtUpdated());
				replicationLogRepository.save(estimateReplicationLog);
			}
		}
		return estimate;
	}


	public Estimate replicationFailed(String estimateInRequest, HttpServletRequest request) 
	{
		Estimate estimate=null;

		if(estimateInRequest!=null)
		{
			estimate=estimateRepository.findById(estimateInRequest).get();

			if(estimate!=null)
			{
				EstimateReplicationLog estimateReplicationLog=replicationLogRepository.findByEstimate_EstimateCodeAndUserName(estimateInRequest,request.getRemoteUser());
				if(null!=estimateReplicationLog)
				{
					replicationLogRepository.delete(estimateReplicationLog);
				}
			}
		}
		return estimate;
	}



}
