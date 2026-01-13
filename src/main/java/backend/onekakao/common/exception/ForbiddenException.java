package backend.onekakao.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 403 Forbidden 예외 클래스
 * 
 * 인증은 되었지만 해당 자원에 대한 접근 권한이 없을 때 발생하는 예외입니다.
 * 규정된 메시지로 일관된 에러 응답을 제공합니다.
 */
public class ForbiddenException extends TrexException {

    private static final String MESSAGE = "접근 권한이 없습니다";
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public ForbiddenException() {
        super(MESSAGE, STATUS);
    }
}
