package animo.realcom.mahakubera.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:maha-kubera-constants.properties")
public class AppConstantsConfig {

	@Autowired
	Environment environment;

	public AppConstantsConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getValue(String key) {
		return environment.getProperty(key);
	}

}
