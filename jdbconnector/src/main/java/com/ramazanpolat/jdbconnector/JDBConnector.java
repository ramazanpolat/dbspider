package com.ramazanpolat.jdbconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;


public class JDBConnector {
	private enum InstanceType {
		KNOWN, // It's a known database. In this case DBType is provided in constructor.
		CUSTOM,// It's a custom database. DBType is not provided in constructor.
		CUSTOM_WITH_USERPASS // Just like CUSTOM, but also username and password is included in constructor.
	}
	private InstanceType instanceType;
	private String desc;
	private String username;
	private String password;
	private String driver;
	private String dbUrl;
	private DBType dbType;
	private List<String> systemSchemas;
	private List<String> systemTables;
	private String serviceName;
	
	
	public List<String> getSystemTables(){
		return systemTables;
	}
	
	public void setSystemTables(List<String> tables) {
		this.systemTables = tables;
	}
	
	public List<String> getSystemSchemas(){
		return systemSchemas;
	}
	
	public void setSystemSchemas(List<String> schemas) {
		this.systemSchemas = schemas;
	}

	public Connection getConnection(){
		Connection conn = null;
				
		try {
			switch (this.getInstanceType()) {
			case KNOWN:
				Class.forName(getDriver());
				conn = DriverManager.getConnection(getURL(), getUsername(), getPassword());
				break;
			case CUSTOM_WITH_USERPASS:
				Class.forName(getDriver());
				conn = DriverManager.getConnection(getURL(), getUsername(), getPassword());
				break;
			case CUSTOM:
				Class.forName(getDriver());
				conn = DriverManager.getConnection(getURL());
				break;
			default:
				break;
			}
		}
		catch (Exception e) {
			System.err.println("error:getConnection:"+e.getMessage());
			e.printStackTrace();
		}
		
		return conn;
	}
	
	private void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getDriver(){
		return this.driver;
	}
	
	public String dbDriverLookup(DBType dbType){
		String driver = null;
		switch (dbType) {
		case DB2:
			driver = "com.ibm.db2.jcc.DB2Driver";
			break;
		case Sybase:
			driver = "net.sourceforge.jtds.jdbc.Driver";
			break;
		case MSSQL:
			
			break;
		case Oracle:
			driver = "oracle.jdbc.driver.OracleDriver";
			break;
		case MySQL:
			driver = "com.mysql.jdbc.Driver";
			break;
		case PostgreSQL:
			
			break;
		default:
			break;
		}
		return driver;
		
	}
	
	public String getURL(){
		return this.dbUrl;
	}
	
	public String dbUrlLookup(DBType dbType, String server, String port, String database){
		String connString = null;
		switch (dbType) {
		case DB2:
			connString  = String.format("jdbc:db2://%s:%s/%s", server, port, database);
			break;
		case Sybase:
			connString  = String.format("jdbc:jtds:sybase://%s:%s/%s", server, port, database);
			break;
		case MSSQL:
			
			break;
		case Oracle:
			connString  = String.format("jdbc:mysql://%s:%s/%s:", server, port, database);
			break;
		case MySQL:
			connString  = String.format("jdbc:mysql://%s:%s/%s:", server, port, database);
			break;
		case PostgreSQL:
			
			break;
		default:
			break;
		}
		return connString;
	}
	
	
	public boolean isSystemSchema(String schemaName){
		return getSystemSchemas().contains(schemaName.toUpperCase());
	}
	
	public boolean isSystemTable(String tableName){
		return getSystemTables().contains(tableName.toUpperCase());
	}
	
	public JDBConnector(DBType dbType, String server, String port, String database, String username, String password) {
		setInstanceType(InstanceType.KNOWN);
		setDbType(dbType);
		setSystemSchemas(systemSchemasLookup(dbType));
		setSystemTables(systemTablesLookup(dbType));
		setDriver(dbDriverLookup(dbType));
		setDbUrl(dbUrlLookup(dbType, server, port, database));
		setUsername(username);
		setPassword(password);
	}
	
