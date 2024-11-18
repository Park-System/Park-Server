package parkSystem.park.park.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import parkSystem.park.park.service.facade.ParkingOpenApiFacade;

@SpringBootTest
class ParkingOpenApiServiceImplTest {

    @Autowired
    ParkingOpenApiFacade parkOpenApiServiceImpl;
//    @Test
//    void test() throws IOException {
//        parkOpenApiServiceImpl.getParkInfo();
//    }


}