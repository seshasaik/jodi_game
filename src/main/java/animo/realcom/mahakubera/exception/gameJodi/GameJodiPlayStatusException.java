package animo.realcom.mahakubera.exception.gameJodi;

import animo.realcom.mahakubera.exception.BaseRuntimeException;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;

public class GameJodiPlayStatusException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final String DEFAULT_ERROR_CODE = ExceptionMessageConstants.GAME_JODI_PLAY_STATUS_NOT_FOUND;
	private static final long serialVersionUID = 1L;

	public GameJodiPlayStatusException() {
		super();
		this.errorId = DEFAULT_ERROR_CODE;
		// TODO Auto-generated constructor stub
	}

	public GameJodiPlayStatusException(String errorId, String message) {
		super(errorId, message);
		// TODO Auto-generated constructor stub
	}

	public GameJodiPlayStatusException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public GameJodiPlayStatusException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GameJodiPlayStatusException(String errorId) {
		super(errorId);
		// TODO Auto-generated constructor stub
	}

	public GameJodiPlayStatusException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
