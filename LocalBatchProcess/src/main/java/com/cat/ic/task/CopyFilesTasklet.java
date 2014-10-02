package com.cat.ic.task;

import java.io.File;
import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class CopyFilesTasklet implements Tasklet, InitializingBean {
	private final Logger log = LoggerFactory.getLogger(CopyFilesTasklet.class);
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
			log.info("copy files " + files[i].getName() );
			try {
				FileUtils.copyFile(files[i], new File(dstDir+"/"+files[i].getName()));
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
