package org.pstcl.estimate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.model.EstimateDetailsModel;
import org.pstcl.estimate.model.EstimateModel;
import org.pstcl.estimate.service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EstimateController {

	@Autowired
	private EstimateService estimateService;

	@CrossOrigin(allowCredentials="true")
	@GetMapping(value = "/estimates") 
	public  EstimateModel getNewEstimates(HttpServletResponse response,HttpServletRequest request) {
		return estimateService.getLatestEstimatesList(request);
	}
	
	@CrossOrigin(allowCredentials="true")
	@GetMapping(value = "/estimateDetails/{estimateCode}",  produces = "application/json") 
	public  EstimateDetailsModel getNewEstimatesItems(@PathVariable("estimateCode") String estimate,HttpServletResponse response,HttpServletRequest request) {
		
		return estimateService.getEstimatesDetails(estimate,request);
	}
	
	
	
	@CrossOrigin(allowCredentials="true")
	@GetMapping(value = "/confirmReplication/{estimateCode}",  produces = "application/json") 
	public  Estimate confirmReplication(@PathVariable("estimateCode") String estimate,HttpServletResponse response,HttpServletRequest request) {
		return estimateService.confirmReplication(estimate,request);
	}
	
	
	@CrossOrigin(allowCredentials="true")
	@GetMapping(value = "/replicationFailed/{estimateCode}",  produces = "application/json") 
	public  Estimate replicationFailed(@PathVariable("estimateCode") String estimate,HttpServletResponse response,HttpServletRequest request) {
		return estimateService.replicationFailed(estimate,request);
	}
	
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
