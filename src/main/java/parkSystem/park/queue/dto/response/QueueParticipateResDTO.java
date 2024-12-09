package parkSystem.park.queue.dto.response;

import parkSystem.park.member.domain.Member;

public record QueueParticipateResDTO(
        Long memberId,
        String username,
        boolean participateOK
) {
    public static QueueParticipateResDTO toDTO(Member member, boolean participateOK){
        return new QueueParticipateResDTO(
                member.getId(),
                member.getUsername(),
                participateOK
        );
    }
}
