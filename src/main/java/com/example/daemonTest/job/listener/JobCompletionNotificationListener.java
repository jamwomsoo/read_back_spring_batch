package com.example.daemonTest.job.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Job 실행 전에 수행할 로직 작성
        log.info("Spring batch job start");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Job 실행 후에 수행할 로직 작성
        log.info("Spring batch job end");
    }
}