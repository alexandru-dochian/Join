package join.controllers;

import join.security.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String getAdminPage(UserForm userForm) { return "admin"; }
}
