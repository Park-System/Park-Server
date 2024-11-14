package parkSystem.park.reservation;

public final class ReservationConst {


    public  static final  String NOT_FOUND_RESERVATION = "해당 에약을 찾을 수 없습니다.";

    public static final String SUCCESS_DEPOSIT = "보증금 결제가 완료됐습니다";

    public static final int DURATION_TIME = 5;

    public static final int SCHEDULED_CANCEL_TIME = 60000 * 5; // 스케줄러 1분마다 limit 끝난 애들을 감시함

    public static final int SCHEDULED_CANCEL_ROLLBACK_TIME = 60000 * 6; //10 분마다 실패 한 애들 롤백시켜줌

}
