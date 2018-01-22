package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;


@WebServlet("/gb")
public class guestbookServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("환영합니다 Servlet guestbook2"); //웹페이지와 연결됬는지 확인
		request.setCharacterEncoding("utf-8");
		String actionform=request.getParameter("a"); //입력받는 값으로, a 라는 변수를 하나 만들어 줌.
		
		if ("deleteform".equals(actionform)) {
			//System.out.println("deleteform 진입"); //여기까지 웹 접속되는지 확인
			RequestDispatcher rd=request.getRequestDispatcher("deleteform.jsp"); //값을 입력받아, RequestDispatcher객체가 만들어지고, rd로 객체를 받는다. 
																			//request클래스 내의 getRequestDispatcher메소드를 이용해, form.jsp로 보낸다.
			rd.forward(request, response); //rd의 forword메소드를 이용하여, request문서와 response문서를 form.jsp로 보낸다.
			
		} else if ("add".equals(actionform)) {
			System.out.println("add 진입");
			String name=request.getParameter("name"); //폼에서 약속한 변수이름의 값을 받아옴.
			String password=request.getParameter("pass");
			String content=request.getParameter("content");
			
			GuestbookVo vo=new GuestbookVo(1,name,password,content,"");

			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);
			
			System.out.println(vo.toString());
			
			GuestbookDao dao=new GuestbookDao();
			
			dao.insert(vo);
			
			response.sendRedirect("gb?a=list");
		} else if ("delete".equals(actionform)) {
			System.out.println("delete 진입");
			int no = Integer.valueOf(request.getParameter("no"));
			String password=request.getParameter("password");

			GuestbookDao dao=new GuestbookDao();

			dao.delete(no, password);

			response.sendRedirect("gb?a=list");
		} else if ("list".equals(actionform)) {
			System.out.println("list 진입");
			
			GuestbookDao dao=new GuestbookDao();
			
			List<GuestbookVo> list=dao.getList(); 
			
			//어떤 데이터가 올지 모르므로, RequestDispatcher 객체에 담아 보냄.
			request.setAttribute("elist", list); //리스트 실어 보내기, request.setAttribute(부를 이름, 보낼 데이터), 꺼내쓸 때는 getAttribute
			RequestDispatcher rd=request.getRequestDispatcher("list.jsp"); //포워드 작업
			rd.forward(request,response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
