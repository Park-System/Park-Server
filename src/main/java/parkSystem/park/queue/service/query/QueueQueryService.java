package parkSystem.park.queue.service.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.exception.NotFoundMemberException;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.repository.MemberRepository;
import parkSystem.park.queue.dto.response.QueueWaitingPositionResDTO;
import parkSystem.park.queue.exception.NotFoundWaitingPositionException;
import parkSystem.park.queue.repository.QueueRedisRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QueueQueryService {

    private final QueueRedisRepository queueRedisRepository;
    private final MemberRepository memberRepository;

    public QueueWaitingPositionResDTO getWaitingPosition(Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundMemberException("멤버가 존재 하지 않습니다."));

        Long waitingPosition = queueRedisRepository.getWaitingPosition(String.valueOf(memberId));
        if(waitingPosition.equals(-1L)) throw new NotFoundWaitingPositionException("대기자 명단에 없습니다.");

        return QueueWaitingPositionResDTO.toDTO(
                member,
                waitingPosition
        );
    }
}
