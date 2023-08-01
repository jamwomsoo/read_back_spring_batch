# read_back_spring_batch

•구글 지도 API(GeoApiContext, PlacesApi, GeocodingApi)에서 방탈출 가게 위치 등의 정보를 받아와서 DB에 저장 해주는 batch작업

•location = new LatLng(37.497942, 127.027621)-> 강남역 위도,경도를 중심으로 주소에 강남구, 서초구, 송파구인 곳만 포함시

•shedule 매달 1월 1일(@Scheduled(cron = "0 0 12 1 *") )

•job 종류
myjob - 새로 등록된 방탈출 카페를 디비로 들고옴
updatejob - db에 등록된 방탈출 카페의 정보가 수정시 비교 후 update 진행


