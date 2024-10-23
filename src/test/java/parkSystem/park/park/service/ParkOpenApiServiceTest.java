package parkSystem.park.park.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParkOpenApiServiceTest {

    @Autowired
    ParkOpenApiService parkOpenApiService;
    @Test
    void test() throws IOException {
        parkOpenApiService.getParkInfo();
    }

}