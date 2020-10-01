package com.tadigital.servlet; //Java Version 8, last run on. Proposed changed for the methids in-case the self define methods do not work.

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ExceptionController")
public class ExceptionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExceptionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession sess = request.getSession();
		String exception = (String) sess.getAttribute("EXCEPTION");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	protected void doGetSomePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//In case the auto-generated methods are not called
		doGet(request, response);
	}
	protected void dodoFigureOutPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// For auto-generated methods only
		doGet(request, response);
	}

}
