package parkSystem.park.luckDraw.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;
import parkSystem.park.luckDraw.repository.MemberLuckDrawRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberLuckDrawQueryService {

    private final MemberLuckDrawRepository memberLuckDrawRepository;

    public List<MemberLuckDraw> findByLuckDrawId(Long luckDrawId){
        return memberLuckDrawRepository.findByLuckDrawId(luckDrawId);
    }
}
