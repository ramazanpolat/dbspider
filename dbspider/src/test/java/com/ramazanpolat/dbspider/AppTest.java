package com.ramazanpolat.dbspider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		Connection conn = null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			System.out.println("DB2 driver is loaded successfully");
			String url = "jdbc:db2://192.168.101.130:50000/LOCALDB2:" +
					  "user=Administrator;password=qwe123;";

			conn = DriverManager.getConnection(url);
			if (conn.isClosed())
				System.out.println("connection is closed");
			else
				System.out.println("connection is OPEN");
				
			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println(dbmd.getDatabaseProductName());
			System.out.println(dbmd.getDatabaseProductVersion()); 
			
			ResultSet rs = dbmd.getTables(null, null, null, null);
			ResultSetMetaData columns = rs.getMetaData();
			System.out.println("== column names ==");
			int i = 0;
	        while (i < columns.getColumnCount()) {
	          i++;
	          System.out.println(columns.getColumnName(i));
	        }
	        System.out.print("\n== table names ==");
			
	        

			while (rs.next()) {
				System.out.println(rs.getString("TABLE_CAT"));
			}
			
			/*
			String url = "jdbc:sqlite:C:/dev/sqlite/dbspider.db";
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println(dbmd.getDatabaseProductName());
			System.out.println(dbmd.getDatabaseProductVersion());

			String schemaTypes[] = { "TABLE" };
			ResultSet rs = dbmd.getSchemas();

			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			*/

		}
		
		catch (ClassNotFoundException e) {
            System.out.println("Please include Classpath  Where your DB2 Driver is located");
            e.printStackTrace();
            return;
        }

		catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

		assertTrue(true);
	}
}
