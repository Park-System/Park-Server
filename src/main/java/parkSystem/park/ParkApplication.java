package parkSystem.park;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import parkSystem.park.park.service.ParkOpenApiService;

@SpringBootApplication
public class ParkApplication {

	public static void main(String[] args) {SpringApplication.run(ParkApplication.class, args);}

}
