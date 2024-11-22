package parkSystem.park.luckDraw.service.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.service.facade.LuckyDrawService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LuckyDrawCommandServiceTest {

    @Autowired
    private LuckyDrawService luckyDrawService;

    @Test
    @DisplayName("럭키 드로우 이벤트 등록 테스트")
    void createLuckyDraw(){

        //given
        String start = "2024-10-30";
        String end = "2024-12-31";

        // 문자열을 LocalDateTime으로 변환
        LocalDateTime start_date = LocalDateTime.parse(start + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end_date = LocalDateTime.parse(end + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LuckyDrawReqDTO luckyDrawReqDTO
                = new LuckyDrawReqDTO("테스트 럭키 드로우", "테스트 럭키 드로우 상세", start_date, end_date);

        //when
        LuckDraw luckyDraw = luckyDrawService.createLuckyDraw(luckyDrawReqDTO);

        //then
        Assertions.assertThat(luckyDraw.getDrawTitle()).isEqualTo("테스트 럭키 드로우");

    }

}