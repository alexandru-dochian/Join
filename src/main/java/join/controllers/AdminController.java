package join.controllers;

import join.security.User;
import join.security.UserRepository;
import join.storage.FileEntity;
import join.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    private final StorageService storageService;

    @Autowired
    public AdminController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {

        model.addAttribute("files", storageService.loadAll());

        model.addAttribute("createdAccounts", userRepository.findAll().size());

        model.addAttribute("filesOnTheServer", storageService.loadAll().size());

        int totalDownloads = 0;
        for (FileEntity file : storageService.loadAll())
        {
            totalDownloads += file.getNrOfDownloads();
        }
        model.addAttribute("totalDownloads", totalDownloads);


        return "admin";
    }

    @GetMapping("/files/download/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/delete/{filename}")
    public String deleteFile(@PathVariable String filename) {

        storageService.deleteFile(filename);

        return "redirect:/admin";
    }
}
