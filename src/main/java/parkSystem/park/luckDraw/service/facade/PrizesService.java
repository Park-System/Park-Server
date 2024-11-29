package parkSystem.park.luckDraw.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.Prizes;
import parkSystem.park.luckDraw.dto.request.PrizesReqDTO;
import parkSystem.park.luckDraw.service.command.PrizesCommandService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrizesService {

    private final PrizesCommandService prizesCommandService;

    public List<Prizes> createPrizes(List<PrizesReqDTO> prizesReqDtoList){ // 럭키 드로우 경품 등록
        return prizesCommandService.createPrizes(prizesReqDtoList);
    }
}
