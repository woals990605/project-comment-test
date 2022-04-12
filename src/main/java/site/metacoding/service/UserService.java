package site.metacoding.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.user.User;
import site.metacoding.domain.user.UserRepository;



@RequiredArgsConstructor
// 트랜잭션을 관리하는 오브젝트 : 서비스
// 기능들의 모임 (비즈니스 로직)
@Service // 컴포넌트 스캔시에 IoC 컨테이너에 등록
public class UserService {

    private final UserRepository userRepository;

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