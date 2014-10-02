package com.cat.ic.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;

public interface IOutputFileListener {
	@BeforeStep
	public void createOutputNameFromInput(StepExecution stepExecution);
}
