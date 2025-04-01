package fh.technikum.sam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SamApplication {

	protected static final Logger logger = LoggerFactory.getLogger(SamApplication.class);

	public static void main(String[] args) {
		logger.info("Starting REST-Backend for Car-Sharing App");
		SpringApplication.run(SamApplication.class, args);
	}

}
