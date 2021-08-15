package animo.realcom.mahakubera.exception.gameJodi;

import animo.realcom.mahakubera.exception.BaseRuntimeException;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;

public class GameJodiTicketTransactionNotFoundException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiTicketTransactionNotFoundException(String errorId) {
		super(errorId);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketTransactionNotFoundException(String errorId, String message) {
		super(errorId, message);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketTransactionNotFoundException() {
		// TODO Auto-generated constructor stub
		super.errorId = ExceptionMessageConstants.GAME_JODI_TICKET_TRANSACTION_NOT_FOUND;

	}

	public GameJodiTicketTransactionNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketTransactionNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketTransactionNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
