package parkSystem.park.luckDraw.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;
import parkSystem.park.luckDraw.domain.Prizes;
import parkSystem.park.luckDraw.domain.Winners;
import parkSystem.park.luckDraw.dto.response.LuckDrawRaffleResDTO;
import parkSystem.park.luckDraw.repository.WinnersRepository;
import parkSystem.park.luckDraw.service.query.LuckDrawQueryService;
import parkSystem.park.luckDraw.service.query.MemberLuckDrawQueryService;
import parkSystem.park.luckDraw.service.query.PrizesQueryService;
import parkSystem.park.luckDraw.service.query.WinnersQueryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LuckyDrawRaffleCommandService {

    private final LuckDrawQueryService luckDrawQueryService;
    private final PrizesQueryService prizesQueryService;
    private final MemberLuckDrawQueryService memberLuckDrawQueryService;
    private final WinnersRepository winnersRepository;

    /**
     * 럭키 드로우 추첨 서비스
     */

    public List<LuckDrawRaffleResDTO> raffleLuckyDraw(Long luckDrawId){

        log.info("추첨 서비스 동작");

        LuckDraw luckDraw = luckDrawQueryService.findLuckDrawById(luckDrawId);
        List<MemberLuckDraw> memberLuckDrawList = memberLuckDrawQueryService.findByLuckDrawId(luckDraw.getId());
        List<Prizes> prizesList = prizesQueryService.findByLuckDrawId(luckDraw.getId());

        log.info("memberLuckDrawSize : {}", memberLuckDrawList.size());
        log.info("prizesSize : {}", prizesList.size());

        // 경품 갯수에 맞게 럭키 드로우 랜덤 추첨
        Collections.shuffle(memberLuckDrawList, new Random());
        List<MemberLuckDraw> raffleList = memberLuckDrawList.stream().limit(prizesList.size()).toList();

        log.info("raffleSize : {}", raffleList.size());

        for (MemberLuckDraw memberLuckDraw : raffleList) {
            log.info("당첨자 : {}", memberLuckDraw.getMember().getUsername());
        }

        // 경품에 당첨된 당첨자를 당첨자 테이블(Winners) insert
        int prizesCount=0;
        List<Winners> winnersList = new ArrayList<>();
        for (MemberLuckDraw memberLuckDraw : raffleList) {
            Winners winners = Winners.builder()
                    .member(memberLuckDraw.getMember())
                    .luckDraw(memberLuckDraw.getLuckDraw())
                    .prizes(prizesList.get(prizesCount++))
                    .build();

            winnersList.add(winners);
        }

        winnersRepository.saveAll(winnersList);

        /*List<LuckDrawRaffleResDTO> list = new ArrayList<>();
        for (Winners winners : winnersList) {
            LuckDrawRaffleResDTO luckDrawRaffleResDTO = new LuckDrawRaffleResDTO(
                    winners.getMember().getUsername(),
                    winners.getPrizes().getId(),
                    winners.getPrizes().getDiscountRate()
            );

            list.add(luckDrawRaffleResDTO);
        }*/

        return winnersList.stream().map(LuckDrawRaffleResDTO::toDto).toList();
    }
}
