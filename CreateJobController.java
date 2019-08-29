package com.tadigital.servlet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tadigital.getcsv.GetCSV;

@WebServlet("/CreateJobController")
public class CreateJobController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateJobController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GetCSV getcsv = new GetCSV();
		String export_id = getcsv.CreateJob("", "");
		System.out.println(export_id);
		HttpSession session = request.getSession();
		session.setAttribute("job_status", "Created");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Sleep for 1 seconds before enqueueing the job
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
