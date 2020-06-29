package join.controllers;

import join.security.User;
import join.security.UserForm;
import join.security.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
        UserRepository userRepository;

    // helper method
    public User userFormToUser(UserForm userForm) { return new User(userForm.getUsername(), userForm.getPassword(), userForm.getEmail(), "ROLE_USER"); }

    @PostMapping("/create")
    public String createUser(@Valid UserForm userForm, BindingResult bindingResult) {
            System.out.println("sal");
            if (bindingResult.hasErrors()) { return "create"; }

            log.info("User account created: " + userRepository.save(userFormToUser(userForm)));
            return "redirect:/";
    }

    @GetMapping("/create")
    public String getCreatePage(UserForm userForm) { return "create"; }

    @GetMapping("/")
    public String getHomePage(UserForm userForm) { return "home"; }

    @GetMapping("/login")
    public String getLoginPage(UserForm userForm) { return "login"; }
}
