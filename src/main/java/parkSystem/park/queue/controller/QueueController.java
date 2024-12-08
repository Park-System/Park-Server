package parkSystem.park.queue.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.jwt.JwtTokenProvider;
import parkSystem.park.queue.service.facade.QueueService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/participate")
    public ResponseEntity<?> luckyDrawParticipate(@RequestParam Long luckDrawId, HttpServletRequest request){
        String accessToken = request.getHeader("access");
        String username = jwtTokenProvider.getAuthentication(accessToken).getName();

        boolean Ok = queueService.luckDrawParticipateVerify(username);
        if(!Ok) queueService.LuckyDrawParticipate(username);

        return null;
    }
}
