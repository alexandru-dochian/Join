package join.controllers;

import join.storage.FileEntity;
import join.storage.FileEntityRepository;
import join.storage.StorageFileNotFoundException;
import join.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class UserController {

    private final StorageService storageService;

    @Autowired
    public UserController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/user")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll());

        return "user";
    }

    @PostMapping("/user")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        // fetching the user who made the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // saving the file
        storageService.store(file, auth.getName());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/user";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
