package parkSystem.park.park.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.dto.response.ParkingAllInfoResDTO;
import parkSystem.park.park.dto.response.ParkingInfoResDTO;
import parkSystem.park.park.repository.ParkingInfoRepository;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkingInfoQueryServiceImpl implements ParkingInfoQueryService {

    private final ParkingInfoRepository parkInfoRepository;


    @Override
    public List<ParkingAllInfoResDTO> parkList() {
      return  parkInfoRepository.findAll().stream()
                .map(ParkingAllInfoResDTO::new).toList();
    }

    @Override
    public ParkingInfoResDTO parkInfo(Long parkInfoId) {
        ParkingInfo findByParkingInfo = parkInfoRepository.findById(parkInfoId).orElseThrow(() -> new IllegalArgumentException("해당 아이디는 없습니다"));

        return new ParkingInfoResDTO(findByParkingInfo);

    }
}
