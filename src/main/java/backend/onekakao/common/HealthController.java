package backend.onekakao.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
@Tag(name = "Health", description = "Health check API")
public class HealthController {

    @GetMapping
    @Operation(summary = "Health check", description = "Check if the server is running")
    public ResponseEntity<HealthStatus> health() {
        return ResponseEntity.ok(new HealthStatus(HttpStatus.OK, LocalDateTime.now()));
    }

    @Getter
    @AllArgsConstructor
    private static class HealthStatus {
        private HttpStatus status;
        private LocalDateTime timestamp;
    }
}
