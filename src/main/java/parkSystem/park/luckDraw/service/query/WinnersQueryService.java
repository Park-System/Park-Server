package parkSystem.park.luckDraw.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.Winners;
import parkSystem.park.luckDraw.repository.WinnersRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WinnersQueryService {

    private final WinnersRepository winnersRepository;

    public List<Winners> findByLuckDrawId(Long luckDrawId){
        return winnersRepository.findByLuckDrawId(luckDrawId);
    }
}
