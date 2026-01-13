package backend.onekakao.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 500 Internal Server Error 예외 클래스
 * 
 * 서버 내부에서 예상치 못한 오류가 발생했을 때 사용하는 예외입니다.
 * 기본 메시지나 커스텀 메시지를 설정할 수 있습니다.
 */
public class InternalServerException extends TrexException {

    private static final String TEXT = "서버 내부에 오류가 발생했습니다.";
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException() {
        super(TEXT, STATUS);
    }

    public InternalServerException(String text) {
        super(text, STATUS);
    }
}
