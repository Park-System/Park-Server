package parkSystem.park.luckDraw.dto.response;

import parkSystem.park.luckDraw.domain.Winners;

public record LuckDrawRaffleResDTO(
        String username,
        Long rank,
        int discountRate
) {
    public static LuckDrawRaffleResDTO toDto(Winners winners){
        return new LuckDrawRaffleResDTO(
                winners.getMember().getUsername(),
                winners.getPrizes().getId(),
                winners.getPrizes().getDiscountRate()
        );
    }
}
