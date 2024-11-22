package parkSystem.park.luckDraw.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.service.command.LuckyDrawCommandService;

@Service
@RequiredArgsConstructor
public class LuckyDrawService {

    private final LuckyDrawCommandService luckyDrawCommandService;

    public LuckDraw createLuckyDraw(LuckyDrawReqDTO luckyDrawReqDTO){
        return luckyDrawCommandService.createLuckyDraw(luckyDrawReqDTO);
    }
}
