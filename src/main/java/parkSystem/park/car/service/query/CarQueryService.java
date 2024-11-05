package parkSystem.park.car.service.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.car.repository.CarsRepository;
import parkSystem.park.common.config.exception.NotFoundException;

import static parkSystem.park.car.CarConst.CARS_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor

public class CarQueryService {

    private final CarsRepository carsRepository;

    public Cars findByCarId(Long carId){
        return carsRepository.findById(carId).orElseThrow(()-> new NotFoundException(CARS_NOT_FOUND));
    }
}
