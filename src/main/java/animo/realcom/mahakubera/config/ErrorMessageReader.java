package animo.realcom.mahakubera.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import animo.realcom.mahakubera.util.ExceptionMessageConstants;

@Configuration
@PropertySource("classpath:error-codes.properties")
public class ErrorMessageReader extends AbstractPropertiesReader {

	public ErrorMessageReader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return environment.getProperty(key, ExceptionMessageConstants.ERROR_COMMON_MSG_ERCM000001);
	}
}
