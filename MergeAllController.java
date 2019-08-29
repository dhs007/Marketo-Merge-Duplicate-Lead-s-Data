package com.tadigital.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tadigital.dao.LeadDao;
import com.tadigital.entity.Leads;

@WebServlet("/MergeAllController")
public class MergeAllController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MergeAllController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String Status = null;
		HttpSession session = request.getSession();
		LeadDao ld = new LeadDao();
		List<Leads> styles = new ArrayList<Leads>();
		styles = ld.displayAllData();
		if (styles == null || styles.size() == 0) {
			session.setAttribute("STATUS", "No Duplicate Records Found!");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			try {
				rd.forward(request, response);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			MergeAll mA = new MergeAll();
			Status = mA.MergeAllM1();
			session.setAttribute("STATUS", Status);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			try {
				rd.forward(request, response);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
