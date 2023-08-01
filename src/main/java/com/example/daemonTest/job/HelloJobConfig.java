package com.example.daemonTest.job;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.core.repository.CompanyRepository;
import com.example.daemonTest.core.service.GoogleMapApiService;
import com.example.daemonTest.job.listener.JobCompletionNotificationListener;
import lombok.RequiredArgsConstructor;
import org.h2.schema.Constant;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Import(CommonConfig.class)
public class HelloJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CompanyRepository companyRepository;
    private final GoogleMapApiService googleMapApiService;
    private static final int SHEDULE_DAY  = 30;
    /*@Bean("hellojob")
    public Job helloJob(){
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer())
                .start(helloStep())
                .build();
    }


    @JobScope
    @Bean("helloStep")
    public Step helloStep(*//*Tasklet tasklet*//*){
        return stepBuilderFactory.get("helloStep")
                .tasklet(tasklet())
                .build();
    }

    @StepScope
    @Bean
    public Tasklet tasklet(){
        return ((contribution, chunkContext) -> {

            String nextPageToken = "START";
            while(!nextPageToken.equals("END")) {
                nextPageToken = placeSearchService
                        .searchPlacesByLocation(37.497942
                                , 127.027621
                                , nextPageToken);
            }
            return  RepeatStatus.FINISHED;
        });
    }*/
    @Bean
    public Step myStep(@Qualifier("myReader") ItemReader<CompanyDto> reader,@Qualifier("myProcessor") ItemProcessor<CompanyDto, Company> processor,@Qualifier("myWriter") ItemWriter<Company> writer) {
        return stepBuilderFactory.get("myStep")
                .<CompanyDto, Company>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    @Scheduled(cron = "0 0 12 1 *") // 매달 1일에 실행(Job)
    public Job myJob(JobCompletionNotificationListener listener, Step myStep) {
        return jobBuilderFactory.get("myJob")
                .incrementer(new RunIdIncrementer())
                .start(myStep)
                .listener(listener)
                /*.listener(listener)
                .flow(myStep)
                .end()*/
                .build();
    }


}
