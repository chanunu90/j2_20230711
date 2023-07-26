package org.zerock.j2.util;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class JWTTests {

    @Autowired
    private JwtUtil jwtUtil;


    @Test
    public void testCreate(){

        //토큰 생성
        Map<String, Object> claim = Map.of( "email" , "user00@aaa.com" );


        String jwtStr = jwtUtil.generate(claim , 10);

        System.out.println(jwtStr);

    }

    @Test
    public void testToken(){

        String token = "111eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJlbWFpbCI6InVzZXIwMEBhYWEuY29tIiwiaWF0IjoxNjg5NzQ0MzgzLCJleHAiOjE2ODk3NDQ5ODN9.Wa9BXxz9WAH_tKukydeVTcQZ2jLbRUiFPUaJhBjbYfU111";


        try{
            jwtUtil.validateToken(token);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