	// CUSTOM database driver with URL, username and password
	public JDBConnector(String driver, String url, String username, String password) {
		setInstanceType(InstanceType.CUSTOM_WITH_USERPASS);
		setDriver(driver);
		setDbUrl(url);
		setUsername(username);
		setPassword(password);
	}
	// CUSTOM database driver with URL
	public JDBConnector(String driver, String url) {
		setInstanceType(InstanceType.CUSTOM);
		setDriver(driver);
		setDbUrl(url);
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public DBType getDbType() {
		return dbType;
	}
	
	public void setDbType(DBType dbType) {
		this.dbType = dbType;
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	private void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	private InstanceType getInstanceType() {
		return instanceType;
	}
	
	private void setInstanceType(InstanceType instanceType) {
		this.instanceType = instanceType;
	}
	
	public List<String> systemTablesLookup(DBType dbType){
		List<String> tables = null;
		String[] DB2 = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] Sybase = {"SYSALTERNATES",
				"SYSATTRIBUTES",
				"SYSCOLUMNS",
				"SYSCOMMENTS",
				"SYSCONSTRAINTS",
				"SYSDAMS",
				"SYSDEPENDS",
				"SYSENCRYPTKEYS",
				"SYSGAMS",
				"SYSINDEXES",
				"SYSJARS",
				"SYSKEYS",
				"SYSLOGS",
				"SYSOBJECTS",
				"SYSPARTITIONKEYS",
				"SYSPARTITIONS",
				"SYSPROCEDURES",
				"SYSPROTECTS",
				"SYSQUERYPLANS",
				"SYSQUERYMETRICS",
				"SYSREFERENCES",
				"SYSROLES",
				"SYSSEGMENTS",
				"SYSSLICES",
				"SYSSTATISTICS",
				"SYSTABSTATS",
				"SYSTHRESHOLDS",
				"SYSTYPES",
				"SYSUSERMESSAGES",
				"SYSUSERS",
				"SYSXTYPES"};
		
		String[] Oracle = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] MSSQL = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] MySQL = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] PostgreSQL = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		switch (dbType) {
		case DB2:
			tables = Arrays.asList(DB2);
			break;
		case Oracle:
			tables = Arrays.asList(Oracle);
			break;
		case Sybase:
			tables = Arrays.asList(Sybase);
			break;
		case MySQL:
			tables = Arrays.asList(MySQL);
			break;
		case MSSQL:
			tables = Arrays.asList(MSSQL);
			break;
		case PostgreSQL:
			tables = Arrays.asList(PostgreSQL);
			break;

		default:
			break;
		}
		
		return tables;
		
	}

	public List<String> systemSchemasLookup(DBType dbType){
		List<String> schemas = null;
		String[] DB2 = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] Sybase = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] Oracle = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] MSSQL = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] MySQL = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		
		String[] PostgreSQL = {
				"SYSPUBLIC", 
				"SYSIBM",
				"SYSTOOLS",
				"SYSCAT",
				"SYSIBM",
				"SYSIBMADM",
				"SYSSTAT", 
				"NULLID",
				"SQLJ",
				"SYSFUN",
				"SYSIBMINTERNAL",
				"SYSIBMTS",
				"SYSPROC"};
		switch (dbType) {
		case DB2:
			schemas = Arrays.asList(DB2);
			break;
		case Oracle:
			schemas = Arrays.asList(Oracle);
			break;
		case Sybase:
			schemas = Arrays.asList(Sybase);
			break;
		case MySQL:
			schemas = Arrays.asList(MySQL);
			break;
		case MSSQL:
			schemas = Arrays.asList(MSSQL);
			break;
		case PostgreSQL:
			schemas = Arrays.asList(PostgreSQL);
			break;

		default:
			break;
		}
		
		return schemas;
	}

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
