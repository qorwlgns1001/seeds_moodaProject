package Seeds.mooda_project.controller;

import Seeds.mooda_project.domain.User;
import Seeds.mooda_project.dto.UserJoinRequest;
import Seeds.mooda_project.repository.UserRepository;
import Seeds.mooda_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("userJoinRequest", new UserJoinRequest()); // UserJoinRequest 를 userJoinRequest에 저장
        return "users/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute UserJoinRequest req, BindingResult bindingResult, Model model) {

        // Validation
        if (userService.joinValid(req, bindingResult).hasErrors()) { // 오류가 있을경우 이전페이지로 복귀
            model.addAttribute("errorMessage", "오류가 발생하였습니다.");
            model.addAttribute("nextUrl", "/users/join");
            return "users/printErrorMessage";
        }

        userService.join(req);
        model.addAttribute("message", "회원가입에 성공하였습니다!\n로그인 후 사용 가능합니다.");
        model.addAttribute("nextUrl", "/users/login");
        return "users/printMessage";
    }


    @GetMapping("/{id}")
    public Optional<User> getUserProfile(@PathVariable("id") Long id) {
        return userRepository.findById(id);
    }

    @GetMapping("/all")
    public List<User> getAllUserProfile() {
        //return userRepository.findAll();
        return userService.getAllUserList();
    }

}
