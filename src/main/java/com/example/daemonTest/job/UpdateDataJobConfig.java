package com.example.daemonTest.job;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.job.listener.JobCompletionNotificationListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Import(CommonConfig.class)
public class UpdateDataJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;



  /*  @Bean
    public JobCompletionNotificationListener jobCompletionNotificationListener() {
        return new JobCompletionNotificationListener();
    }*/



    @Bean
    public Step updateStep(@Qualifier("apiItemReader") ItemReader<CompanyDto> apiItemReader,
                           @Qualifier("dataItemProcessor") ItemProcessor<CompanyDto, Company> dataItemProcessor,
                           @Qualifier("processedDataItemWriter") ItemWriter<Company> processedDataItemWriter) {
        return stepBuilderFactory.get("updateStep")
                .<CompanyDto, Company>chunk(10)
                .reader(apiItemReader) // Use the qualified ItemReader
                .processor(dataItemProcessor) // Use the qualified ItemProcessor
                .writer(processedDataItemWriter) // Use the qualified ItemWriter
                .build();
    }

    @Bean
    @Scheduled(cron = "0 0 12 1 *") // 매달 1일에 실행(Job)
    public Job updateJob(JobCompletionNotificationListener listener,Step updateStep) {
        return this.jobBuilderFactory.get("updateJob")
                .incrementer(new RunIdIncrementer())
                .start(updateStep)
                //.listener(listener)
                //.flow(updateStep)
                //.end()
                .build();
    }

}
