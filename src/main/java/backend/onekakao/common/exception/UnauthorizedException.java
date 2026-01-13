package backend.onekakao.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 401 Unauthorized 예외 클래스
 * 
 * 인증이 필요하지만 인증 정보가 없거나 잘못된 경우 발생하는 예외입니다.
 * 주로 JWT 토큰 검증 실패 시 사용됩니다.
 */
public class UnauthorizedException extends TrexException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message, STATUS);
    }
}
