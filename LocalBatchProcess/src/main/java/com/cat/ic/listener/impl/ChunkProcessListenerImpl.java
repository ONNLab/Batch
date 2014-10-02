package com.cat.ic.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkProcessListenerImpl implements ChunkListener {
	private final Logger log = LoggerFactory
			.getLogger(ChunkProcessListenerImpl.class);

	@Override
	public void afterChunk(ChunkContext context) {
		log.info(" OutputFile= "+context.getStepContext().getStepExecutionContext().get("outputFile") +
				" Write Record= " + context.getStepContext().getStepExecutionContext().get("FlatFileItemWriter.written"));
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		log.debug("afterChunkError" + context.toString());

	}

	@Override
	public void beforeChunk(ChunkContext context) {
		log.debug("beforeChunk " + context.toString());

	}

}
