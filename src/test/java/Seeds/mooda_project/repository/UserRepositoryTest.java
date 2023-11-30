package Seeds.mooda_project.repository;

import Seeds.mooda_project.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    private void clear() {
        em.flush();
        em.clear();
    }

    @AfterEach
    private void after() {
        em.clear();
    }
    // 회원가입
    @Test
    public void 회원가입_성공() throws Exception {
        // given
        User user = User.builder().username("testName").loginId("test").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();

        // when
        User saveUser = userRepository.save(user);

        // then
        User findUser = userRepository.findById(saveUser.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다.")); // 후에 예외클래스 추가 필요


        assertThat(findUser).isSameAs(saveUser);
        assertThat(findUser).isSameAs(user);
    }

    // 회원가입시 아이디 X
    @Test
    public void 회원가입시_아이디_없음_오류() throws Exception{
        // given
        User user = User.builder().username("testName").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();

        // when, then
        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    // 회원가입시 이름 X
    @Test
    public void 회원가입시_이름_없음_오류() throws Exception {
        // given
        User user = User.builder().loginId("testId").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();

        // when, then
        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    // 회원가입시 비밀번호 X
    @Test
    public void 회원가입시_비밀번호_없음_오류() throws Exception {
        // given
        User user = User.builder().loginId("testId").username("testName").passwordCheck("testPassword").nickname("testNickname").build();

        // when, then
        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    // 회원가입시 비밀번호 확인 X
    @Test
    public void 회원가입시_비밀번호확인_없음_오류() throws Exception {
        // given
        User user = User.builder().loginId("testId").username("testName").password("testPassword").nickname("testNickname").build();

        //when, then
        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    // 회원가입시 비밀번호와 비밀번호 확인 불일치
    @Test
    public void 비밀번호확인_불일치() throws Exception{
        // given
        User user = User.builder().loginId("testId").username("testName").password("testPassword1").passwordCheck("testPassword2").nickname("testNickname").build();
        userRepository.save(user);

        // when, then
        assertThat(user.getPassword()).isNotEqualTo(user.getPasswordCheck());
    }

    // 회원가입시 닉네임 X
    @Test
    public void 회원가입시_닉네임_없음_오류() throws Exception{
        // given
        User user = User.builder().loginId("testId").username("testName").password("testPassword").passwordCheck("testPassword").build();

        // when, then
        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    // 회원가입시 중복된 아이디
    @Test
    public void 회원가입시_중복된_아이디_오류() throws Exception{
        // given
        User user1 = User.builder().loginId("testId").username("testName1").password("testPassword1").passwordCheck("testPassword1").nickname("testNickname1").build();
        User user2 = User.builder().loginId("testId").username("testName2").password("testPassword2").passwordCheck("testPassword2").nickname("testNickname2").build();

        userRepository.save(user1);
        clear();

        // when, then
        assertThrows(Exception.class, () -> userRepository.save(user2));
    }

    // 회원가입시 중복된 닉네임
    @Test
    public void 회원가입시_중복된_닉네임_오류() throws Exception{
        // given
        User user1 = User.builder().loginId("testId1").username("testName1").password("testPassword1").passwordCheck("testPassword1").nickname("testNickname").build();
        User user2 = User.builder().loginId("testId2").username("testName2").password("testPassword2").passwordCheck("testPassword2").nickname("testNickname").build();

        userRepository.save(user1);
        clear();

        // when, then
        assertThrows(Exception.class, () -> userRepository.save(user2));
    }

    // 회원수정
    @Test
    public void 회원수정_성공() throws Exception{
        // given
        User user = User.builder().loginId("testId").username("testName").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();
        userRepository.save(user);
        clear();

        String updatePassword = "updatePassword";
        String updateName = "updateName";
        String updateNickname = "updateNickname";

        // when
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new Exception());
        findUser.editUsername(updateName);
        findUser.editNickname(updateNickname);
        findUser.editPassword(updatePassword);
        em.flush();

        // then
        User findUpdateUser = userRepository.findById(findUser.getId()).orElseThrow(() -> new Exception());

        assertThat(findUpdateUser).isSameAs(findUser);
        assertThat(findUpdateUser.getPassword()).isEqualTo(updatePassword);
        assertThat(findUpdateUser.getNickname()).isEqualTo(updateNickname);
        assertThat(findUpdateUser.getUsername()).isEqualTo(updateName);
    }

    // 회원삭제
    @Test
    public void 회원삭제_성공() throws Exception{
        // given
        User user = User.builder().loginId("testId").username("testName").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();
        userRepository.save(user);
        clear();

        // when
        userRepository.delete(user);
        clear();

        // then
        assertThrows(Exception.class, () -> userRepository.findById(user.getId()).orElseThrow(() -> new Exception()));
    }

    // 회원가입시 생성시간
    @Test
    public void 회원가입_생성시간_등록() throws Exception{
        // given
        User user = User.builder().username("testName").loginId("testId").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();
        userRepository.save(user);
        clear();

        // when
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new Exception());

        // then
        assertThat(findUser.getCreatedDate()).isNotNull();
        //assertThat(findUser.getLastModifiedDate()).isNotNull();

    }

    // existsByLoginId 메소드 작동여부
    @Test
    public void existsByLoginId_메소드_정상작동() throws Exception{
        // given
        String userId = "testId";
        User user = User.builder().loginId("testId").username("testName").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();
        userRepository.save(user);
        clear();

        // when, then
        assertThat(userRepository.existsByLoginId(userId)).isTrue();
        assertThat(userRepository.existsByLoginId(userId + "123")).isFalse();
    }

    // existsByUsername 메소드 작동여부
    @Test
    public void existsByUsername_메소드_정상작동() throws Exception{
        // given
        String username = "testName";
        User user = User.builder().loginId("testId").username("testName").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();
        userRepository.save(user);
        clear();

        // when, then
        assertThat(userRepository.existsByUsername(username)).isTrue();
        assertThat(userRepository.existsByUsername(username + "123")).isFalse();
    }

    // existsByNickname 메소드 작동여부
    @Test
    public void existsByNickname_메소드_정상작동() throws Exception{
        // given
        String nickname = "testNickname";
        User user = User.builder().loginId("testId").username("testName").password("testPassword").passwordCheck("testPassword").nickname("testNickname").build();
        userRepository.save(user);
        clear();

        // when, then
        assertThat(userRepository.existsByNickname(nickname)).isTrue();
        assertThat(userRepository.existsByNickname(nickname + "123")).isFalse();
    }

}