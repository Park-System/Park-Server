package parkSystem.park.park.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.dto.response.ParkApiResDTO;
import parkSystem.park.park.repository.ParkRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ParkOpenApiService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ParkRepository parkRepository;

    String apiUrl = "http://apis.data.go.kr/6260000/BusanPblcPrkngInfoService/getPblcPrkngInfo";
    String serviceKey = "WYIUcexBXmPnj68GHRo%2FviP5RiWfySCWYYgzSx1QePp6MvhonxE6Yb8UhGhBBsKZf%2BcFF7esB1IQRtcAdGHyDQ%3D%3D";
    String resultType = "json";
    String numOfRows = "615";

    public void getParkInfo() throws IOException {

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
        StringBuilder resultSb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            resultSb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result= resultSb.toString();
        apiParsingAndSave(result);
    }

    public void apiParsingAndSave(String result) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Map<String, Object> map = objectMapper.readValue(result, Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");

        Object o = itemsMap.get("item");
        String s = objectMapper.writeValueAsString(o);
        ParkApiResDTO[] parkApiResDTOS = objectMapper.readValue(s, ParkApiResDTO[].class);

        for (ParkApiResDTO parkApiResDTO : parkApiResDTOS) {
            String ftDay = parkApiResDTO.getFtDay();
            String jibunAddr = parkApiResDTO.getJibunAddr();
            String pkFm = parkApiResDTO.getPkFm();
            Integer pkCnt = parkApiResDTO.getPkCnt();
            String pkNam = parkApiResDTO.getPkNam();
            String satEndTe = parkApiResDTO.getSatEndTe();
            String satSrtTe = parkApiResDTO.getSatSrtTe();
            String svcEndTe = parkApiResDTO.getSvcEndTe();
            String svcSrtTe = parkApiResDTO.getSvcSrtTe();
            String temMin = parkApiResDTO.getTemMin();
            String spclNote = parkApiResDTO.getSpclNote();
            ParkingInfo parkingInfo = new ParkingInfo(pkNam, jibunAddr, pkFm, pkCnt, svcSrtTe, svcEndTe, satSrtTe, satEndTe, temMin, ftDay, spclNote);
            parkRepository.save(parkingInfo);
        }

    }

}
