package parkSystem.park.luckDraw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.dto.response.CommonResponse;
import parkSystem.park.luckDraw.dto.request.LuckyDrawJoinReqDTO;
import parkSystem.park.luckDraw.dto.request.LuckyDrawReqDTO;
import parkSystem.park.luckDraw.service.facade.LuckyDrawService;
import parkSystem.park.luckDraw.service.facade.MemberLuckDrawService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lucky-draw")
public class LuckyDrawController {

    private final LuckyDrawService luckyDrawService;
    private final MemberLuckDrawService memberLuckDrawService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> createLuckyDraw(@RequestBody LuckyDrawReqDTO luckyDrawReqDTO){
        luckyDrawService.createLuckyDraw(luckyDrawReqDTO);
        CommonResponse commonResponse = new CommonResponse("200 OK", "럭키 드로우 이벤트 등록 완료.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<CommonResponse> joinLuckyDraw(@RequestBody LuckyDrawJoinReqDTO luckyDrawJoinReqDTO){
        memberLuckDrawService.joinLuckyDraw(luckyDrawJoinReqDTO);
        CommonResponse commonResponse = new CommonResponse("200 OK", "럭키 드로우 이벤트 참여 완료.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
