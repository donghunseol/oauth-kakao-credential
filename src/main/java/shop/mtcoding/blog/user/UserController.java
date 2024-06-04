package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // http://ip주소:8080/oauth/kakao/callback?accessToken=hrt43uoihf8isrd
    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<?> oauthKakaoCallback(@RequestParam("accessToken") String kakaoAccessToken) {
        System.out.println("스프링에서 받은 카카오 토큰 : " + kakaoAccessToken);

        String blogAccessToken = userService.카카오로그인(kakaoAccessToken);
        // 1. 카카오에서 사용자 정보 요청하기

        // 2. code 방식과 동일

        // 3. jwt(스프링서버) 생성해서 앱에게 전달

        return ResponseEntity.ok().header("Authorization", "Bearer " + blogAccessToken).body(new ApiUtil(null));
    }

    // http://ip주소:8080/oauth/kakao/callback?accessToken=hrt43uoihf8isrd
    @GetMapping("/oauth/naver/callback")
    public ResponseEntity<?> oauthNaverCallback(@RequestParam("accessToken") String naverAccessToken) {
        System.out.println("스프링에서 받은 네이버 토큰 : " + naverAccessToken);

        String blogAccessToken = userService.네이버로그인(naverAccessToken);
        // 1. 카카오에서 사용자 정보 요청하기

        // 2. code 방식과 동일

        // 3. jwt(스프링서버) 생성해서 앱에게 전달

        return ResponseEntity.ok().header("Authorization", "Bearer " + blogAccessToken).body(new ApiUtil(null));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO reqDTO) {
        String jwt = userService.로그인(reqDTO);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(new ApiUtil(null));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil(null));
    }
}
