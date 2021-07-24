package animo.realcom.mahakubera.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:maha-kubera-constants.properties")
public class AppConstantsReader extends AbstractPropertiesReader {
	
}
