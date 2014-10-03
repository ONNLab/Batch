package com.cat.ic.listener.impl;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
			ExitStatus es = jobExecution.getExitStatus();
	        jobExecution.setExitStatus(new ExitStatus(es.getExitCode(), "JOB_COMPLETED"));
	    }
	    else if(jobExecution.getStatus() == BatchStatus.FAILED){
	    	ExitStatus es = jobExecution.getExitStatus();
	        jobExecution.setExitStatus(new ExitStatus(es.getExitCode(), "JOB_FAILED"));
	    }
	}

}
