package com.tadigital.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tadigital.dao.*;

/**
 * Servlet implementation class CleanUpController
 */
@WebServlet("/CleanUpController")
public class CleanUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CleanUpController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CleanUpDao cld = new CleanUpDao();
		HttpSession session = request.getSession();
		String status = null;
		status = cld.deleteFileAndTable();
		session.setAttribute("EXITSTATUS", status);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
