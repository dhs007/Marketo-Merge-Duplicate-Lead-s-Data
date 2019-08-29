package com.tadigital.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import au.com.bytecode.opencsv.CSVReader;
import com.tadigital.dao.*;

public class ImportCsv extends Dao {

	/*
	 * public static void main(String[] args) throws SQLException, IOException { //
	 * TODO Auto-generated method stub ImportCsv im = new ImportCsv(); im.readCsv();
	 * 
	 * }
	 */
	public void readCsv() throws SQLException, IOException {

		Connection con1 = openConnection();
		Statement stmt = openStatement(con1);
		String sql1 = "Create Table lead_information (Id int,FirstName varchar(255),LastName varchar(255),Country varchar(255),Email varchar(255),SFDC varchar(255))";
		stmt.executeUpdate(sql1);
		String sql = " INSERT INTO lead_information (Id,FirstName,LastName,Country,Email,SFDC) VALUES(?,?,?,?,?,?) ";

		try {
			BufferedReader bReader = new BufferedReader(
					new FileReader("C:\\Users\\darshil.shah\\Desktop\\LeadDataJava.txt"));
			String line = "";
			System.out.println(bReader.readLine());
			while ((line = bReader.readLine()) != null) {
				try {

					if (line != null)

					{
						Connection con = openConnection();
						PreparedStatement ps = con.prepareStatement(sql);
						String[] array = line.split(",+");
						for (String result : array) {
							System.out.println(result);
							// Create preparedStatement here and set them and excute them

							ps.setString(1, array[0]);
							ps.setString(2, array[1]);
							ps.setString(3, array[2]);
							ps.setString(4, array[3]);
							ps.setString(5, array[4]);
							ps.setString(6, array[5]);

							// Assuming that your line from file after split will folllow that sequence

						}
						ps.executeUpdate();
						ps.close();
					}
				} finally {
					if (bReader == null) {
						bReader.close();
					}
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

	}
}
