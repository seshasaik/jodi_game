package animo.realcom.mahakubera.exception;

import lombok.Getter;

@Getter
public abstract class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String errorId;

	public BaseRuntimeException(String errorId) {
		super();
		this.errorId = errorId;
	}
	
	public BaseRuntimeException(String errorId, String message) {
		super(message);
		this.errorId = errorId;
	}

	public BaseRuntimeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

	

}
