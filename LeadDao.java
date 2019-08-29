package com.tadigital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tadigital.entity.Leads;

public class LeadDao extends Dao {

	public java.util.List<Leads> displayAllData() {

		int status = 0;

		Connection con = openConnection();
		Statement stmt = openStatement(con);
		ResultSet rs = null;
		List<Leads> list = new ArrayList<Leads>();

		try {
			String sql = "select * from lead_information where Email in (\r\n"
					+ "select Email from lead_information \r\n" + "group by Email having count(*) > 1\r\n" + ")";

			rs = stmt.executeQuery(sql);
			/*
			 * if(rs.next()){
			 * 
			 * do{
			 * System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+
			 * ","+rs.getString(4)+","+rs.getString(5)); }while(rs.next()); } else{
			 * System.out.println("Record Not Found..."); }
			 */

			// rs = con.createStatement().executeQuery(sql);

			/*
			 * while (rs.next()) {
			 * 
			 * lead.setID(rs.getString(1)); lead.setFirstName(rs.getString(2));
			 * lead.setLastName(rs.getString(3)); lead.setCountry(rs.getString(4));
			 * lead.setEmail(rs.getString(5)); // System.out.println("\nIn Lead Dao " +
			 * lead.getID() + " " + lead.getFirstName() + " " + lead.getLastName() + " " +
			 * lead.getCountry() + " " + lead.getEmail()); list.add(lead); }
			 * 
			 */
			if (rs.next()) {

				do {
					Leads lead = new Leads();
					lead.setID(rs.getString(1));
					lead.setFirstName(rs.getString(2));
					lead.setLastName(rs.getString(3));
					lead.setCountry(rs.getString(4));
					lead.setEmail(rs.getString(5));
					if (rs.getString(6) == null || rs.getString(6).equals("null")) {
						lead.setSFDC("Value Not Available");
					} else {
						lead.setSFDC(rs.getString(6));
					}
					// System.out.println("In Lead Dao do_while\n" + lead.getID() + " " +
					// lead.getFirstName() + " " + lead.getLastName() + " " + lead.getCountry() + "
					// " + lead.getEmail());
					list.add(lead);
					// System.out.println(list);

				} while (rs.next());
			}
			/*
			 * for (int i = 0 ; i < list.size() ; i++) {
			 * System.out.println(list.get(i).getID() + " " + list.get(i).getFirstName() +
			 * " " + list.get(i).getLastName() + " " + list.get(i).getCountry() + " " +
			 * list.get(i).getEmail() ); }
			 */
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			closeStatement(stmt);
			closeResultSet(rs);
			closeConnection(con);
		}
		return list;

	}

	public boolean deleteRecord(String id) {

		boolean status = false;
		int row = 0;
		int id1 = Integer.parseInt(id);
		Connection con = openConnection();
		Statement stmt = openStatement(con);
		String sql = "DELETE FROM lead_information where `Id` = '" + id1 + "'";
		try {
			row = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (row != 0)
			status = true;
		return status;
	}

}