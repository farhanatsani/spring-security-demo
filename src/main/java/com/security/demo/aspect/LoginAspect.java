package com.security.demo.aspect;

import com.security.demo.authentication.AuthenticationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LoginAspect {
    @Before(value = "execution(* com.security.demo.authentication.AuthenticationController.login(..))")
    public void checkBeforeLogin(JoinPoint joinPoint) {

        log.info("Aspect before controller");

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        AuthenticationRequestDTO loginReqDTO = (AuthenticationRequestDTO) joinPoint.getArgs()[0];
        log.info("Login request {}", loginReqDTO.toString());
    }
}
