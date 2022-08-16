package com.sparta.w6.controller;



import com.sparta.w6.controller.response.ResponseDto;
import com.sparta.w6.request.LoginRequestDto;
import com.sparta.w6.request.MemberRequestDto;
import com.sparta.w6.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/member/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }

//  @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    return memberService.reissue(request, response);
//  }
@GetMapping("/user/kakao/callback")
public ResponseDto<?> kakaoLogin(@RequestParam String code, HttpServletResponse response){
    return memberService.kakaoLogin(code, response);
}

    @RequestMapping(value = "/api/member/logout", method = RequestMethod.POST)
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }
}
