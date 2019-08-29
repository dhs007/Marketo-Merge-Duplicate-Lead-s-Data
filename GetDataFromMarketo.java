package com.tadigital.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tadigital.getcsv.GetCSV;

@WebServlet("/GetDataFromMarketo")
public class GetDataFromMarketo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetDataFromMarketo() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* GetCSV getcsv = new GetCSV(); */
		HttpSession session = request.getSession();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		System.out.println(startDate + " " + endDate);

		GetCSV getcsv = new GetCSV();
		String export_id = getcsv.CreateJob(startDate, endDate);
		System.out.println(export_id);
		session.setAttribute("job_status", "Created");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Sleep for 1 seconds before enqueueing the job

		String enqueue_status = getcsv.StartingJob(export_id);
		System.out.println(enqueue_status);
		session.setAttribute("job_status", "Job Queued");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Sleep for 10 seconds before polling the job status after queueing
		String job_status, withoutQuotes_line1;
		do {
			job_status = getcsv.PollingJobStatus(export_id);
			withoutQuotes_line1 = job_status.replace("\"", "");
			System.out.println(withoutQuotes_line1);
			try {
				TimeUnit.SECONDS.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!(withoutQuotes_line1.equals("Completed"))); // Wait for 20 seconds and check the status of the queued
																// job
		session.setAttribute("job_status", "Completed"); // Repeat (Keep checking the status of the job) this
		// until the job status changes to Completed.
		// Once the job status is changed to completed issue the
		// GET request to get the CSV file in the form of
		// response
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Sleep for 10 seconds before making a GET request for the csv
		String csv_response = getcsv.retrieveData(export_id);
		boolean write_status = getcsv.writeToFile(csv_response);
		if (write_status == true) {
			System.out.println("File Written Succesfully");
		} else {
			System.out.println("File not written");
		}

		try {
			getcsv.writeToSQL();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * try { getcsv.Util(); session.setAttribute("Status", "Done"); } catch
		 * (InterruptedException e) { e.printStackTrace();
		 * session.setAttribute("Status", "Error in operation"); } catch (SQLException
		 * e) { e.printStackTrace(); session.setAttribute("Status",
		 * "Error in operation");
		 * 
		 * }
		 */

		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
