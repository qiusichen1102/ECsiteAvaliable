package com.example.demo.util;

import com.example.demo.bean.front.Stock;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class  PageUtil {
		//一般的なページング表示のutil
        public static List<?> PageCommon(List<?> obj, int pageSize, int pageNumber, HttpSession session, Model model){
                int totalItems = obj.size();
                int totalPages = (totalItems + pageSize-1)/pageSize;
                int fromIndex = (pageNumber -1 ) * pageSize;
                int toIndex = Math.min(fromIndex+pageSize,totalItems);
                List<?> PagedContactList = obj.subList(fromIndex, toIndex);
                session.setAttribute("totalPages",totalPages);
                session.setAttribute("currentPage",pageNumber);
                return PagedContactList;
        }
}
