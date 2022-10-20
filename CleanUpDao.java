package com.tadigital.dao;

import com.tadigital.dao.Dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CleanUpDao extends Dao {
	
	public String deleteFileAndTable ()  {
		
		connection co2 =openConnection();
		connection co3 = NULL;
		close();
		
		Connection con = openConnection();
		Statement stmt = openStatement(con);
		
		String sql = "DROP TABLE lead_information";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		File file = new File("C:\\Users\\darshil.shah\\Desktop\\LeadDataJava.txt");
		boolean result = file.delete();
		if(result == false)
			return "Operation Failed";
		else
			return "CleanUp Done Successfully";
		
	}

}
