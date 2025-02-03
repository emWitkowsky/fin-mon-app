package finance.life.monitoring.backend.core.handler.exception;

import finance.life.monitoring.backend.core.handler.exception.dto.FieldValidationErrorsDto;
import finance.life.monitoring.backend.core.handler.exception.policy.BusinessExceptionPolicy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class BusinessException extends RuntimeException implements BusinessExceptionPolicy {
    protected final String code;
    protected final String message;
    protected final HttpStatus httpStatus;
    protected final List<FieldValidationErrorsDto> errors;
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(final BusinessExceptionReason reason) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = reason.getHttpStatus();
        this.errors = new ArrayList<>();
    }

    public BusinessException(
            final BusinessExceptionReason reason, HttpStatus overridingHttpStatus) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = overridingHttpStatus;
        this.errors = new ArrayList<>();
    }

    public BusinessException(final BusinessExceptionReason reason, final Object... parameters) {
        if (parameters != null) {
            this.message = String.format(reason.getMessage(), parameters);
        } else {
            this.message = reason.getMessage();
        }

        this.code = reason.getCode();
        this.httpStatus = reason.getHttpStatus();
        this.errors = new ArrayList<>();
    }

    public BusinessException(
            final BusinessExceptionReason reason, final List<FieldValidationErrorsDto> errors) {
        this.errors = Objects.requireNonNullElseGet(errors, ArrayList::new);
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = reason.getHttpStatus();
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String toString() {
        return String.format(
                "BusinessException(errorCode=%s, message=%s, httpStatus=%s, errors=%s)",
                this.getCode(), this.getMessage(), this.getHttpStatus().value(), this.getErrors());
    }
}
