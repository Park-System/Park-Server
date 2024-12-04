package parkSystem.park.queue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    @PostMapping("/participate")
    public ResponseEntity<?> luckyDrawParticipate(@RequestParam Long luckDrawId){

        return null;
    }
}
