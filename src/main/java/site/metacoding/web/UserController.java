package site.metacoding.web;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.handler.CustomException;
import site.metacoding.domain.user.User;
import site.metacoding.service.UserService;
import site.metacoding.util.UtilValid;
import site.metacoding.web.dto.ResponseDto;
import site.metacoding.web.dto.user.IdFindReqDto;
import site.metacoding.web.dto.user.JoinReqDto;
import site.metacoding.web.dto.user.LoginReqDto;
import site.metacoding.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/api/user/id/same-check")
    public @ResponseBody ResponseDto<String> sameIdCheck(String id) {
        String data = userService.유저아이디검사(id);
        return new ResponseDto<String>(1, "통신성공", data);
    }

    @GetMapping("/api/user/email/same-check")
    public @ResponseBody ResponseDto<String> sameEmailCheck(String email) {
        String data = userService.유저이메일검사(email);
        return new ResponseDto<String>(1, "통신성공", data);
    }

    @GetMapping("/user/id-find-form")
    public String idFindForm() {
        return "/user/idFindForm";
    }

    @PostMapping("/user/id-find")
    public String idFind(@Valid IdFindReqDto idFindReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        userService.아이디찾기(idFindReqDto);

        return "/user/id-show-form";
    }

    @PostMapping("/user/id-show-form")
    public String idShowForm(@Valid IdFindReqDto idFindReqDto, BindingResult bindingResult, Model model) {
        UtilValid.요청에러처리(bindingResult);

        model.addAttribute("id", userService.아이디찾기(idFindReqDto));

        return "/user/idShowForm";
    }

    @GetMapping("/user/password-reset-form")
    public String passwordResetForm() {
        return "/user/passwordResetForm";
    }

    @PostMapping("/user/password-reset")
    public String passwordReset(@Valid PasswordResetReqDto passwordResetReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        userService.패스워드초기화(passwordResetReqDto);

        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);
        System.out.println("나와라 얍 : " + joinReqDto.toString());
        userService.회원가입(joinReqDto.toEntity());

        // redirect:매핑주소
        return "redirect:/login-form"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인폼 (인증 X)
    @GetMapping("/login-form")
    public String loginForm(HttpServletRequest request, Model model) {

        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                System.out.println("쿠키값 : " + cookie.getName());
                if (cookie.getName().equals("remember")) {
                    model.addAttribute("remember", cookie.getValue());
                }

            }
        }

        return "user/loginForm";
    }

    @PostMapping("/api/user/email/same-check")
    public String login(@Valid LoginReqDto loginReqDto, BindingResult bindingResult, HttpServletResponse response) {
        System.out.println("사용자로 부터 받은 username, password : " + loginReqDto);

        UtilValid.요청에러처리(bindingResult);

        Optional<User> userOp = userService.로그인(loginReqDto.toEntity());

        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            userEntity.setRemember(loginReqDto.getRemember());

            session.setAttribute("principal", userEntity);

            if (userEntity.getRemember() != null && userEntity.getRemember().equals("on")) {
                response.addHeader("Set-Cookie", "remember=" + userEntity.getId());
            }

            return "redirect:/";

        } else {
            throw new CustomException("입력하신 아이디나 비밀번호가 틀렸습니다.");
        }

    }

    // 로그아웃 - 로그인O
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login-form";
    }

    // 유저 상세 페이지 (동적 -> DB연동 필요) - 인증(로그인) O
    @GetMapping("/user/{no}")
    public String detail(@PathVariable Integer no, Model model) {

        // 유효성 검사 하기
        User principal = (User) session.getAttribute("principal");

        // 1. 인증 체크 (로그인하지 않고 주소로 접근 막기)
        if (principal == null) {
            return "error/page1";
        }

        // 2. 권한 체크
        if (principal.getNo() != no) {
            return "error/page1";
        }

        User userEntity = userService.회원정보(no);

        if (userEntity == null) {
            // 누군가 고의로 DELETE 하지 않는 이상 거의 타지 않는 오류
            return "error/page1";

        } else {
            model.addAttribute("user", userEntity);
            return "user/detail";
        }

    }

    // 유저수정 페이지 (동적) - 로그인O
    @GetMapping("/s/user/update-form")
    public String updateForm() {
        // 세션값을 출력했는데, 원래는 디비에서 가져와야 함.
        return "user/updateForm";
    }

    // username(X), password(O), email(O)
    // password=1234&email=ssar@nate.com (x-www-form-urlencoded)
    // { "password" : "1234", "email" : "ssar@nate.com" } (application/json)
    // json을 받을 것이기 때문에 Spring이 데이터 받을 때 파싱전략을 변경!!
    // Put요청은 Http Body가 있다. Http Header의 Content-Type에 MIME타입을 알려줘야 한다.

    // @RequestBody -> BufferedReader + JSON 파싱(자바 오브젝트)
    // @ResponseBody -> BufferedWriter + JSON 파싱(자바 오브젝트)

    // 유저수정 - 로그인O
    @PutMapping("/s/user/{no}")
    public @ResponseBody ResponseDto<String> update(@PathVariable Integer no, @RequestBody User user) {

        User principal = (User) session.getAttribute("principal");

        // 1. 인증 체크
        if (principal == null) {
            return new ResponseDto<String>(-1, "인증안됨", null);
        }

        // 2. 권한체크
        if (principal.getNo() != no) {
            return new ResponseDto<String>(-1, "권한없어", null);
        }

        User userEntity = userService.회원수정(no, user);
        session.setAttribute("principal", userEntity);// 세션변경 - 덮어쓰기

        return new ResponseDto<String>(1, "성공", null);
    }

    @RequestMapping("user/delete")
    public @ResponseBody ResponseDto<String> userDelete(@RequestParam Integer no, @RequestParam String userPw,
            Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            User userEntity = userService.deleteUser(no, user);
            return new ResponseDto<String>(1, "성공", null);
        } else {
            return new ResponseDto<String>(-1, "실패", null);
        }
    }

}
