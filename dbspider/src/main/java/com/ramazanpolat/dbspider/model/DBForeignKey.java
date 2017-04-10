package com.ramazanpolat.dbspider.model;

import java.util.ArrayList;
import java.util.List;

import com.ramazanpolat.dbspider.DBSpider;

public class DBForeignKey {
	private String name;
	private String schemaName;
	private String tableName;
	private DBSpider dbSpider;
	private List<DBColumn> columns = new ArrayList<DBColumn>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public DBSpider getDbSpider() {
		return dbSpider;
	}
	public void setDbSpider(DBSpider dbSpider) {
		this.dbSpider = dbSpider;
	}
	public List<DBColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DBColumn> columns) {
		this.columns = columns;
	}

}
