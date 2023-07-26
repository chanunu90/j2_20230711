package org.zerock.j2.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.service.MemberService;
import org.zerock.j2.service.SocialService;
import org.zerock.j2.util.JwtUtil;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final SocialService socialService;
    //jwt토큰 인증
    private final JwtUtil jwtUtil;

    @GetMapping("kakao")
    public MemberDTO getAuthCode(String code) {

        log.info("=============================================");
        log.info(code);

        String email = socialService.getKakaoEmail(code);

        log.info("=============================================");
        log.info(code);


        MemberDTO memberDTO = memberService.getMemberWithEmail(email);

        return memberDTO;

    }

    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO) {

        log.info("Parameter: " + memberDTO);

        try {
            Thread.sleep(2000);
//            Thread.sleep(200000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MemberDTO result = memberService.login(
                memberDTO.getEmail(),
                memberDTO.getPw()
        );

        result.setAccessToken(jwtUtil.generate(Map.of("email" , result.getEmail()) , 1));
        result.setRefreshToken(jwtUtil.generate(Map.of("email" , result.getEmail()) , 60*24));

        log.info("result: " + result);

        return result;

    }


    @RequestMapping("refresh")
    public Map<String, String> refresh( @RequestHeader("Authorization") String accessToken,
                                        String refreshToken ) {

        log.info("Refresh.... access: " + accessToken);
        log.info("Refresh... refresh: " + refreshToken);

        //accessToken은 만료되었는지 확인

        //refreshToken은 만료되지 않았는지 확인

        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);


        return Map.of("accessToken", jwtUtil.generate(claims, 1),
                "refreshToken", jwtUtil.generate(claims, 60 * 24));

    }


}
