package com.example.daemonTest.core.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseTimeEntity{
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_code")
    private Long id;

    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "phone_num")
    private String phoneNum;
    @Column(name = "grade")
    private double grade;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "GOOGLE_PLACE_ID")
    private String googlePlaceId;
    @Column(name = "URL")
    private String url;



    public Company(String companyName
            , String address, String number
            , double aver_rate, String latitude
            , String longitude, String googlePlaceId
                   ,String url
    ){
        this.companyName = companyName;
        this.address = address;
        this.phoneNum = number;
        this.grade = aver_rate;
        this.latitude  = latitude;
        this.longitude = longitude;
        this.googlePlaceId = googlePlaceId;
        this.url           = url;
    }
}
