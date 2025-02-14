package finance.life.monitoring.backend.core.handler.exception;

import finance.life.monitoring.backend.core.handler.exception.dto.ErrorResponseDto;
import finance.life.monitoring.backend.core.handler.exception.dto.FieldValidationErrorsDto;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseBuilder {
    private ErrorResponseBuilder() {}

    public static ErrorResponseDto build(final String code, final String message) {
        return buildDetails(code, message);
    }

    public static ErrorResponseDto build(
            final String code, final String message, final List<FieldValidationErrorsDto> errors) {
        return buildDetails(code, message, errors);
    }

    private static ErrorResponseDto buildDetails(final String code, final String message) {
        return buildDetails(code, message, null);
    }

    private static ErrorResponseDto buildDetails(
            final String code, final String message, final List<FieldValidationErrorsDto> errors) {
        final ErrorResponseDto errorResponseDetails = new ErrorResponseDto();
        errorResponseDetails.setCode(code);
        errorResponseDetails.setMessage(message);
        errorResponseDetails.setTimestamp(LocalDateTime.now());
        if (!CollectionUtils.isEmpty(errors)) {
            errorResponseDetails.setErrors(errors);
        }
        return errorResponseDetails;
    }
}
