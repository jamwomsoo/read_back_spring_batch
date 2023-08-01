package com.example.daemonTest.job.processor;

import com.example.daemonTest.core.domain.Company;
import com.example.daemonTest.core.dto.CompanyDto;
import com.example.daemonTest.core.repository.CompanyRepository;
import com.example.daemonTest.job.reader.DbDataItemReader;
import com.example.daemonTest.job.reader.GoogleApiItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class UpdateDataJobProcessor implements ItemProcessor<CompanyDto, Company> {
    private final CompanyRepository companyRepository;
    /*private final GoogleApiItemReader apiItemReader;
    private final DbDataItemReader    dbDataItemReader;

    public UpdateDataJobProcessor(GoogleApiItemReader apiItemReader, DbDataItemReader dbDataItemReader) {
        this.apiItemReader = apiItemReader;
        this.dbDataItemReader = dbDataItemReader;
    }*/

    /*public UpdateDataJobProcessor() {

    }*/


    @Override
    @Transactional
    public Company process(CompanyDto item) throws Exception {
        if(item != null) {
            Company entity = item.toEntity();

            Company company = companyRepository.findByGooglePlaceId(item.getGooglePlaceId());
            //entity.setId(company.getId());
            if(! compareEntity(entity, company)){

                //log.info(String.valueOf(!company.equals(entity)));
//                log.info("API DATA" + item.toString() + " UPDATE");
                log.info("DB DATA :: " + company.toString() + "---->  API DATA :: " +entity.toString() + " UPDATE!!!");
                return entity;
            }
            //log.info("DB DATA" + item.toString());
        }
        return null;
    }

    private Boolean compareEntity(Company apiData, Company dbData){
        if(!Objects.equals(apiData.getAddress(), dbData.getAddress())) return false;
        if(!Objects.equals(apiData.getCompanyName(), dbData.getCompanyName())) return  false;
        if(!Objects.equals(apiData.getPhoneNum(), dbData.getPhoneNum())) return false;
        if(!Objects.equals(apiData.getGrade(), dbData.getGrade())) return false;
        if(!Objects.equals(apiData.getLatitude(), dbData.getLatitude())) return false;
        if(!Objects.equals(apiData.getLongitude(), dbData.getLongitude())) return false;
        if(!Objects.equals(apiData.getGooglePlaceId(), dbData.getGooglePlaceId())) return false;
        if(!Objects.equals(apiData.getUrl(), dbData.getUrl())) return false;

        return  true;
    }
}
