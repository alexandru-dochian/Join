package join;

import join.security.UserRepository;
import join.storage.FileEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    public static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileEntityRepository fileEntityRepository;

    @Scheduled(fixedRate = 5000)
    public void printCurrentUsers()
    {
        log.info("Info about files..." + fileEntityRepository.findAll());
    }

}
