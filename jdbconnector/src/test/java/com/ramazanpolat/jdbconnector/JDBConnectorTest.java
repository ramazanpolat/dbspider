package com.ramazanpolat.jdbconnector;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;


public class JDBConnectorTest {
	
	@Ignore
	@Test
	public void ConstructorTest1() throws SQLException{
		JDBConnector dbcon = new JDBConnector(DBType.DB2, "192.168.101.130", "50000", "LOCALDB2", "Administrator", "qwe123");
		System.out.println("JDBConnector created.");
		Connection con = dbcon.getConnection();
		assertEquals(false, con.isClosed());
		System.out.println("connection is open.");
		con.close();
		assertEquals(true, con.isClosed());
		System.out.println("connection is closed.");
	}
	
	@Ignore
	@Test
	public void ConstructorTest2() throws SQLException{
		JDBConnector dbcon = new JDBConnector("com.ibm.db2.jcc.DB2Driver", "jdbc:db2://192.168.101.130:50000/LOCALDB2", "Administrator", "qwe123");
		System.out.println("JDBConnector created.");
		Connection con = dbcon.getConnection();
		assertEquals(false, con.isClosed());
		System.out.println("connection is open.");
		con.close();
		assertEquals(true, con.isClosed());
		System.out.println("connection is closed.");
	}
	
	@Ignore
	@Test
	public void ConstructorTest3() throws SQLException{
		JDBConnector dbcon = new JDBConnector("com.ibm.db2.jcc.DB2Driver", "jdbc:db2://192.168.101.130:50000/LOCALDB2:user=Administrator;password=qwe123;");
		System.out.println("JDBConnector created.");
		Connection con = dbcon.getConnection();
		assertEquals(false, con.isClosed());
		System.out.println("connection is open.");
		con.close();
		assertEquals(true, con.isClosed());
		System.out.println("connection is closed.");
	}
	
	@Ignore
	@Test
	public void sybaseTest1() throws Exception{
		//JDBConnector dbcon = new JDBConnector(DBType.Sybase, "192.168.101.139","5000","pubs3","sa","qwe123");
		
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:jtds:sybase://192.168.101.139:5000/pubs3", "sa", "qwe123");
		System.out.println("Sybase connection created.");
		
		assertEquals(false, con.isClosed());
		System.out.println("connection is open.");
		
		PreparedStatement ps = con.prepareStatement("Select * from dbo.authors");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
		
		con.close();
		assertEquals(true, con.isClosed());
		System.out.println("connection is closed.");
	}
	@Ignore
	@Test
	public void sybaseTest2() throws Exception{
		JDBConnector dbcon = new JDBConnector(DBType.Sybase, "192.168.101.139","5000","pubs3","sa","qwe123");
		
		Connection con = dbcon.getConnection();
		System.out.println("Sybase connection created.");
		
		assertEquals(false, con.isClosed());
		System.out.println("connection is open.");
		
		PreparedStatement ps = con.prepareStatement("Select * from dbo.authors");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
		
		con.close();
		assertEquals(true, con.isClosed());
		System.out.println("connection is closed.");
	}
	
	@Test
	public void sybaseTest3() throws Exception{
		JDBConnector dbcon = new JDBConnector("net.sourceforge.jtds.jdbc.Driver","jdbc:jtds:sybase://192.168.101.139:5000/pubs3","sa","qwe123");
		
		Connection con = dbcon.getConnection();
		System.out.println("Sybase connection created.");
		
		assertEquals(false, con.isClosed());
		System.out.println("connection is open.");
		
		PreparedStatement ps = con.prepareStatement("Select * from dbo.authors");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
		
		con.close();
		assertEquals(true, con.isClosed());
		System.out.println("connection is closed.");
	}
	
}
