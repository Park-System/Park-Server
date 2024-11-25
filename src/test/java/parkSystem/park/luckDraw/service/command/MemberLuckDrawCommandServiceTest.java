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
import parkSystem.park.luckDraw.repository.LuckyDrawRepository;
import parkSystem.park.luckDraw.service.facade.LuckyDrawService;
import parkSystem.park.luckDraw.service.facade.MemberLuckDrawService;
import parkSystem.park.luckDraw.service.facade.PrizesService;
import parkSystem.park.luckDraw.service.query.LuckDrawQueryService;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.domain.enums.UserType;
import parkSystem.park.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberLuckDrawCommandServiceTest {

    @Autowired
    LuckyDrawService luckyDrawService;
    @Autowired
    PrizesService prizesService;
    @Autowired
    MemberLuckDrawService memberLuckDrawService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LuckyDrawRepository luckyDrawRepository;

    @BeforeEach
    void setUp(){

        // ---- 테스트 멤버 세팅 ----
        Member member = new Member("1234","test@test","test","123", UserRole.USER, UserType.GENERAL);
        memberRepository.save(member);

        // ---- 테스트 럭키 드로우 세팅 ----
        String start = "2024-10-30";
        String end = "2024-12-31";

        // 문자열을 LocalDateTime으로 변환
        LocalDateTime start_date = LocalDateTime.parse(start + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end_date = LocalDateTime.parse(end + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LuckyDrawReqDTO luckyDrawReqDTO
                = new LuckyDrawReqDTO("테스트 럭키 드로우", "테스트 럭키 드로우 상세", start_date, end_date);

        LuckDraw luckyDraw = luckyDrawService.createLuckyDraw(luckyDrawReqDTO);


        List<PrizesReqDTO> list = new ArrayList<>();

        for(int i=1; i<=3;i++){
            PrizesReqDTO prizesReqDTO
                    = new PrizesReqDTO("럭키 드로우 경품 "+i, 1, 50, start_date, end_date, luckyDraw.getId());

            list.add(prizesReqDTO);
        }

        List<Prizes> prizes = prizesService.createPrizes(list);

    }

    @Test
    @DisplayName("럭키 드로우 참여 테스트")
    void joinLuckyDraw(){

        //given
        Member member = memberRepository.findByUsername("test").get();
        List<LuckDraw> all = luckyDrawRepository.findAll();
        LuckDraw luckDraw = all.get(0);

        log.info("{}",member.getUsername());
        log.info("{}",luckDraw.getDrawTitle());

        //when
        LuckyDrawJoinReqDTO luckyDrawJoinReqDTO = new LuckyDrawJoinReqDTO(member.getId(), luckDraw.getId());
        MemberLuckDraw memberLuckDraw = memberLuckDrawService.joinLuckyDraw(luckyDrawJoinReqDTO);

        //then
        Assertions.assertThat(memberLuckDraw.getMember().getUsername()).isEqualTo("test");
        Assertions.assertThat(memberLuckDraw.getLuckDraw().getDrawTitle()).isEqualTo("테스트 럭키 드로우");

    }
}