package parkSystem.park.luckDraw.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.domain.Prizes;
import parkSystem.park.luckDraw.dto.request.PrizesReqDTO;
import parkSystem.park.luckDraw.repository.LuckyDrawRepository;
import parkSystem.park.luckDraw.repository.PrizesRepository;
import parkSystem.park.luckDraw.service.query.LuckDrawQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PrizesCommandService {

    private final LuckDrawQueryService luckDrawQueryService;
    private final PrizesRepository prizesRepository;

    /**
     * 럭키 드로우 경품 등록 서비스
     */

    public List<Prizes> createPrizes(List<PrizesReqDTO> prizesReqDtoList){

        List<Prizes> list = new ArrayList<>();

        for (PrizesReqDTO prizesReqDTO : prizesReqDtoList) {

            LuckDraw luckDrawById = luckDrawQueryService.findLuckDrawById(prizesReqDTO.luckyDraw_id());

            Prizes prizes = Prizes.builder()
                    .prizeName(prizesReqDTO.prizeName())
                    .quantity(prizesReqDTO.quantity())
                    .discountRate(prizesReqDTO.discountRate())
                    .startDate(prizesReqDTO.startDate())
                    .endDate(prizesReqDTO.endDate())
                    .luckDraw(luckDrawById)
                    .build();

            list.add(prizes);
        }

        return prizesRepository.saveAll(list);
    }
}
