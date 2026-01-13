package backend.onekakao.common.exception.handler;

import backend.onekakao.common.exception.TrexException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 전역 예외 처리 핸들러
 * 
 * 애플리케이션 전체에서 발생하는 예외를 통합 처리합니다.
 * 사용자 정의 OreumException과 예상치 못한 예외들을 처리하여
 * RFC 7807 ProblemDetail 형식으로 일관된 에러 응답을 제공합니다.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final UncatchedExceptionHandler handler;

    @ExceptionHandler(TrexException.class)
    public ResponseEntity<ProblemDetail> handleCustomException(TrexException e) {
        log.info(e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception e, WebRequest request) {
        log.error(e.getMessage());
        ProblemDetail problemDetail = handleException(e, request);
        HttpStatus statusCode = HttpStatus.valueOf(problemDetail.getStatus());
        ProblemDetail detailsToSend = ProblemDetail.forStatus(statusCode);
        return ResponseEntity.of(detailsToSend).build();
    }

    private ProblemDetail handleException
            (Exception e, WebRequest request) {
        try {
            ProblemDetail problemDetail = (ProblemDetail) Objects.requireNonNull(handler.handleException(e, request))
                    .getBody();
            problemDetail.setProperties(setDetails(getCurrentHttpRequest(), e, HttpStatus.INTERNAL_SERVER_ERROR));
            return problemDetail;
        } catch (Exception ex) {
            return ProblemDetail.forStatusAndDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }



    private static Map<String, Object> setDetails(HttpServletRequest request, Exception exception, HttpStatus status) {
        StackTraceElement origin = exception.getStackTrace()[0];
        Map<String, Object> map = new HashMap<>();
        map.put("httpMethod", request.getMethod());
        map.put("requestUri", request.getRequestURI());
        map.put("statusCode", status.toString());
        map.put("sourceClass", origin.getClassName());
        map.put("sourceMethod", origin.getMethodName());
        map.put("exceptionClass", exception.getClass().getSimpleName());
        map.put("exceptionMessage", exception.getMessage());
        return map;
    }
}
