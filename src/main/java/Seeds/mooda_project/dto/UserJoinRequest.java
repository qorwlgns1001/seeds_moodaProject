package Seeds.mooda_project.dto;

import Seeds.mooda_project.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
public class UserJoinRequest { // 회원가입시 사용되면 DTO
    private String loginId; // 로그인시 사용하는 ID
    private String nickname; // 닉네임
    private String password; // 비밀번호
    private String passwordCheck; // 비밀번호 확인

    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
