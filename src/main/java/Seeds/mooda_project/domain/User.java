package Seeds.mooda_project.domain;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @Column(name = "user_id")
    private Long id; // primary Key

    @Column(nullable = false, length = 30, unique = true)
    private String loginId; // 로그인시 사용하는 ID
    @Column(nullable = false, length = 30)
    private String username; // 유저 이름(실명)
    @Column(nullable = false, length = 30, unique = true)
    private String nickname; // 닉네임
    private String password; // 비밀번호
    private String passwordCheck; // 비밀번호 확인
    @CreatedDate @Column(updatable = false)
    private LocalDateTime createdDate; // 회원가입된 시간
    @LastModifiedDate @Column(updatable = false)
    private LocalDateTime lastModifiedDate;


    public void editNickname(String nickname) {
        this.nickname = nickname;
    }

    public void editUsername(String username){
        this.username = username;
    }

    public void editPassword(String password) {
        this.password = password;
        passwordCheck = password;
    }

}
