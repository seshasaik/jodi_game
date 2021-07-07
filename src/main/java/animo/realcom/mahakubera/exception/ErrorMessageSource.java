package animo.realcom.mahakubera.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import animo.realcom.mahakubera.util.ExceptionMessageConstants;

public abstract class ErrorMessageSource {

	public ErrorMessageSource() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	Environment env;
	
	public String getErrorMessage(String errorCode) {
		return env.getProperty(errorCode, ExceptionMessageConstants.ERROR_COMMON_MSG_ERCM000001);
	}
	
}
