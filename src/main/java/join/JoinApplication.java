package join;

import join.storage.StorageProperties;
import join.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@EnableJpaRepositories
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
//@EnableScheduling
public class JoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoinApplication.class, args);
	}


}
