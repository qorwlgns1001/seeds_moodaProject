package Seeds.mooda_project.controller;

import Seeds.mooda_project.domain.User;
import Seeds.mooda_project.repository.UserRepository;
import Seeds.mooda_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {

        return "home";
    }

    @GetMapping ("/users/save")
    public String userLogin() {
        return "login";
    }
}
