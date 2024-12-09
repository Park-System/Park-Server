package parkSystem.park.queue.dto.response;

import parkSystem.park.member.domain.Member;

public record QueueWaitingPositionResDTO(
        Long memberId,
        String username,
        Long rank
) {
    public static QueueWaitingPositionResDTO toDTO(Member member, Long rank){
        return new QueueWaitingPositionResDTO(
                member.getId(),
                member.getUsername(),
                rank
        );
    }
}
