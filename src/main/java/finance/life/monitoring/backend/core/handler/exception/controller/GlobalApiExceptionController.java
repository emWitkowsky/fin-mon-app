package finance.life.monitoring.backend.core.handler.exception.controller;

import finance.life.monitoring.backend.core.handler.exception.dto.FieldValidationErrorsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class GlobalApiExceptionController {
    @GetMapping("http-message-not-readable")
    ResponseEntity<FieldValidationErrorsDto> handleHttpMessageNotReadableException(
            @RequestBody FieldValidationErrorsDto fieldValidationErrorsDto) {
        return ResponseEntity.ok(fieldValidationErrorsDto);
    }
}
