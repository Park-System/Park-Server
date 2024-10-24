package parkSystem.park.park.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import parkSystem.park.park.domain.ParkingInfo;

import java.io.IOException;
import java.util.List;

public interface ParkOpenApiService {

    public void getParkInfo() throws IOException;
    public List<ParkingInfo> apiParsingAndSave(String result) throws JsonProcessingException;
    public void parkSpotSave();
}
