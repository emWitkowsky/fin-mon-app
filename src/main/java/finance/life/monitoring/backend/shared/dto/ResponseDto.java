package finance.life.monitoring.backend.shared.dto;

import finance.life.monitoring.backend.core.handler.exception.dto.FieldValidationErrorsDto;
import finance.life.monitoring.backend.shared.enums.SuccessCode;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseDto<T> (
        SuccessCode code,
        String message,
        LocalDateTime timestamp,
        List<FieldValidationErrorsDto> errors,
        T data) {
    public ResponseDto(
            SuccessCode code, String message, List<FieldValidationErrorsDto> errors, T data) {
        this(code, message, LocalDateTime.now(), errors, data);
    }
}
