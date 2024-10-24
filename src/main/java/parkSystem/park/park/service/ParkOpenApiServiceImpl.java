package parkSystem.park.park.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.dto.response.ParkInfoApiResDTO;
import parkSystem.park.park.repository.ParkInfoRepository;
import parkSystem.park.park.repository.ParkSpotRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ParkOpenApiServiceImpl implements ParkOpenApiService{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ParkInfoRepository parkInfoRepository;
    private final ParkSpotRepository parkSpotRepository;
    private final JdbcTemplate jdbcTemplate;

    String apiUrl = "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo";
    String serviceKey = "WYIUcexBXmPnj68GHRo%2FviP5RiWfySCWYYgzSx1QePp6MvhonxE6Yb8UhGhBBsKZf%2BcFF7esB1IQRtcAdGHyDQ%3D%3D";
    String resultType = "json";
    String numOfRows = "615";

    public void getParkInfo() throws IOException {

        //공공 API 호출 url 생성
        StringBuilder sb = new StringBuilder(apiUrl);
        sb.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
        sb.append("&" + URLEncoder.encode("resultType", "UTF-8") + "=" + URLEncoder.encode(resultType, "UTF-8"));
        sb.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));

        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // json 데이터 읽어오기
        StringBuilder resultSb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            resultSb.append(line);
        }
        rd.close();
        conn.disconnect();

        // json 스트링으로 변환
        String result= resultSb.toString();

        // ParkInfo 테이블 저장
        parkInfoRepository.saveAll(apiParsingAndSave(result));
        // ParkSpot 테이블 저장
        parkSpotSave();
    }

    public List<ParkingInfo> apiParsingAndSave(String result) throws JsonProcessingException {

        List<ParkingInfo> list = new ArrayList<>();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Map<String, Object> map = objectMapper.readValue(result, Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");

        Object o = itemsMap.get("item");
        String s = objectMapper.writeValueAsString(o);
        ParkInfoApiResDTO[] parkInfoApiResDTOS = objectMapper.readValue(s, ParkInfoApiResDTO[].class);

        for (ParkInfoApiResDTO parkInfoApiResDTO : parkInfoApiResDTOS) {
            String ftDay = parkInfoApiResDTO.getFtDay();
            String jibunAddr = parkInfoApiResDTO.getJibunAddr();
            String pkFm = parkInfoApiResDTO.getPkFm();
            Integer pkCnt = parkInfoApiResDTO.getPkCnt();
            String pkNam = parkInfoApiResDTO.getPkNam();
            String satEndTe = parkInfoApiResDTO.getSatEndTe();
            String satSrtTe = parkInfoApiResDTO.getSatSrtTe();
            String svcEndTe = parkInfoApiResDTO.getSvcEndTe();
            String svcSrtTe = parkInfoApiResDTO.getSvcSrtTe();
            String temMin = parkInfoApiResDTO.getTemMin();
            String spclNote = parkInfoApiResDTO.getSpclNote();
            ParkingInfo parkingInfo = new ParkingInfo(pkNam, jibunAddr, pkFm, pkCnt, svcSrtTe, svcEndTe, satSrtTe, satEndTe, temMin, ftDay, spclNote);
            list.add(parkingInfo);
        }

        return list;
    }

    public void parkSpotSave() {
        List<ParkingInfo> allParkingInfo = parkInfoRepository.findAll();

        List<ParkingSpot> parkingSpots = new ArrayList<>();

        for (ParkingInfo parkingInfo : allParkingInfo) {
            int parkingAmount = parkingInfo.getParkingAmount();
            for (int i = 1; i <= parkingAmount; i++) {
                ParkingSpot parkingSpot = ParkingSpot.builder()
                .parkingSpotName("Spot " + i)
                .parkingInfo(parkingInfo)
                .build();
                parkingSpots.add(parkingSpot);
            }
        }

        // Bulk Insert
        String sql = "INSERT INTO parking_spot (parking_spot_name, parking_info_id) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ParkingSpot parkingSpot = parkingSpots.get(i);
                ps.setString(1, parkingSpot.getParkingSpotName());
                ps.setLong(2, parkingSpot.getParkingInfo().getId());
            }

            @Override
            public int getBatchSize() {
                return parkingSpots.size();
            }
        });
    }

}
