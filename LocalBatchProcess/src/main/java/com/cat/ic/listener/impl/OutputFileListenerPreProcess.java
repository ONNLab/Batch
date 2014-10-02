package com.cat.ic.listener.impl;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;

import com.cat.ic.listener.IOutputFileListener;

/**
 * @author Anusorn Chaikaew
 * 
 */
public class OutputFileListenerPreProcess implements IOutputFileListener {
	private final Logger log = LoggerFactory.getLogger(OutputFileListenerPreProcess.class);
	private String outputKeyName = "outputFile";
	private String inputKeyName = "fileName";
	private String path = "file:outputs/";
	
	public void setPath(String path) {
		this.path = path;
	}

	public void setOutputKeyName(String outputKeyName) {
		this.outputKeyName = outputKeyName;
	}

	public void setInputKeyName(String inputKeyName) {
		this.inputKeyName = inputKeyName;
	}
	
	@Override
	@BeforeStep
	public void createOutputNameFromInput(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		String inputName = stepExecution.getStepName().replace(":", "-");
		
		if (executionContext.containsKey(inputKeyName)) {
			inputName = executionContext.getString(inputKeyName);
		}
		log.info("inputName =[" + inputName + "]");
		if (!executionContext.containsKey(outputKeyName)) {
			executionContext.putString(outputKeyName, path + FilenameUtils.getName(inputName));
		}
		log.info("outputName =[" +executionContext.getString(outputKeyName)+"]");
	}

}
