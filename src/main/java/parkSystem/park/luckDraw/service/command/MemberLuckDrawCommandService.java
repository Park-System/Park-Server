package parkSystem.park.luckDraw.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;
import parkSystem.park.luckDraw.dto.request.LuckyDrawJoinReqDTO;
import parkSystem.park.luckDraw.repository.MemberLuckDrawRepository;
import parkSystem.park.luckDraw.service.query.LuckDrawQueryService;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberLuckDrawCommandService {

    private final MemberRepository memberRepository;
    private final LuckDrawQueryService luckDrawQueryService;
    private final MemberLuckDrawRepository memberLuckDrawRepository;

    /**
     * 럭키 드로우 참여 서비스
     */

    public MemberLuckDraw joinLuckyDraw(LuckyDrawJoinReqDTO luckyDrawJoinReqDTO){

        Long luckDrawId = luckyDrawJoinReqDTO.luckDraw_id();
        Long memberId = luckyDrawJoinReqDTO.member_id();

        Member member = memberRepository.findById(memberId).get();
        LuckDraw luckDraw = luckDrawQueryService.findLuckDrawById(luckDrawId);

        MemberLuckDraw memberLuckDraw = MemberLuckDraw.builder()
                .luckDraw(luckDraw)
                .member(member)
                .build();

        return memberLuckDrawRepository.save(memberLuckDraw);
    }

}
