package Seeds.mooda_project.repository;

import Seeds.mooda_project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByLoginId(String loginId); //
    public Page<User> findAllByNicknameContains(String nickname, PageRequest pageRequest); // 닉네임에 String 이 포함되어있는지 -> Admin이 User 검색시 사용
    public Boolean existsByLoginId(String loginId); // 로그인Id 중복체크용
    public Boolean existsByNickname(String nickname); // 닉네임 중복체크용

}
