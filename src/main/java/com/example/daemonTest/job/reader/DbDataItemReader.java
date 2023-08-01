package com.example.daemonTest.job.reader;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.core.repository.CompanyRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbDataItemReader implements ItemReader<CompanyDto> {
    private final CompanyRepository companyRepository;
    private int currentIndex = 0;
    private List<CompanyDto> resultList;


    public DbDataItemReader(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        resultList = new ArrayList<>();
    }

    @Override
    public CompanyDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (resultList.isEmpty()) {
            resultList = companyRepository.findAll()
                    .stream()
                    .map(Company::toDto)
                    .collect(Collectors.toList());
        }

        if (currentIndex < resultList.size()) {
            return resultList.get(currentIndex++);
        }
        return null;
    }
}
