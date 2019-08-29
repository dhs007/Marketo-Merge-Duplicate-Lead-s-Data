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

import com.eclipsesource.json.JsonObject;
import com.tadigital.dao.LeadDao;
import com.tadigital.entity.Leads;
import com.tadigital.leadActions.MergeLeads;

@WebServlet("/MergeLeadsController")
public class MergeLeadsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MergeLeadsController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LeadDao ld = new LeadDao();
		List<Leads> leads = new ArrayList<Leads>();
		HttpSession session = request.getSession();
		boolean dbUpdateStatus = false;
		leads = ld.displayAllData();
		if (leads == null || leads.size() == 0) {
			session.setAttribute("CONSOLEMESSAGE", "No Duplicate Records Found");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			try {
				rd.forward(request, response);
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			MergeLeads mergeLeads = new MergeLeads();
			String winningEmail = null, result = " "/* , delRecordId = null */;
			// String leadEmail [];
			ArrayList list = new ArrayList();
			ArrayList list1 = new ArrayList();
			ArrayList list2 = new ArrayList();
			ArrayList list3 = new ArrayList();
			int list_size = leads.size(), flag = 0;
			System.out.println("I am List Size " + list_size);
			for (int i = 0; i < list_size; i++) {

				if (request.getParameter(leads.get(i).getID()) != null && flag == 0) {
					mergeLeads.winningId = Integer.parseInt(leads.get(i).getID());
					winningEmail = leads.get(i).getEmail();
					flag = 1;
				}

				else if (request.getParameter(leads.get(i).getID()) != null && flag != 0) {
					// mergeLeads.leadIds = new int[]{Integer.parseInt(leads.get(i).getID())};
					list2.add(Integer.parseInt(leads.get(i).getID()));
					list.add(leads.get(i).getEmail());
					list1.add(leads.get(i).getID());
					list3.add(leads.get(i).getSFDC());
					flag = flag + 1;
				}

				/*
				 * if (flag == 2) { break; }
				 */
			}
			System.out.println("I am Flag: " + flag);

			String[] leadEmail = new String[list.size()];
			for (int j = 0; j < leadEmail.length; j++)
				leadEmail[j] = list.get(j).toString();

			String[] delRecordId = new String[list1.size()];
			for (int j = 0; j < delRecordId.length; j++)
				delRecordId[j] = list1.get(j).toString();

			int[] LeadID = new int[list2.size()];
			for (int j = 0; j < LeadID.length; j++) {
				LeadID[j] = (int) list2.get(j);
			}

			String[] sfdcType = new String[list3.size()];
			for (int j = 0; j < sfdcType.length; j++) {
				sfdcType[j] = list3.get(j).toString();
			}
			int flag_main = 1;
			int flag_sfdc = 1;
			for (int i = 0; i < flag - 1; i++) {

				if (!(winningEmail.equals(leadEmail[i]))) {

					flag_main = 0;
					result = "Mail Ids are not same";
				}

			}
			for (int a = 0; a < sfdcType.length - 1; a++) {
				for (int b = a + 1; b < sfdcType.length; b++) {
					if (a != b) {
						if ((sfdcType[a].equals("Lead") && sfdcType[b].equals("Contact"))
								|| (sfdcType[a].equals("Contact") && sfdcType[b].equals("Lead")) ) {
							flag_sfdc = 0;
							result = "SFDC Type Mismatch Error!";
							break;
						}
					}
				}
			}

			if (flag_main == 1 && flag_sfdc == 1) {

				mergeLeads.leadIds = LeadID;
				System.out.println(
						"LeadID Length" + LeadID.length + "MergeLeads.LEADIDS LENGTH" + mergeLeads.leadIds.length);
				result = mergeLeads.postData();
				for (int i = 0; i < flag - 1; i++) {
					dbUpdateStatus = ld.deleteRecord(delRecordId[i]);
					System.out.println(dbUpdateStatus);

				}

			}
			System.out.println(result);
			if (flag_main == 1) {
				JsonObject JSONObject = JsonObject.readFrom(result.toString());
				String resp = JSONObject.get("success").toString();
				System.out.println(resp);
				if (resp.equals("true")) {
					resp = "Records Merged Successfully";
				} else {
					resp = "Error Merging Records";
				}
				session.setAttribute("CONSOLEMESSAGE", resp);
			} else {
				session.setAttribute("CONSOLEMESSAGE", result);
			}
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
