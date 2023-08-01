package com.example.daemonTest;

import com.example.daemonTest.job.CommonConfig;
import com.example.daemonTest.job.HelloJobConfig;
import com.example.daemonTest.job.UpdateDataJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executor;

@Slf4j
@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
//@EnableBatchProcessing
@Import({HelloJobConfig.class, UpdateDataJobConfig.class, CommonConfig.class})
public class DaemonTestApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DaemonTestApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(DaemonTestApplication.class, args);
		DaemonTestApplication application = context.getBean(DaemonTestApplication.class);
		application.startJobs();
	}


	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job myJob;

	@Autowired
	private Job updateJob;

	// Job을 실행하는 메서드
	private void runJob(Job job) {
		try {
			log.info("RUN JOB : " + job);
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.toJobParameters();

			JobExecution jobExecution = jobLauncher.run(job, jobParameters);
			System.out.println("Job Execution Status: " + jobExecution.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 두 개의 Job을 실행하는 메서드
	public void startJobs() {
		runJob(myJob);
		runJob(updateJob);
	}

}
