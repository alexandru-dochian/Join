package join.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.management.FileSystem;

@Service
public class FileSystemStorageService implements StorageService {

    @Autowired
    FileEntityRepository fileEntityRepository;

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file, String userWhoUploaded) {
        // fetching the actual name
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                // save in the project filesystem
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);

                // save in the repository if it's not present
                if (!fileEntityRepository.findByfileName(file.getOriginalFilename()).isPresent())
                {
                    fileEntityRepository.save(new FileEntity(file.getOriginalFilename(), userWhoUploaded));
                }
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public List<FileEntity> loadAll() {
            return fileEntityRepository.findAll();
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {

                Optional<FileEntity> fileEntity =  fileEntityRepository.findByfileName(filename);

                fileEntity.orElseThrow(() -> new StorageException("fileName " + filename + " was not found in the repo!"));

                fileEntityRepository.save(fileEntity.get().wasDownloaded());

                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteFile(String fileName)
    {
        try {
            // fetch the file to be deleted
            Optional<FileEntity> toBeDeleted = fileEntityRepository.findByfileName(fileName);

            if (toBeDeleted.isPresent())
            {
                FileSystemUtils.deleteRecursively(this.rootLocation.resolve(fileName));
                fileEntityRepository.delete(toBeDeleted.get());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
