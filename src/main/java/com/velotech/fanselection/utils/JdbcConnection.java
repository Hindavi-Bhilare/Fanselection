/**
 * 
 */

package com.velotech.fanselection.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wppl
 *
 */
public class JdbcConnection {

	private static Connection connection = null;

	// private static String host =
	// "jdbc:sqlserver://pc1:1433;databaseName=velotech_v1";
	//
	// private static String uName = "sa";
	//
	// private static String pass = "root";

	public static Connection getConnection() {

		try {

			ResourceUtil resourceUtil = new ResourceUtil();
			String host = resourceUtil.getDatabaseurl();
			String uName = resourceUtil.getDatabaseUsername();
			String pass = resourceUtil.getDatabasePassword();
			if (connection == null) {
				Class.forName(resourceUtil.getDatabaseDriverClassName());
				// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(host, uName, pass);
			}
		} catch (SQLException err) {
			System.out.println(err.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return connection;
	}

	public static void main(String[] args) {

		getConnection();
	}
}
