package com.example.daemonTest.job.processor;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


public class MyProcessor implements ItemProcessor<CompanyDto, Company> {

    @Override
    public Company process(CompanyDto item) throws Exception {

        return item.toEntity();
    }
}
