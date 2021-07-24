package animo.realcom.mahakubera.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class AbstractPropertiesReader {

	public AbstractPropertiesReader() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	protected Environment environment;

	public String getValue(String key) {
		return environment.getProperty(key);
	}

}
