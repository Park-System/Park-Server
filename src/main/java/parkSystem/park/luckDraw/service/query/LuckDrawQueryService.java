package parkSystem.park.luckDraw.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.LuckDraw;
import parkSystem.park.luckDraw.exception.NotFoundLuckyDrawException;
import parkSystem.park.luckDraw.repository.LuckyDrawRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LuckDrawQueryService {

    private final LuckyDrawRepository luckyDrawRepository;

    public LuckDraw findLuckDrawById(Long DrawId){
        return luckyDrawRepository.findById(DrawId)
                .orElseThrow(()->new NotFoundLuckyDrawException("럭키 드로우 이벤트가 존재하지 않습니다."));
    }
}
