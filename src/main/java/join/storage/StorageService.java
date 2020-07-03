package join.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {
    void init();

    void store(MultipartFile file, String userWhoUploaded);

    List<FileEntity> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    public void deleteFile(String fileName);

    void deleteAll();
}
