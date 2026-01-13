package backend.onekakao.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * OREUM 프로젝트 기본 예외 클래스
 * 
 * 비즈니스 로직 처리 중 발생하는 예외들의 기반 클래스입니다.
 * HTTP 상태 코드와 메시지를 포함하여 일관된 예외 처리를 지원합니다.
 */
@Getter
public class TrexException extends RuntimeException {
  private final HttpStatus status;

  public TrexException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public String statusCode() {
    return status.toString();
  }
}
