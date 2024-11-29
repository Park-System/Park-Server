package parkSystem.park.luckDraw.service.command;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;
import parkSystem.park.luckDraw.domain.Prizes;
import parkSystem.park.luckDraw.dto.request.LuckyDrawJoinReqDTO;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.dto.request.PrizesReqDTO;
import parkSystem.park.luckDraw.dto.response.LuckDrawRaffleResDTO;
import parkSystem.park.luckDraw.repository.LuckyDrawRepository;
import parkSystem.park.luckDraw.repository.MemberLuckDrawRepository;
import parkSystem.park.luckDraw.service.facade.LuckyDrawService;
import parkSystem.park.luckDraw.service.facade.MemberLuckDrawService;
import parkSystem.park.luckDraw.service.facade.PrizesService;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class LuckyDrawRaffleCommandServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LuckyDrawService luckyDrawService;
    @Autowired
    PrizesService prizesService;
    @Autowired
    LuckyDrawRepository luckyDrawRepository;
    @Autowired
    MemberLuckDrawService memberLuckDrawService;
    @Autowired
    MemberLuckDrawRepository memberLuckDrawRepository;

    @BeforeEach
    void setUp(){

        // ---- 회원 10명 생성 ----
        for (int i = 1; i <= 10; i++) {
            Member member = new Member("testpwd", "test@test", "user"+i, "test"+i, UserRole.USER, null);
            memberRepository.save(member);
        }

        // ---- 럭키 드로우 세팅 ----
        String start = "2024-10-30";
        String end = "2024-12-31";

        // 문자열을 LocalDateTime으로 변환
        LocalDateTime start_date = LocalDateTime.parse(start + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end_date = LocalDateTime.parse(end + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LuckyDrawReqDTO luckyDrawReqDTO
                = new LuckyDrawReqDTO("테스트 럭키 드로우", "테스트 럭키 드로우 상세", start_date, end_date);
        LuckDraw luckyDraw = luckyDrawService.createLuckyDraw(luckyDrawReqDTO);

        // ---- 경품 세팅 ----
        List<PrizesReqDTO> list = new ArrayList<>();
        for(int i=1; i<=3;i++){
            PrizesReqDTO prizesReqDTO
                    = new PrizesReqDTO("럭키 드로우 경품 "+i, 1, 50, start_date, end_date, luckyDraw.getId());

            list.add(prizesReqDTO);
        }

        List<Prizes> prizes = prizesService.createPrizes(list);

        // ---- 럭키 드로우 참여 세팅 ----
        List<Member> memberList = memberRepository.findAll();
        List<LuckDraw> all = luckyDrawRepository.findAll();
        LuckDraw luckDraw = all.get(0);

        for (Member member : memberList) {
            LuckyDrawJoinReqDTO luckyDrawJoinReqDTO = new LuckyDrawJoinReqDTO(member.getId(), luckDraw.getId());
            memberLuckDrawService.joinLuckyDraw(luckyDrawJoinReqDTO);
        }

        List<MemberLuckDraw> memberLuckDrawList = memberLuckDrawRepository.findAll();
        log.info("setup memberLuckDraw size : {}", memberLuckDrawList.size());

    }

    @Test
    @DisplayName("럭키 드로우 선착순 추첨 테스트")
    void test(){

        //given
        List<LuckDraw> luckDrawList = luckyDrawRepository.findAll();
        LuckDraw luckDraw = luckDrawList.get(0);

        //when
        log.info("경품 추첨 시작!!!");
        List<LuckDrawRaffleResDTO> luckDrawRaffleResDTOS = luckyDrawService.raffleLuckyDraw(luckDraw.getId());

        //then
        Assertions.assertThat(luckDrawRaffleResDTOS.size()).isEqualTo(3);
    }
}