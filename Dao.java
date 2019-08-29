package com.tadigital.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {
	protected Connection openConnection() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return con;
	}

	protected Statement openStatement(Connection con) {
		Statement stmt = null;

		try {
			stmt = con.createStatement();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return stmt;
	}

	protected void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	protected void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	protected void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
