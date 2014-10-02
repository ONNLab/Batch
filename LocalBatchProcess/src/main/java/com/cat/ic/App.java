package com.cat.ic;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		App obj = new App();
		obj.run();
	}

	private void run() {

		String[] springConfig = { "spring/batch-jobs/FilterCDRForMVNOJob.xml" };

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("filterCDRForMVNOJob");

		try {
			JobParameters param = new JobParametersBuilder()
										.addLong("time",System.currentTimeMillis())
										.toJobParameters();

			JobExecution execution = jobLauncher.run(job, param);
			System.out.println("Exit Status : " + execution.getStatus());
			System.out.println("Exit Status : "	+ execution.getAllFailureExceptions());

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		
		((ConfigurableApplicationContext)context).close();
		
	}

}