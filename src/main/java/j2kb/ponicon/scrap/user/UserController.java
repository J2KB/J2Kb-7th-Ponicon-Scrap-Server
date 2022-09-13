package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;

import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.user.dto.GetUsernameSameRes;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    // 회원가입
    // [POST] /user/join
    @PostMapping("/join")
    public BaseResponse join(@RequestBody PostJoinReq postJoinReq){

        // 요청한 값에 대한 validation 처리 필요

        userService.join(postJoinReq);

        return new BaseResponse("회원가입에 성공했습니다");
    }

    // 아이디 중복 확인
    // [GET] user/duplicate?id=
    @GetMapping("/duplicate")
    public BaseResponse<GetUsernameSameRes> checkUsernameDuplicate(@RequestParam(name = "id")String username){

        // id 널값 처리해야함.

        boolean isDuplicate = userService.checkUsernameDuplicate(username);

        GetUsernameSameRes res = new GetUsernameSameRes(isDuplicate);
        return new BaseResponse<GetUsernameSameRes>(res);
    }

    // 일반 로그인
    // [POST] user/login
    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@RequestBody PostLoginReq postLoginReq, HttpServletResponse response){

        User user = userService.login(postLoginReq, response);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }

    // 카카오 로그인 리다이렉션 url
    // [GET] user/login/kakao?code=
    @GetMapping("/login/kakao")
    public BaseResponse<LoginRes> kakaoLogin(@RequestParam(name = "code") String code, HttpServletResponse response){
        System.out.println("code = " + code);

        User user = kakaoService.login(code, response);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }

    // 통합 로그아웃
    // [GET] user/logout
    @GetMapping("/logout")
    public BaseResponse logout(HttpServletResponse response){

        userService.logout(response);

        return new BaseResponse("로그아웃에 성공했습니다");
    }


    /* 테스트용 api */
    @GetMapping("/test/error")
    public BaseResponse error(){
        userService.error();

        return null;
    }

    @GetMapping("/test/error2")
    public BaseResponse error2(){
        userService.error2();

        return null;
    }

    @GetMapping("/test/category")
    public String categorySave(){
        userService.testSave();
        return "카테고리 테스트 세이브";
    }

    @GetMapping("/test/cookie")
    public void getCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies) {
            System.out.printf("%s: %s\n", c.getName(), c.getValue()); // 쿠키 이름과 값 가져오기
        }
    }
    /* 테스트용 api 끝 */
}
