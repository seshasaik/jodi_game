package animo.realcom.mahakubera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MahakuberaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MahakuberaApplication.class, args);
	}
}
