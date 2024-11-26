package parkSystem.park.luckDraw.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.Prizes;
import parkSystem.park.luckDraw.repository.PrizesRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrizesQueryService {

    private final PrizesRepository prizesRepository;

    public List<Prizes> findByLuckDrawId(Long luckDrawId){
        return prizesRepository.findByLuckDrawId(luckDrawId);
    }
}
