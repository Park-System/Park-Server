package parkSystem.park.luckDraw.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.domain.Winners;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.dto.response.LuckDrawRaffleResDTO;
import parkSystem.park.luckDraw.service.command.LuckyDrawCommandService;
import parkSystem.park.luckDraw.service.command.LuckyDrawRaffleCommandService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LuckyDrawService {

    private final LuckyDrawCommandService luckyDrawCommandService;
    private final LuckyDrawRaffleCommandService luckyDrawRaffleCommandService;

    public LuckDraw createLuckyDraw(LuckyDrawReqDTO luckyDrawReqDTO){
        return luckyDrawCommandService.createLuckyDraw(luckyDrawReqDTO);
    }

    public List<LuckDrawRaffleResDTO> raffleLuckyDraw(Long luckDrawId){
        return luckyDrawRaffleCommandService.raffleLuckyDraw(luckDrawId);
    }
}
