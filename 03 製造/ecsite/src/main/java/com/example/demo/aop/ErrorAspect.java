package com.example.demo.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Aspect
@Component
public class ErrorAspect {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ErrorAspect(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    //グローバルな例外捕捉で、例外が発生した場合は、セッションとCookieをクリアし、エントリーページに戻ります。
    @AfterThrowing(pointcut = "execution(* com.example.demo..*(..))", throwing = "ex")
    public void handleException(Throwable ex) {
        clearSession();
        clearCookies();
        //redirectToErrorPage();
    }

    //セッションをクリア
    private void clearSession() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    //Cookieをクリア
    private void clearCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies)
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        cookie.setValue(null);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    });
        }
    }

  /*  private void redirectToErrorPage() {
        try {
            response.sendRedirect("./error/errorPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}