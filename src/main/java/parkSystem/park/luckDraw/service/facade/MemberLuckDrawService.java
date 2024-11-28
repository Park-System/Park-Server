package parkSystem.park.luckDraw.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;
import parkSystem.park.luckDraw.dto.request.LuckyDrawJoinReqDTO;
import parkSystem.park.luckDraw.dto.response.LuckDrawJoinResDTO;
import parkSystem.park.luckDraw.service.command.MemberLuckDrawCommandService;

@Service
@RequiredArgsConstructor
public class MemberLuckDrawService {

    private final MemberLuckDrawCommandService memberLuckDrawCommandService;

    public LuckDrawJoinResDTO joinLuckyDraw(LuckyDrawJoinReqDTO luckyDrawJoinReqDTO){ // 럭키 드로우 참여
        return memberLuckDrawCommandService.joinLuckyDraw(luckyDrawJoinReqDTO);
    }
}
