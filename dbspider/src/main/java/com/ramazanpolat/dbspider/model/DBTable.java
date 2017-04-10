package com.ramazanpolat.dbspider.model;

import java.util.List;

import com.ramazanpolat.dbspider.DBSpider;

public class DBTable {
	private String name;
	private String schemaName;
	private DBSpider dbSpider;
	
	public DBTable(DBSpider dbSpider, String schemaName, String tableName) {
//		schemaName = schemaName.toUpperCase();
//		tableName = tableName.toUpperCase();
		setDbSpider(dbSpider);
		setSchema(schemaName);
		setName(tableName);
	}
	
	public DBColumn getColumn(String columnName){
		//columnName = columnName.toUpperCase();
		return getDbSpider().getColumn(getSchema(), getName(), columnName);		
	}
	
	public boolean hasPrimaryKey(){
		return (getDbSpider().getPrimaryKey(getSchema(), getName()).getColumns().size() > 0);
	}
	
	public DBPrimaryKey getPrimaryKey(){
		return getDbSpider().getPrimaryKey(getSchema(), getName());
	}
	
	public List<DBColumn> getColumnList(){
		return this.getDbSpider().getColumnList(getSchema(), getName());
	}
	
	public List<String> getColumnNames(){
		return this.getDbSpider().getColumnNames(getSchema(), getName());
	}
	
	public String getFullName() {
		return getSchema() +"."+getName();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchema() {
		return schemaName;
	}

	public void setSchema(String schemaName) {
		//schemaName = schemaName.toUpperCase();
		this.schemaName = schemaName;
	}

	public DBSpider getDbSpider() {
		return dbSpider;
	}

	public void setDbSpider(DBSpider dbSpider) {
		this.dbSpider = dbSpider;
	}

}
