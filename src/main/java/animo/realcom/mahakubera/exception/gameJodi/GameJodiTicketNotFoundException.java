package animo.realcom.mahakubera.exception.gameJodi;

import animo.realcom.mahakubera.exception.BaseRuntimeException;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;

public class GameJodiTicketNotFoundException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiTicketNotFoundException(String errorId) {
		super(errorId);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketNotFoundException(String errorId, String message) {
		super(errorId, message);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketNotFoundException() {
		// TODO Auto-generated constructor stub
		this.errorId = ExceptionMessageConstants.GAME_JODI_TICKET_NOT_FOUND;
	}

	public GameJodiTicketNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GameJodiTicketNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
