package site.metacoding.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.handler.CustomException;
import site.metacoding.domain.user.User;
import site.metacoding.domain.user.UserRepository;
import site.metacoding.util.email.EmailUtil;
import site.metacoding.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
// 트랜잭션을 관리하는 오브젝트 : 서비스
// 기능들의 모임 (비즈니스 로직)
@Service // 컴포넌트 스캔시에 IoC 컨테이너에 등록
public class UserService {

    private final UserRepository userRepository;
    private final EmailUtil emailUtil;

    @Transactional
    public void 패스워드초기화(PasswordResetReqDto passwordResetReqDto) {
        // 1. username, email 이 같은 것이 있는지 체크 (DB)
        Optional<User> userOp = userRepository.findPassword(
                passwordResetReqDto.getName(),
                passwordResetReqDto.getId(),
                passwordResetReqDto.getEmail());

        // 2. 같은 것이 있다면 DB password 초기화 - BCrypt 해시 - update 하기 (DB)
        if (userOp.isPresent()) {
            User userEntity = userOp.get(); // 영속화
            userEntity.setPassword("9999");
        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다.");
        }

        // 3. 초기화된 비밀번호 이메일로 전송
        emailUtil.sendEmail("woals990605@naver.com", "비밀번호 초기화", "초기화된 비밀번호 : 9999");
    } // 더티체킹 (update)

    public String 유저아이디검사(String id) {
        User userEntity = userRepository.mUsernameSameCheck(id);

        if (userEntity == null) {
            return "없어";
        } else {
            return "있어";
        }
    }

    public String 유저이메일검사(String email) {
        User userEntity = userRepository.mEmailSameCheck(email);

        if (userEntity == null) {
            return "없어";
        } else {
            return "있어";
        }
    }

    @Transactional
    public void 회원가입(User user) {
        // save하면 DB에 INSERT하고 INSERT된 결과를 다시 return 해준다. -> jpa가 리턴해줌

        userRepository.save(user);
    }

    public User 로그인(User user) {
        // 로그인 처리 쿼리를 JPA에서 제공해주지 않음 -> nativeQuery 생성
        return userRepository.mLogin(user.getId(), user.getPassword());
    }

    public User 회원정보(Integer no) {
        Optional<User> userOp = userRepository.findById(no);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            return userEntity;
        } else {
            throw new RuntimeException("아이디를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public User 회원수정(Integer no, User user) {
        // UPDATE user SET password = ?, email = ?, WHERE id = ?
        Optional<User> userOp = userRepository.findById(no); // 영속화 (DB의 row를 영속성 컨텍스트에 옮김)

        if (userOp.isPresent()) {
            // 영속화된 오브젝트 수정
            User userEntity = userOp.get();

            userEntity.setPassword(user.getPassword());
            userEntity.setEmail(user.getEmail());

            return userEntity;
        } else {
            throw new RuntimeException("회원수정에 실패하였습니다.");
        }

    } // 트랜잭션이 걸려있으면 @Service가 종료될 때 변경 감지 후 DB에 UPDATE -> 더티체킹
}