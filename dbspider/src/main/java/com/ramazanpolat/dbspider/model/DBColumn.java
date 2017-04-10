package com.ramazanpolat.dbspider.model;

import com.ramazanpolat.dbspider.DBSpider;

public class DBColumn {
	private String name;
	private String schemaName;
	private String tableName;
	private String dataType;
	private String typeName;
	private String colSize;
	private String bufLen;
	private String decDigits;
	private String nullable;
	private String isNullable;
	private String defValue;
	private String isAutoInc;
	private DBSpider dbSpider;
	private DBColumn foreignKey;
	
	public DBColumn(DBSpider dbSpider, String schemaName, String tableName, String columnName) {
		setDbSpider(dbSpider);
		setSchemaName(schemaName);
		setTableName(tableName);
		setName(columnName);
	}
	
	public boolean isPrimaryKey(){
		return getDbSpider().getPrimaryKeyColumnNames(getSchemaName(), getTableName()).contains(getName());
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getColSize() {
		return colSize;
	}

	public void setColSize(String colSize) {
		this.colSize = colSize;
	}

	public String getBufLen() {
		return bufLen;
	}

	public void setBufLen(String bufLen) {
		this.bufLen = bufLen;
	}

	public String getDecDigits() {
		return decDigits;
	}

	public void setDecDigits(String decDigits) {
		this.decDigits = decDigits;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getIsAutoInc() {
		return isAutoInc;
	}

	public void setIsAutoInc(String isAutoInc) {
		this.isAutoInc = isAutoInc;
	}

	public DBSpider getDbSpider() {
		return dbSpider;
	}

	public void setDbSpider(DBSpider dbSpider) {
		this.dbSpider = dbSpider;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	/**
	 * @return the foreignKey
	 */
	public DBColumn getForeignKey() {
		return foreignKey;
	}

	/**
	 * @param foreignKey the foreignKey to set
	 */
	public void setForeignKey(DBColumn foreignKey) {
		this.foreignKey = foreignKey;
	}
}
