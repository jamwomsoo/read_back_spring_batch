package com.example.daemonTest.job;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.core.repository.CompanyRepository;
import com.example.daemonTest.job.listener.JobCompletionNotificationListener;
import com.example.daemonTest.job.processor.MyProcessor;
import com.example.daemonTest.job.reader.MyReader;
import com.example.daemonTest.job.writer.MyWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class HelloJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CompanyRepository companyRepository;

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
    public Step myStep(ItemReader<CompanyDto> reader, ItemProcessor<CompanyDto, Company> processor, ItemWriter<Company> writer) {
        return stepBuilderFactory.get("myStep")
                .<CompanyDto, Company>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job myJob(JobCompletionNotificationListener listener, Step myStep) {
        return jobBuilderFactory.get("myJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(myStep)
                .end()
                .build();
    }

    @Bean
    public ItemReader<CompanyDto> myReader() {
        return new MyReader();
    }

    @Bean
    public ItemProcessor<CompanyDto, Company> myProcessor() {
        return new MyProcessor();
    }

    @Bean
    public ItemWriter<Company> myWriter() {
        return new MyWriter(companyRepository);
    }

    @Bean
    public JobCompletionNotificationListener jobCompletionNotificationListener() {
        return new JobCompletionNotificationListener();
    }
}
