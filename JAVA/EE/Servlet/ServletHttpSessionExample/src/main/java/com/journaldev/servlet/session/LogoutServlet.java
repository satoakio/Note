package com.journaldev.servlet.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");

    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("JSESSIONID")){
					System.out.println("JSESSIONID="+cookie.getValue());
					break;
				}
			}
    	}

		// 返回和这个request关联的session.
    	HttpSession session = request.getSession(false);
		// invalidate the session if exists
		if(session != null){
			session.invalidate();
    	}
    	response.sendRedirect("login.html");
    }

}
