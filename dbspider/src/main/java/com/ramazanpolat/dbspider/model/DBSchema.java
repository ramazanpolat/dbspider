package com.ramazanpolat.dbspider.model;

import java.util.List;

import com.ramazanpolat.dbspider.DBSpider;

public class DBSchema {
	private String name;
	private DBSpider dbSpider;
	
	public DBTable getTable(String tableName){
		//tableName = tableName.toUpperCase();
		return getDbSpider().getTable(this.getName(), tableName);
	}
	
	public List<String> getTableNames() {
		return getTableNames(false);
	}
	
	public List<String> getTableNames(boolean showSystemTables) {
		return getDbSpider().getTableNames(getName(), showSystemTables);
	}
	
	public DBSchema(DBSpider dbSpider, String schemaName) {
		//schemaName = schemaName.toUpperCase();
		setDbSpider(dbSpider);
		setName(schemaName);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public DBSpider getDbSpider() {
		return dbSpider;
	}
	public void setDbSpider(DBSpider dbSpider) {
		this.dbSpider = dbSpider;
	}
	
}
