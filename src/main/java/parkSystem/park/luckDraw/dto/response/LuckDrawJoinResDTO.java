package parkSystem.park.luckDraw.dto.response;

import parkSystem.park.luckDraw.domain.MemberLuckDraw;

public record LuckDrawJoinResDTO(
        Long memberLuckDraw_id,
        Long member_id,
        String username

) {
    public static LuckDrawJoinResDTO toDto(MemberLuckDraw memberLuckDraw){
        return new LuckDrawJoinResDTO(
                memberLuckDraw.getId(),
                memberLuckDraw.getMember().getId(),
                memberLuckDraw.getMember().getUsername()
        );
    }
}
