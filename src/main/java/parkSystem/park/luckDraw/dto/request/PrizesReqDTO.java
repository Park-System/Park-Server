package parkSystem.park.luckDraw.dto.request;

import java.time.LocalDateTime;

public record PrizesReqDTO(
        String prizeName,

        int quantity,

        int discountRate,

        LocalDateTime startDate,

        LocalDateTime endDate,

        long luckyDraw_id

) {
}
