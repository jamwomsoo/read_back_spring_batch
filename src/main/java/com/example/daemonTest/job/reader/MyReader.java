package com.example.daemonTest.job.reader;

import com.example.daemonTest.core.dto.CompanyDto;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyReader implements ItemReader<CompanyDto> {

    private static final String API_KEY = "GOOGLEAPIKEY";
    private final GeoApiContext context;
    private List<CompanyDto> companyDtoList;
    private int currentIndex;

    public MyReader() {
        this.context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        this.companyDtoList = new ArrayList<>();
        this.currentIndex = 0;
    }

    @Override
    public CompanyDto read() throws Exception {
        if (companyDtoList.isEmpty()) {
            fetchCompanyData();
        }

        if (currentIndex < companyDtoList.size()) {
            return companyDtoList.get(currentIndex++);
        }

        return null; // 모든 데이터를 읽었을 경우 null 반환
    }

    private void fetchCompanyData() throws InterruptedException, ApiException, IOException {
        LatLng location = new LatLng(37.497942, 127.027621);

        PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, location)
                .rankby(RankBy.DISTANCE)
                .keyword("방탈출")
                .await();

        addCompanyDataToList(response);

        String nextPageToken = response.nextPageToken;

        while (nextPageToken != null) {
            response = PlacesApi.nearbySearchNextPage(context, nextPageToken)
                    .await();

            addCompanyDataToList(response);

            nextPageToken = response.nextPageToken;
        }
    }

    private void addCompanyDataToList(PlacesSearchResponse response) throws InterruptedException, ApiException, IOException {
        for (PlacesSearchResult result : response.results) {
            String placeId = result.placeId;
            PlaceDetails placeDetails = PlacesApi.placeDetails(context, placeId).await();
            if (placeDetails != null) {
                GeocodingResult[] results = GeocodingApi.geocode(context, placeDetails.formattedAddress)
                        .language("ko")
                        .await();

                if (results != null && results.length > 0) {
                    GeocodingResult geocodingResult = results[0];
                    String formattedAddress = geocodingResult.formattedAddress;
                    String url              = placeDetails.website == null ? "" : placeDetails.website.toString();
                    if (formattedAddress.contains("강남구")
                            || formattedAddress.contains("서초구")
                            || formattedAddress.contains("잠실구")) {
                        String latitude = String.valueOf(result.geometry.location.lat);
                        String longitude = String.valueOf(result.geometry.location.lng);
                        // 상세 정보 사용 예시
                        System.out.println("장소 이름: " + placeDetails.name);
                        System.out.println("주소: " + placeDetails.formattedAddress);
                        System.out.println("전화번호: " + placeDetails.formattedPhoneNumber);
                        System.out.println("평균 평점: " + placeDetails.rating);
                        System.out.println("오픈 시간: " + placeDetails.openingHours);
                        System.out.println("korean address: " +  geocodingResult.formattedAddress);
                        System.out.println("Latitude: " + latitude);
                        System.out.println("Longitude: " + longitude);
                        System.out.println("web url : " + placeDetails.website);
                        System.out.println();
                        companyDtoList.add(new CompanyDto(
                                placeDetails.name,
                                formattedAddress,
                                placeDetails.formattedPhoneNumber,
                                placeDetails.rating,
                                latitude,
                                longitude,
                                placeId,
                                url

                        ));
                    }
                }
            }
        }
    }
}