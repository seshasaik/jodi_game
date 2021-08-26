package animo.realcom.mahakubera.exception;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import animo.realcom.mahakubera.config.AbstractPropertiesReader;
import animo.realcom.mahakubera.exception.gameJodi.GameJodiTicketNotFoundException;
import animo.realcom.mahakubera.exception.gameJodi.GameJodiTicketTransactionNotFoundException;
import animo.realcom.mahakubera.exception.gameJodi.InvalidGameJodiTicketTransaction;
import animo.realcom.mahakubera.modal.response.ErrorResponse;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	AbstractPropertiesReader errorMessageReader;

	@ExceptionHandler(value = { InvalidAuthenticatonException.class, GameJodiTicketNotFoundException.class,
			GameJodiTicketTransactionNotFoundException.class, UserNotFoundException.class })
	public ResponseEntity<ErrorResponse> handleApplicationGenericNotFoundException(BaseRuntimeException ex,
			WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(ex, HttpStatus.NOT_FOUND));

	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR));

	}

	@ExceptionHandler(value = { InvalidGameJodiTicketTransaction.class })
	public ResponseEntity<ErrorResponse> handleBadRequestException(BaseRuntimeException ex, WebRequest request) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex, HttpStatus.BAD_REQUEST));

	}

	private ErrorResponse buildErrorResponse(BaseRuntimeException ex, HttpStatus status) {

		String userMessage = StringUtils.hasText(ex.getMessage()) ? StringUtils.trimLeadingWhitespace(ex.getMessage())
				: getErrorMessage(ex.getErrorId());

		return buildErrorResponse(status, ex.getErrorId(), userMessage);
	}

	private ErrorResponse buildErrorResponse(Exception ex, HttpStatus status) {
		String errorCode = ExceptionMessageConstants.ERROR_COMMON_MSG_ERCM000001;

		return buildErrorResponse(status, errorCode, getErrorMessage(errorCode));
	}

	private ErrorResponse buildErrorResponse(HttpStatus status, String errorCode, String message) {
		ErrorResponse.ErrorResponseBuilder errorResponseBuilder = ErrorResponse.builder();

		errorResponseBuilder.status(status.name()).error(errorCode.split("_")[1])
				.developerMessage(errorCode.split("_")[0]).userMessage(message).timestamp(LocalDateTime.now());
		return errorResponseBuilder.build();
	}

	private String getErrorMessage(String errorCode) {
		return errorMessageReader.getValue(errorCode);
	}
}
