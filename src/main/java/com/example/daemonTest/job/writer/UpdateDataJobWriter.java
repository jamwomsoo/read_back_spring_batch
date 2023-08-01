package com.example.daemonTest.job.writer;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UpdateDataJobWriter implements ItemWriter<Company> {
    private final CompanyRepository companyRepository;
    @Override
    public void write(List<? extends Company> items) throws Exception {
        log.info("UPDATE DATA JOB WRITER START");
        for (Company item : items) {
            companyRepository
                    .findByGooglePlaceId(item.getGooglePlaceId())
                    .update(item);
        }
    }
}
