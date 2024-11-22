package parkSystem.park.luckDraw.service.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.domain.Prizes;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.dto.request.PrizesReqDTO;
import parkSystem.park.luckDraw.service.facade.LuckyDrawService;
import parkSystem.park.luckDraw.service.facade.PrizesService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class PrizesCommandServiceTest {

    @Autowired
    PrizesService prizesService;

    @Autowired
    LuckyDrawService luckyDrawService;


    @Test
    @DisplayName("럭키 드로우 경품 등록 테스트")
    void createPrizes(){

        //given

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

        //when
        List<Prizes> prizes = prizesService.createPrizes(list);

        //then
        int i=1;
        for (Prizes prize : prizes) {
            Assertions.assertThat(prize.getPrizeName()).isEqualTo("럭키 드로우 경품 "+i++);
        }

    }
}