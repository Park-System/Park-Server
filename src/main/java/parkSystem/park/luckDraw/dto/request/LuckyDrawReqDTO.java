package parkSystem.park.luckDraw.dto.request;

import java.time.LocalDateTime;

public record LuckyDrawReqDTO(
        String drawTitle,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
