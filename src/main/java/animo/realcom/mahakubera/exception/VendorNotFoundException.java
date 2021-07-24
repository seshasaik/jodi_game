package animo.realcom.mahakubera.exception;

public class VendorNotFoundException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public VendorNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	public VendorNotFoundException(String errorId, String message) {
		// TODO Auto-generated constructor stub
		super(errorId, message);
	}

	public VendorNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public VendorNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VendorNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public VendorNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
