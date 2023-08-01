package com.example.daemonTest.job.writer;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class MyWriter implements ItemWriter<Company> {

    private final CompanyRepository companyRepository;

    public MyWriter(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // 데이터를 저장하거나 출력하는 로직 구현
    @Override
    public void write(List<? extends Company> items) throws Exception {
        // 데이터를 저장하거나 출력하는 로직 작성

        // 예를 들면 데이터베이스에 저장, 파일에 쓰기 등
        //System.out.println("Data Saved for Users: " + items);
        for (Company item : items) {
            if (companyRepository.existByCompany(item.getCompanyName(), item.getAddress())) {
                //log.info("IS ITEM EXISTED :: " + companyRepository.existByCompany(item.getCompanyName(), item.getAddress()));
                continue;
            }
            //System.out.println("Data Saved for Users: " + item);
            companyRepository.saveAll(items);
        }
    }
}
