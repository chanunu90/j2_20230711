package org.zerock.j2.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.service.MemberService;
import org.zerock.j2.service.SocialService;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final SocialService socialService;

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
        log.info("result: " + result);

        return result;

    }




}
