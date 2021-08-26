package animo.realcom.mahakubera.exception;

import animo.realcom.mahakubera.util.ExceptionMessageConstants;

public class UserNotFoundException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public UserNotFoundException(String errorId) {
		super(errorId);
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String errorId, String message) {
		super(errorId, message);
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException() {
		this.errorId = ExceptionMessageConstants.ERROR_USER_ACOUNT_NOT_FOUND;
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
