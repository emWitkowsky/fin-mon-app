package finance.life.monitoring.backend.core.handler.exception;

import finance.life.monitoring.backend.core.handler.exception.dto.FieldValidationErrorsDto;
import finance.life.monitoring.backend.core.handler.exception.policy.BusinessExceptionPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public enum BusinessExceptionReason implements BusinessExceptionPolicy {
    GOAL_NOT_FOUND("Could not find goal with id", HttpStatus.NOT_FOUND, null),
    PLANNED_TRANSACTION_NOT_FOUND("Could not find planned transaction with id", HttpStatus.NOT_FOUND, null);

    private final String code = name();
    private final String message;
    private final HttpStatus httpStatus;
    private final List<FieldValidationErrorsDto> errors;
}
