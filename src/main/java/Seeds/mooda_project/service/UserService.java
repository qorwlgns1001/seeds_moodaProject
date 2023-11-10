package Seeds.mooda_project.service;

import Seeds.mooda_project.domain.User;
import Seeds.mooda_project.dto.UserDto;
import Seeds.mooda_project.dto.UserJoinRequest;
import Seeds.mooda_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    /**
     * 회원 정보 저장 (회원가입)
     */
    public void join(UserJoinRequest req) {
        userRepository.save(req.toEntity());
    }

    public BindingResult joinValid(UserJoinRequest req, BindingResult bindingResult) {
        if (req.getLoginId().isEmpty()) { // 아이디가 공란인경우
            bindingResult.addError(new FieldError("req", "loginId", "아이디가 비어있습니다."));
        }
        else if (req.getLoginId().length() < 5 || req.getLoginId().length() > 15) { // 아이디 길이가 맞지 않는경우
            bindingResult.addError(new FieldError("req", "loginId", "아이다는 5자 이상 15자 이하로 설정해주세요."));
        }
        else if (userRepository.existsByLoginId(req.getLoginId())) { // 아이디가 중복되는경우
            bindingResult.addError(new FieldError("req", "loginId", "아이다가 이미 사용중입니다."));
        }

        if (req.getPassword().isEmpty()) {
            bindingResult.addError(new FieldError("req", "password", "비밀번호가 비어있습니다."));
        }

        if (!req.getPassword().equals(req.getPasswordCheck())) {
            bindingResult.addError(new FieldError("req", "passwordCheck", "비밀번호가 일치하지 않습니다"));
        }

        if (req.getNickname().isEmpty()) {
            bindingResult.addError(new FieldError("req", "nickname", "닉네임이 비어있습니다."));
        }
        else if (req.getNickname().length() < 3 || req.getNickname().length() > 10) {
            bindingResult.addError(new FieldError("req", "nickname", "닉네임은 3자 이상 10자 이하로 설정해주세요."));
        }
        else if (userRepository.existsByNickname(req.getNickname())) {
            bindingResult.addError(new FieldError("req", "nickname", "닉네임이 이미 사용중입니다."));
        }
        return bindingResult;
    }

    @Transactional
    public void edit (UserDto userDto, String loginId) {
        User loginUser = userRepository.findByLoginId(loginId).get(); // repository 에서 Id에 해당하는 유저 얻기

        if (userDto.getNewPassword().equals("")) {
            loginUser.edit(loginUser.getPassword(), userDto.getNickname());
        }
    }

    public BindingResult editValidPassword (UserDto userDto, BindingResult bindingResult, String loginId) {
        User loginUser = userRepository.findByLoginId(loginId).get();

        if (userDto.getNowPassword().isEmpty()) {
            bindingResult.addError(new FieldError("userDto", "nowPassword", "현재 비밀번호가 비어있습니다."));
        }
        else if (!userDto.getNowPassword().equals(loginUser.getPassword())) {
            bindingResult.addError(new FieldError("userDto", "nowPassword", "현재 비밀번호가 일치하지 않습니다."));
        }

        if (!userDto.getNewPassword().equals(userDto.getNewPasswordCheck())) {
            bindingResult.addError(new FieldError("userDto", "newPasswordCheck", "비밀번호가 일치하지 않습니다."));
        }
        if (userDto.getNewPassword().isEmpty()) {
            bindingResult.addError(new FieldError("userDto", "newPassword", "새로운 비밀번호가 비어있습니다."));
        }
        if (userDto.getNewPasswordCheck().isEmpty()) {
            bindingResult.addError(new FieldError("userDto", "newPasswordCheck", "새로운 비밀번호 확인란이 비어있습니다."));
        }
        return bindingResult;
    }

    public BindingResult editValidNickname (UserDto userDto, BindingResult bindingResult, String loginId) {
        User loginUser = userRepository.findByLoginId(loginId).get();

        if ()
    }
}
