package com.example.daemonTest;

import com.example.daemonTest.job.HelloJobConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executor;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
@EnableBatchProcessing
@Import({HelloJobConfig.class})
public class DaemonTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaemonTestApplication.class, args);
	}


}
