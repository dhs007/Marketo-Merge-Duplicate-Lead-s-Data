package com.tadigital.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.tadigital.dao.LeadDao;
import com.tadigital.entity.Leads;
import com.tadigital.leadActions.MergeLeads;

public class MergeAll {

	/*
	 * public static void main(String[] args) { MergeAll mA = new MergeAll();
	 * mA.MergeAllM1();
	 * 
	 * }
	 */

	public String MergeAllM1() {
		String status = null;
		int key = 0;
		LeadDao ld = new LeadDao();
		List<Leads> leads = new ArrayList<Leads>();
		// HttpSession session = request.getSession();
		boolean dbUpdateStatus = false;
		leads = ld.displayAllData();
		MergeLeads mergeLeads = new MergeLeads();
		String winningEmail = null, result = " "/* , delRecordId = null */;
		int sfdc_flag;
		ArrayList lista = new ArrayList();
		ArrayList listb = new ArrayList();
		ArrayList listc = new ArrayList();
		ArrayList listd = new ArrayList();
		int list_size = leads.size(), flag = 0;
		/*
		 * for(int i = 0 ; i < list_size ; i++) {
		 * list2.add(Integer.parseInt(leads.get(i).getID())); list.add(
		 * leads.get(i).getEmail() ); list1.add(leads.get(i).getID()); flag = flag + 1;
		 * 
		 * }
		 */
		flag = 0;
		int i = 0;
		for (; i < leads.size() - 1;) {
			i = 0;
			flag = 0;
			System.out.println("Outside Loop Leads.size() " + leads.size());
			mergeLeads.winningId = Integer.parseInt(leads.get(i).getID());
			flag++;
			lista.removeAll(lista);
			listb.removeAll(listb);
			listc.removeAll(listc);
			for (int j = i + 1; j < leads.size(); j++) {
				if (leads.get(i).getEmail().equals(leads.get(j).getEmail())) {
					listc.add(Integer.parseInt(leads.get(j).getID()));
					lista.add(leads.get(j).getEmail());
					listb.add(leads.get(j).getID());
					listd.add(leads.get(j).getSFDC());
					flag = flag + 1;
					System.out.println("Inside");
				}
			}
			String[] leadEmail = new String[lista.size()];
			for (int j = 0; j < leadEmail.length; j++)
				leadEmail[j] = lista.get(j).toString();

			String[] delRecordId = new String[listb.size()];
			for (int j = 0; j < delRecordId.length; j++)
				delRecordId[j] = listb.get(j).toString();

			int[] LeadID = new int[listc.size()];
			for (int j = 0; j < LeadID.length; j++) {
				LeadID[j] = (int) listc.get(j);
			}
			String[] sfdcType = new String[listd.size()];
			for (int j = 0; j < sfdcType.length; j++) {
				sfdcType[j] = listd.get(j).toString();
			}

			sfdc_flag = 1;

			for (int a = 0; a < sfdcType.length - 1; a++) {
				for (int b = a + 1; b < sfdcType.length; b++) {
					if (a != b) {
						if ((sfdcType[a].equals("Lead") && sfdcType[b].equals("Customer"))
								|| (sfdcType[a].equals("Customer") && sfdcType[b].equals("Lead"))) {
							sfdc_flag = 0;
							String s = "SFDC Type Mismatch Error!";
							System.out.println("Merge All" + s);
							break;
						}
					}
				}
			}
			if (sfdc_flag == 1) {
				mergeLeads.leadIds = LeadID;
				System.out.println(
						"LeadID Length" + LeadID.length + "MergeLeads.LEADIDS LENGTH" + mergeLeads.leadIds.length);
				result = mergeLeads.postData();
				System.out.println(result);
				for (int k = 0; k < flag - 1; k++) {
					dbUpdateStatus = ld.deleteRecord(delRecordId[k]);
					System.out.println("Records to be deleted from the database" + delRecordId[k]);
					System.out.println(dbUpdateStatus);
				}

				leads = ld.displayAllData();
				System.out.println("Updated Leads.size() " + leads.size());
				flag = 0;
				key = 1;
			} else {
				flag = 0;
				continue;
			}
		}
		if (key == 1) {
			status = "Successfully Merged";
		} else if (key == 0)
			status = "Error Merging the Records";
		return status;
	}

}