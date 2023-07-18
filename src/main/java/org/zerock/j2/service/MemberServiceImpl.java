package org.zerock.j2.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.entity.Member;
import org.zerock.j2.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public static final class MemberLoginException extends  RuntimeException{
        public MemberLoginException(String msg){
            super(msg);
        }
    }

    @Override
    public MemberDTO login(String email, String pw) {

        MemberDTO memberDTO = null;
        try {

            Optional<Member> result = memberRepository.findById(email);

            Member member = result.orElseThrow();

            if(!member.getPw().equals(pw)){
                throw new MemberLoginException("Password Incorrect");
            }

            memberDTO = MemberDTO.builder()
                    .email(member.getEmail())
                    .pw("")
                    .nickname(member.getNickname())
                    .admin(member.isAdmin())
                    .build();

        }catch (Exception e){
            throw new MemberLoginException(e.getMessage());
        }

        return memberDTO;
    }

    @Override
    public MemberDTO getMemberWithEmail(String email) {

        Optional<Member> result = memberRepository.findById(email);

        if(result.isPresent()){
            //가입된 정보가 있다.
            Member member = result.get();

            MemberDTO memberDTO = MemberDTO.builder()
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .admin(member.isAdmin())
                    .build();

            return memberDTO;
        }
        //가입정보가 없으면(데이터베이스가 없다면)
        Member socialMember = Member.builder()
                .email(email)
                .pw(UUID.randomUUID().toString())
                .nickname("SOCIAL_MEMBER")
                .build();

        memberRepository.save(socialMember);


        MemberDTO memberDTO = MemberDTO.builder()
                .email(socialMember.getEmail())
                .nickname(socialMember.getNickname())
                .admin(socialMember.isAdmin())
                .build();

        return memberDTO;
    }

}
