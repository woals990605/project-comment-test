package site.metacoding.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.user.User;
import site.metacoding.service.UserService;
import site.metacoding.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    

    // @GetMapping("/logout")
    // public String logout() {
    // session.invalidate();
    // return "redirect:/";
    // }

    // 웹브라우저 -> 회원가입 페이지 주세요!! (O)
    // 앱 -> 회원가입 페이지 주세요? (X)
    // 회원가입폼 (인증 X)
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {

        // 필터의 역할
        // 1. username, password, email 1.null체크, 2.공백체크
        if (user.getId() == null || user.getPassword() == null || user.getEmail() == null) {
            return "redirect:/joinForm";
        }
        if (user.getId().equals("") || user.getPassword().equals("") || user.getEmail().equals("")) {
            return "redirect:/joinForm";
        }

        userService.회원가입(user);

        // redirect:매핑주소
        return "redirect:/loginForm"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인폼 (인증 X)
    @GetMapping("/loginForm")
    public String loginForm(HttpServletRequest request, Model model) {
        // jSessionId=fjsdklfjsadkfjsdlkj333333;remember=ssar
        // request.getHeader("Cookie");
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies(); // jSessionId, remember 두개가 있음.

            for (Cookie cookie : cookies) {
                System.out.println("쿠키값 : " + cookie.getName());
                if (cookie.getName().equals("remember")) {
                    model.addAttribute("remember", cookie.getValue());
                }

            }
        }

        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(User user, HttpServletResponse response) {

        System.out.println("사용자로 부터 받은 username, password : " + user);

        User userEntity = userService.로그인(user);

        if (userEntity != null) {
            session.setAttribute("principal", userEntity);
            if (user.getRemember() != null && user.getRemember().equals("on")) {
                response.addHeader("Set-Cookie", "remember=" + user.getId());
            }
            return "redirect:/";
        } else {
            return "redirect:/loginForm";
        }

    }

    // 로그아웃 - 로그인O
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/loginForm"; // PostController 만들고 수정하자.
    }

// 앱은 이 메서드 요청 안함, 웹만 함
    // SSR할지 CSR할지 선택 -> 이거는 SSR!
    @GetMapping("/s/user/{id}")
    public String detail(/* Model model, */@PathVariable Integer no, Model model) {
        // DB에서 셀렉트해서 모델에 담으면 끝
        // User userEntity = userService.회원정보(id);
        // model.addAttribute("user", userEntity);
        User principal = (User) session.getAttribute("principal");

        // 1. 인증 체크
        if (principal == null) {
            return "error/page1";
        }

        // 2. 권한체크
        if (principal.getNo() != no) {
            return "error/page1";// http 상태코드 403을 함께 리턴!!
        }

        User userEntity = userService.회원정보(no);
        if (userEntity == null) {
            return "error/page1";
        } else {
            model.addAttribute("user", userEntity);
            return "user/detail";
        }

    }

    // 유저수정 페이지 (동적) - 로그인O
    @GetMapping("/s/user/updateForm")
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
    @PutMapping("/s/user/{id}")
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

}
