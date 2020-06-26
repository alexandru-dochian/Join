package join;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
//@EnableScheduling
public class JoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoinApplication.class, args);
	}

}
