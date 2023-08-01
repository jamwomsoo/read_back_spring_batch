package com.example.daemonTest.job.reader;

import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.core.service.GoogleMapApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GoogleApiItemReader implements ItemReader<CompanyDto> {
    private final GoogleMapApiService googleMapApiService;
    private int currentIndex = 0;
    private List<CompanyDto> resultList;

    public GoogleApiItemReader(GoogleMapApiService googleMapApiService) {
        this.googleMapApiService = googleMapApiService;
        this.resultList = new ArrayList<>();
        this.currentIndex = 0;
    }

    @Override
    public CompanyDto read() throws Exception {
        log.info("GOOGLE API READ METHOD IN");
        if (resultList.isEmpty()) {
            resultList = googleMapApiService.fetchCompanyData();
        }

        if (currentIndex < resultList.size()) {
            return resultList.get(currentIndex++);
        }
        log.info("GOOGLE API READ METHOD  OUT");
        return null;
    }
}
