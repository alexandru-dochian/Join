package join.controllers;

import join.security.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @GetMapping("/user")
    public String getUserPage(Model model) { return "user"; }

    @PostMapping("/user")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
    {
        
        return "user";
    }

}
