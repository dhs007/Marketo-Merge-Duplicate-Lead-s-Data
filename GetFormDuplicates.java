package com.tadigital.servlet;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tadigital.dao.LeadDao;
import com.tadigital.entity.Leads;

@WebServlet("/GetFormDuplicates")
public class GetFormDuplicates extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetFormDuplicates() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LeadDao ld = new LeadDao();
		List<Leads> leads = new ArrayList<Leads>();
		leads = ld.displayAllData();
		/* System.out.println("I am back in servlet"); */
		// List<Leads> styles = (List<Leads>) request.getAttribute("LEADDATA");
		/*
		 * if(leads!=null){ for(Leads lead: leads){ System.out.println("\n" +
		 * lead.getID() + " " + lead.getFirstName() + " " + lead.getLastName() + " " +
		 * lead.getCountry() + " " + lead.getEmail()); } }
		 */
		int j = 1;
		HttpSession session = request.getSession();
		session.setAttribute("LEADDATA", leads);
		session.setAttribute("TestData", j);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
