package backend.onekakao.common.exception.handler;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring MVC 예외 처리 핸들러
 * 
 * Spring MVC에서 발생하는 표준 예외들(예: MethodArgumentNotValidException)을
 * 처리하여 사용자 친화적인 에러 메시지로 변환합니다.
 */
@Component
public class UncatchedExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, Object> validation = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                validation.values().toString()
        );
        return ResponseEntity.of(problemDetail).build();
    }
}
