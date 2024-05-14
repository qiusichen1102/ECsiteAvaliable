package com.example.demo.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CookieUtil {
    public static boolean isCookieExpired(Cookie cookie) {
        int maxAge = cookie.getMaxAge();

        if (maxAge > 0) {
            long currentTime = System.currentTimeMillis() / 1000;

            return maxAge < currentTime; //現在時刻よりも有効期限が過ぎている場合、期限切れと表示されます
        }
        return false; //maxAgeが負の値の場合、セッションCookieと見なされ、期限切れになりません
    }

    //すべてのCookie値を検索し、目標のCookie値が存在するかどうかを確認します。目標のCookie値が存在する場合は、その値を返します
    public static String checkCookie(HttpServletRequest request, String CookieValue){
        Cookie[] cookies = request.getCookies();
        String rememberMeToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieValue)) {
                    rememberMeToken = cookie.getValue();
                    break;
                }
            }
        }
        return rememberMeToken;
    }
}
