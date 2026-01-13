package backend.onekakao.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 404 Not Found 예외 클래스
 * 
 * 요청한 리소스(사용자, 게시물 등)를 찾을 수 없을 때 발생하는 예외입니다.
 * 표준화된 메시지 형식을 사용하여 일관성 있는 에러 메시지를 제공합니다.
 */
public class NotFoundException extends TrexException {

    private static final String MESSAGE = "존재하지 않는 %s입니다.";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String target) {
        super(String.format(MESSAGE, target), STATUS);
    }
}
