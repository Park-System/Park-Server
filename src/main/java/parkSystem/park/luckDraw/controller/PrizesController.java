package parkSystem.park.luckDraw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.dto.response.CommonResponse;
import parkSystem.park.luckDraw.dto.request.PrizesReqDTO;
import parkSystem.park.luckDraw.service.facade.PrizesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prizes")
public class PrizesController {

    private final PrizesService prizesService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> createPrizes(@RequestBody List<PrizesReqDTO> prizesReqDtoList){

        prizesService.createPrizes(prizesReqDtoList);
        CommonResponse commonResponse = new CommonResponse("200 OK", "럭키 드로우 경품 등록 완료.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
