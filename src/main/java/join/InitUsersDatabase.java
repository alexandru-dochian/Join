package join;

import join.security.User;
import join.security.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitUsersDatabase {

    private static final Logger log = LoggerFactory.getLogger(InitUsersDatabase.class);

    @Bean
    CommandLineRunner init(UserRepository repository) {

        return args -> {
            log.info("Creating admin... " +
                    repository.save(new User("admin", "pass", "admin@boss.com", "ROLE_ADMIN")));
            log.info("Creating user... " +
                    repository.save(new User("user", "pass", "user@slave.com", "ROLE_USER")));
        };
    }
}