package com.fp.dbModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DbManager {

	private Connection con;

	private DbManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found or failed to load. Check your libraries");
		}

		final String DB_IP = "127.0.0.1";
		final String DB_PORT = "3306";
		final String DB_DBNAME = "project";
		final String DB_USER = "root";
		final String DB_PASS = "root";
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER,
					DB_PASS);
		} catch (SQLException e) {
			System.out.println("Ops");
		}

	}

	public Connection getConnection() {
		return con;
	}

	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
