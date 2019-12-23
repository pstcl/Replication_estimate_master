package org.pstcl.estimate.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.pstcl.estimate.entity.Estimate;

import lombok.Getter;
import lombok.Setter;
public class EstimateModel {



	@Getter
	@Setter
	private LocalDateTime previousAccessDateTime;

	@Getter
	@Setter
	private LocalDateTime accessTime;

	@Getter
	private List<Estimate> newEstimates;

	public 	void setNewEstimates(List<Estimate> estimates)
	{
		this.newEstimates=estimates;
		Collections.sort(this.newEstimates);

	}
}

