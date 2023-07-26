package org.zerock.j2.controller.interceptor;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.j2.util.JwtUtil;

import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Component
public class JWTIntercepter implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")){
            return true;
        }

        try {

            String headerStr = request.getHeader("Authorization");

            if(headerStr == null || headerStr.trim().equals("") || headerStr.length() < 7){

                throw new JwtUtil.CustomJWTException("NULL TOKEN");

            }

            String accessToken = headerStr.substring(7);

            Map<String , Object> claims = jwtUtil.validateToken(accessToken);

            log.info("result :" + claims);
//            log.info("======================================================");
//            log.info(accessToken);

        } catch (Exception e){

            response.setContentType("application/json");

//            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Gson gson = new Gson();

            String str = gson.toJson(Map.of("error", e.getMessage()));
//
//
//            //{"키" ,"값"}
//            String str = "{ \"error\":  }";
            response.getOutputStream().write(str.getBytes());

            return false;

        }




        log.info("======================================================");
        log.info(handler);
        log.info("======================================================");
        log.info(jwtUtil);
        log.info("======================================================");


        return true;

    }
}
