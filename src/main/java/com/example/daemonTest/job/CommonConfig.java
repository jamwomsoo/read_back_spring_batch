package com.example.daemonTest.job;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.core.repository.CompanyRepository;
import com.example.daemonTest.core.service.GoogleMapApiService;
import com.example.daemonTest.job.listener.JobCompletionNotificationListener;
import com.example.daemonTest.job.processor.MyProcessor;
import com.example.daemonTest.job.processor.UpdateDataJobProcessor;
import com.example.daemonTest.job.reader.DbDataItemReader;
import com.example.daemonTest.job.reader.GoogleApiItemReader;
import com.example.daemonTest.job.reader.MyReader;
import com.example.daemonTest.job.writer.MyWriter;
import com.example.daemonTest.job.writer.UpdateDataJobWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@RequiredArgsConstructor
public class CommonConfig {
    private final CompanyRepository companyRepository;
    private final GoogleMapApiService googleMapApiService;

    @Bean
    public ItemReader<CompanyDto> myReader() {
        return new MyReader(googleMapApiService);
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

    @Bean
    ItemProcessor<CompanyDto, Company> dataItemProcessor(){
        return new UpdateDataJobProcessor(companyRepository);
    }

    @Bean
    ItemWriter<Company> processedDataItemWriter(){
        return new UpdateDataJobWriter(companyRepository);
    }

    @Bean
    public ItemReader<CompanyDto> dbItemReader() {
        // 구현 필요: DB에서 데이터를 읽어오는 ItemReader
        return new DbDataItemReader(companyRepository);
    }

    @Bean
    public ItemReader<CompanyDto> apiItemReader() {
        // 구현 필요: API에서 데이터를 읽어오는 ItemReader
        return new GoogleApiItemReader(googleMapApiService);
    }
}
