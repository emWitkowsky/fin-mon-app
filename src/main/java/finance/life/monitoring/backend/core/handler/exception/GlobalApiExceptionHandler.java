package finance.life.monitoring.backend.core.handler.exception;

import finance.life.monitoring.backend.core.handler.exception.dto.ErrorResponseDto;
import finance.life.monitoring.backend.core.handler.exception.dto.FieldValidationErrorsDto;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponseDto> handleUncaughtException(
            final Exception exception, final ServletWebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        Exception.class.getSimpleName(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomUncaughtBusinessException(
            final BusinessException exception, final ServletWebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        exception.getCode(), exception.getMessage(), exception.getErrors());
        return ResponseEntity.status(exception.getHttpStatus()).body(errorResponseDto);
    }

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomUncaughtApplicationException(
            final ApplicationException exception, final ServletWebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(exception.getCode(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    private static ResponseEntity<Object> handleStandardException(
            @Nonnull Exception exception, @Nonnull ErrorCode code) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(code.getName(), code.getMessage());
        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    private static ServletMissingParameter getServletMissingParameter(
            ServletRequestBindingException exception) {
        final String missingParameter;
        final String missingParameterType;

        switch (exception) {
            case MissingRequestHeaderException missingRequestHeaderException -> {
                missingParameter = missingRequestHeaderException.getHeaderName();
                missingParameterType = "header";
            }
            case MissingServletRequestParameterException
                    missingServletRequestParameterException -> {
                missingParameter = missingServletRequestParameterException.getParameterName();
                missingParameterType = "query";
            }
            case MissingPathVariableException missingPathVariableException -> {
                missingParameter = missingPathVariableException.getVariableName();
                missingParameterType = "path";
            }
            default -> {
                missingParameter = "unknown";
                missingParameterType = "unknown";
            }
        }
        return new ServletMissingParameter(missingParameter, missingParameterType);
    }

    @NotNull
    private ResponseEntity<Object> getObjectResponseEntity(
            ErrorCode code, ServletMissingParameter servletMissingParameter) {
        final FieldValidationErrorsDto missingParameterDto =
                FieldValidationErrorsDto.builder()
                        .code(code)
                        .field(servletMissingParameter.missingParameter())
                        .message(
                                format(
                                        "Missing %s parameter with name '%s'",
                                        servletMissingParameter.missingParameterType(),
                                        servletMissingParameter.missingParameter()))
                        .build();

        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        code.getName(),
                        code.getHttpStatus().getReasonPhrase(),
                        singletonList(missingParameterDto));

        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(
            final ConstraintViolationException exception, final ServletWebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final List<FieldValidationErrorsDto> invalidParameters =
                processInvalidParameters(exception);
        final ErrorCode code = ErrorCode.BAD_REQUEST;

        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        code.getName(), code.getHttpStatus().getReasonPhrase(), invalidParameters);

        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @Nonnull final HttpRequestMethodNotSupportedException exception,
            @Nonnull final HttpHeaders headers,
            @Nonnull final HttpStatusCode status,
            @Nonnull final WebRequest request) {
        final ErrorCode code = ErrorCode.METHOD_NOT_ALLOWED;
        return handleStandardException(exception, code);
    }

    @Override
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            @Nonnull HttpMediaTypeNotSupportedException exception,
            @Nonnull final HttpHeaders headers,
            @Nonnull final HttpStatusCode status,
            @Nonnull final WebRequest request) {
        final ErrorCode code = ErrorCode.BAD_REQUEST;
        return handleStandardException(exception, code);
    }

    @Override
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            @Nonnull HttpMediaTypeNotAcceptableException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.BAD_REQUEST;
        return handleStandardException(exception, code);
    }

    @Override
    public ResponseEntity<Object> handleMissingPathVariable(
            @Nonnull MissingPathVariableException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.BAD_REQUEST;
        return handleStandardException(exception, code);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            @Nonnull MissingServletRequestParameterException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.MISSING_REQUEST_PARAMETER;
        ServletMissingParameter servletMissingParameter = getServletMissingParameter(exception);

        return getObjectResponseEntity(code, servletMissingParameter);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(
            @Nonnull MissingServletRequestPartException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.MISSING_REQUEST_PART;

        final FieldValidationErrorsDto missingParameterDto =
                FieldValidationErrorsDto.builder()
                        .code(code)
                        .field(exception.getRequestPartName())
                        .message(
                                format(
                                        "Missing query parameter with name '%s'",
                                        exception.getRequestPartName()))
                        .build();

        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        code.getName(),
                        code.getHttpStatus().getReasonPhrase(),
                        singletonList(missingParameterDto));

        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
            @Nonnull final ServletRequestBindingException exception,
            @Nonnull final HttpHeaders headers,
            @Nonnull final HttpStatusCode status,
            @Nonnull final WebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        ServletMissingParameter servletMissingParameter = getServletMissingParameter(exception);
        final ErrorCode code = ErrorCode.BAD_REQUEST;

        return getObjectResponseEntity(code, servletMissingParameter);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            @Nonnull final HttpMessageNotReadableException exception,
            @Nonnull final HttpHeaders headers,
            @Nonnull final HttpStatusCode status,
            @Nonnull final WebRequest request) {
        final ErrorCode code = ErrorCode.BAD_REQUEST;
        return handleStandardException(exception, code);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(
            @Nonnull TypeMismatchException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        String parameter = exception.getPropertyName();
        if (exception instanceof MethodArgumentTypeMismatchException castedException) {
            parameter = castedException.getName();
        }

        final ErrorCode code = ErrorCode.BAD_REQUEST;
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        code.getName(),
                        "Unexpected type specified",
                        List.of(
                                new FieldValidationErrorsDto(
                                        ErrorCode.BAD_REQUEST, parameter, "Unexpected type")));

        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleConversionNotSupported(
            @Nonnull final ConversionNotSupportedException exception,
            @Nonnull final HttpHeaders headers,
            @Nonnull final HttpStatusCode status,
            @Nonnull final WebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final ErrorCode code = ErrorCode.CONVERSION_NOT_SUPPORTED;
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        code.getName(),
                        format(
                                "Failed to convert %s with value %s",
                                exception.getPropertyName(), exception.getRequiredType()));
        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @Nonnull final MethodArgumentNotValidException exception,
            @Nonnull final HttpHeaders headers,
            @Nonnull final HttpStatusCode status,
            @Nonnull final WebRequest request) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        final ErrorCode code = ErrorCode.BAD_REQUEST;
        final List<FieldValidationErrorsDto> invalidParameters =
                exception.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        FieldValidationErrorsDto.builder()
                                                .code(code)
                                                .field(fieldError.getField())
                                                .message(fieldError.getDefaultMessage())
                                                .build())
                        .toList();

        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(
                        code.getName(), code.getHttpStatus().getReasonPhrase(), invalidParameters);

        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    private List<FieldValidationErrorsDto> processInvalidParameters(
            ConstraintViolationException exception) {
        final List<FieldValidationErrorsDto> invalidParameters = new ArrayList<>();
        exception
                .getConstraintViolations()
                .forEach(
                        constraintViolation -> {
                            Path propertyPath = constraintViolation.getPropertyPath();
                            List<String> path = new ArrayList<>();
                            propertyPath.forEach(property -> path.add(property.toString()));
                            final FieldValidationErrorsDto errors = new FieldValidationErrorsDto();
                            errors.setCode(ErrorCode.BAD_REQUEST);
                            errors.setField(path.getLast());
                            errors.setMessage(constraintViolation.getMessage());
                            invalidParameters.add(errors);
                        });
        return invalidParameters;
    }

    @Override
    public ResponseEntity<Object> handleHandlerMethodValidationException(
            @Nonnull final HandlerMethodValidationException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.BAD_REQUEST;
        final ErrorResponseDto errorResponseDto =
                ErrorResponseBuilder.build(code.getName(), exception.getMessage());
        return ResponseEntity.status(code.getHttpStatus()).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(
            @Nonnull final NoHandlerFoundException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.NOT_FOUND;
        return handleStandardException(exception, code);
    }

    @Override
    public ResponseEntity<Object> handleNoResourceFoundException(
            @Nonnull NoResourceFoundException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        final ErrorCode code = ErrorCode.NOT_FOUND;
        return handleStandardException(exception, code);
    }

    private record ServletMissingParameter(String missingParameter, String missingParameterType) {}
}
