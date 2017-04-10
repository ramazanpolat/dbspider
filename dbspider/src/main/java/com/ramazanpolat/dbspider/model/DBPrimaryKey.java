package com.ramazanpolat.dbspider.model;

import java.util.ArrayList;
import java.util.List;

import com.ramazanpolat.dbspider.DBSpider;

public class DBPrimaryKey {
	private String name;
	private String schemaName;
	private String tableName;
	private DBSpider dbSpider;
	private List<DBColumn> columns = new ArrayList<DBColumn>();
	
	public DBPrimaryKey(DBSpider DbSpider, String SchemaName, String TableName){
		setDbSpider(DbSpider);
		setSchemaName(SchemaName);
		setTableName(TableName);
	}
	
	public void addColumn(String colName){
		columns.add(getDbSpider().getTable(getSchemaName(), getTableName()).getColumn(colName));
	}
	
	public DBColumn getColumn(String colName){
		DBColumn col = null;
		
		for (DBColumn dbColumn : getColumns()) {
			if (dbColumn.getName().equals(colName)){
				col = dbColumn;
				break;
			}
		}
		
		return col;
	}
	
	public List<String> getColumnNames(){
		List<String> columnNames = new ArrayList<String>();
		for(DBColumn column: getColumns()){
			columnNames.add(column.getName());
		}
		return columnNames;
	}

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
