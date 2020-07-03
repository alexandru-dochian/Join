package join.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByfileName(String fileName);
}
