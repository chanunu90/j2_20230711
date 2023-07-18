package org.zerock.j2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.j2.entity.Member;

import java.util.Optional;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testInsert(){
        Member member = Member.builder()
                .email("vltfhem90@naver.com")
                .pw("1111")
                .nickname("chaeunwoo")
                .build();

        memberRepository.save(member);

    }


    @Test
    public void testRead(){

    String email = "user00@aaa.com";

        Optional<Member> result = memberRepository.findById(email);
        Member member = result.orElseThrow();

        System.out.println(member);

    }

}
