package Seeds.mooda_project.domain;
import jakarta.persistence.*;

import lombok.*;

import java.net.PasswordAuthentication;
import java.time.LocalDateTime;


// 사용자 아이디, 닉네임 중복 X
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder // 생성자 인자로 너무 많은 파라미터를 받을 경우, 가독성을 위해 Builder 사용
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true, length = 10)
    private String loginId; // 로그인시 사용하는 ID
    private String nickname; // 닉네임
    private String password; // 비밀번호
    private String passwordCheck; // 비밀번호 확인
    private LocalDateTime createdTime; // 회원가입된 시간


    public void edit(String newPassword, String newNickname) {
        this.password = newPassword;
        this.passwordCheck = newPassword;
        this.nickname = newNickname;
    }

}
