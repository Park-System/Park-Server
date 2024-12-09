package parkSystem.park.queue.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import parkSystem.park.jwt.JwtTokenProvider;
import parkSystem.park.queue.dto.response.QueueParticipateResDTO;
import parkSystem.park.queue.dto.response.QueueWaitingPositionResDTO;
import parkSystem.park.queue.service.facade.QueueService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 럭키드로우 참여 API를 호출 하기 전, 대기열 먼저 확인 후
     * 바로 참여가 가능하면 참가열,
     * 참여가 불가능하면 대기열로 진입
     */
    @PostMapping("/participate")
    public ResponseEntity<QueueParticipateResDTO> luckyDrawParticipate(HttpServletRequest request){
        String accessToken = request.getHeader("access");
        String username = jwtTokenProvider.getAuthentication(accessToken).getName();

        QueueParticipateResDTO queueParticipateResDTO = queueService.luckDrawParticipateVerify(username);
        if(queueParticipateResDTO.participateOK()){
            return new ResponseEntity<>(queueParticipateResDTO, HttpStatus.OK);
        } else {
            queueService.LuckyDrawParticipate(username);
            return new ResponseEntity<>(queueParticipateResDTO, HttpStatus.CONFLICT);
        }
    }

    /**
     * 해당 사용자의 웨이팅 순번을 응답
     */
    @GetMapping("/waiting/{memberId}")
    public ResponseEntity<QueueWaitingPositionResDTO> queueWaitingNumber(@PathVariable Long memberId){
        QueueWaitingPositionResDTO waitingPosition = queueService.getWaitingPosition(memberId);
        return new ResponseEntity<>(waitingPosition, HttpStatus.OK);
    }
}
