package com.tadigital.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tadigital.authentication.Identity;

@WebServlet("/GetAccessTokenController")
public class GetAccessTokenController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetAccessTokenController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String access_token = new Identity().getToken();
		System.out.println(access_token);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
