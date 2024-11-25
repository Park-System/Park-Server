package parkSystem.park.luckDraw.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;
import parkSystem.park.luckDraw.dto.request.LuckyDrawJoinReqDTO;
import parkSystem.park.luckDraw.service.command.MemberLuckDrawCommandService;

@Service
@RequiredArgsConstructor
public class MemberLuckDrawService {

    private final MemberLuckDrawCommandService memberLuckDrawCommandService;

    public MemberLuckDraw joinLuckyDraw(LuckyDrawJoinReqDTO luckyDrawJoinReqDTO){
        return memberLuckDrawCommandService.joinLuckyDraw(luckyDrawJoinReqDTO);
    }
}
