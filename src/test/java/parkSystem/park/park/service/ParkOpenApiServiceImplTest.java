package parkSystem.park.park.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ParkOpenApiServiceImplTest {

    @Autowired
    ParkOpenApiServiceImpl parkOpenApiServiceImpl;
    @Test
    void test() throws IOException {
        parkOpenApiServiceImpl.getParkInfo();
    }

}