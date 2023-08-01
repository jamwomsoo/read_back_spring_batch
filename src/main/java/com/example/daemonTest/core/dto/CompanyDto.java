package com.example.daemonTest.core.dto;

import com.example.daemonTest.core.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "NUMBER")
    private String number;
    @Column(name = "AVER_RATE")
    private double aver_rate;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "GOOGLE_PLACE_ID")
    private String googlePlaceId;
    @Column(name = "URL")
    private String url;

    @Override
    public String toString() {
        return "CompanyDto{" +
                "companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", aver_rate=" + aver_rate +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", googlePlaceId='" + googlePlaceId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public CompanyDto(String companyName
            , String address, String number
            , double aver_rate, String latitude
            , String longitude, String googlePlaceId
                      , String url
    ){
        this.companyName = companyName;
        this.address = address;
        this.number = number;
        this.aver_rate = aver_rate;
        this.latitude  = latitude;
        this.longitude = longitude;
        this.googlePlaceId = googlePlaceId;
        this.url          =url;
    }
    public Company toEntity(){
        return Company.builder()
                .companyName(companyName)
                .address(address)
                .phoneNum(number)
                .grade(aver_rate)
                .latitude(latitude)
                .longitude(longitude)
                .googlePlaceId(googlePlaceId)
                .url(url)
                .build();
    }
}
