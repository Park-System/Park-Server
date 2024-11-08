package parkSystem.park.park.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.common.config.exception.NotFoundException;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.repository.ParkingInfoRepository;
import parkSystem.park.reservation.domain.Reservation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static parkSystem.park.park.ParkingConst.PARKING_INFO_NOTFOUND;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ParkingInfoCommandService {

    private final ParkingInfoRepository parkingInfoRepository;


    public ParkingInfo getParkingInfo(Long parkId) {
        return parkingInfoRepository.findById(parkId).orElseThrow(() -> new NotFoundException(PARKING_INFO_NOTFOUND));
    }

    public void updateParkingInfoAmount(List<Reservation> reservations){

        // 결제가 실패한 애들을 해당 아이디와 해당 숫자를 그룹화
        Map<Long, Long> parkingInfoCounts = reservations.stream()
                .collect(Collectors.groupingBy(r -> r.getParkingInfo().getId(), Collectors.counting()));

        parkingInfoCounts.forEach((parkingInfo, count)-> {
            log.info("parkingInfo ={}, count ={}" , parkingInfo, count);
            parkingInfoRepository.updateParkingAmount(count.intValue(), parkingInfo);
        });

    }
}
