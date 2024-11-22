package parkSystem.park.luckDraw.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.repository.LuckyDrawRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LuckyDrawCommandService {

    private final LuckyDrawRepository luckyDrawRepository;

    public LuckDraw createLuckyDraw(LuckyDrawReqDTO luckyDrawReqDTO){
        LuckDraw luckDraw = LuckDraw.builder()
                .drawTitle(luckyDrawReqDTO.drawTitle())
                .description(luckyDrawReqDTO.description())
                .startDate(luckyDrawReqDTO.startDate())
                .endDate(luckyDrawReqDTO.endDate())
                .build();

        return luckyDrawRepository.save(luckDraw);
    }
}
