package com.cat.ic.task;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class MoveFilesTasklet implements Tasklet, InitializingBean {
	private final Logger log = LoggerFactory.getLogger(MoveFilesTasklet.class);
	private Resource srcDir;
	private String dstDir;
	

	public void setDstDir(String dstDir) {
		this.dstDir = dstDir;
	}

	public void setSrcDir(Resource srcDir) {
		this.srcDir = srcDir;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File dir = srcDir.getFile();
		Assert.state(dir.isDirectory());

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			log.info("move file " + files[i].getName() );
			try {
				if (files[i].renameTo(new File(dstDir
						+ files[i].getName()))) {
					log.info("File " + files[i].getName() + " is moved successful!");
				} else {
					log.error("File is failed to move! dstDir="+dstDir);
				}
			} catch (Exception e) {
				log.error("Exception " + e.getMessage());
			}
		}

		return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(srcDir, "directory must be set");
		Assert.notNull(dstDir, "directory must be set");
	}

}
