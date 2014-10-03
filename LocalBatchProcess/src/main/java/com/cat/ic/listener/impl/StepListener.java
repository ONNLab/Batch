package com.cat.ic.listener.impl;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		ExitStatus es = stepExecution.getExitStatus();
		if( stepExecution.getStatus() == BatchStatus.COMPLETED ){
			
			stepExecution.setExitStatus(new ExitStatus(es.getExitCode(), "STEP_COMPLETED"));
	    }
	    else if(stepExecution.getStatus() == BatchStatus.FAILED){

	    	stepExecution.setExitStatus(new ExitStatus(es.getExitCode(), "STEP_FAILED"));
	    }
		return null;
	}

}
