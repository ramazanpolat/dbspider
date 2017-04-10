package com.ramazanpolat.dbspider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ramazanpolat.dbspider.model.DBColumn;
import com.ramazanpolat.dbspider.model.DBForeignKey;
import com.ramazanpolat.dbspider.model.DBPrimaryKey;
import com.ramazanpolat.dbspider.model.DBSchema;
import com.ramazanpolat.dbspider.model.DBTable;
import com.ramazanpolat.jdbconnector.JDBConnector;
import com.ramazanpolat.jdbconnector.DBType;
public class DBSpider {
	private JDBConnector jdbConnector;
	private DBType dbType;

	public DBSpider(JDBConnector jdbConnector) {
		setJdbConnector(jdbConnector);
	}
	
	// *** GET SCHEMA ***
	public DBSchema getSchema(String schemaName) {
		//schemaName= schemaName.toUpperCase();
		if (getSchemaNames().contains(schemaName)) {
			return new DBSchema(this, schemaName);
		} else
			return null;
	}

	public List<DBSchema> getSchemaList() {
		return getSchemaList(false);
	}

	public List<DBSchema> getSchemaList(boolean showSystemSchemas) {
		List<DBSchema> schemas = null;

		try (Connection conn = getJdbConnector().getConnection()) {
			if (!conn.isClosed()) {
				schemas = new ArrayList<DBSchema>();
				DatabaseMetaData dbmd = conn.getMetaData();
				// System.out.println(dbmd.getDatabaseProductName());
				// System.out.println(dbmd.getDatabaseProductVersion());

				ResultSet rs = dbmd.getSchemas();
				// ResultSetMetaData columns = rs.getMetaData();
				// System.out.println("== column names of get schemas ==");
				// int i = 0;
				// while (i < columns.getColumnCount()) {
				// i++;
				// System.out.println(columns.getColumnName(i));
				// }
				// System.out.print("\n");

				while (rs.next()) {
					String schemaName = rs.getString("TABLE_SCHEM");

					if (showSystemSchemas) {
						schemas.add(new DBSchema(this, schemaName));
					} else if (!getJdbConnector().isSystemSchema(schemaName.toUpperCase())) {
						schemas.add(new DBSchema(this, schemaName));
					}
				}
			}
		} catch (SQLException e) {
			schemas = null;
			System.err.println("error:getTables:" + e.getMessage());
			e.printStackTrace();

		}
		return schemas;
	}

	public List<String> getSchemaNames() {
		return getSchemaNames(false);

	}

	public List<String> getSchemaNames(boolean showSystemSchemas) {
		List<String> schemaList = new ArrayList<String>();
		for (DBSchema sc : getSchemaList(showSystemSchemas)) {
			schemaList.add(sc.getName());
		}
		return schemaList;
	}

	// *** GET TABLES ***
	public DBTable getTable(String schemaName, String tableName) {
//		schemaName = schemaName.toUpperCase();
//		tableName = tableName.toUpperCase();
		if (this.getTableNames(schemaName, true).contains(tableName)) {
			return new DBTable(this, schemaName, tableName);
		} else
			return null;
	}

	public List<DBTable> getTableList(String schemaName, boolean showSystemTables) {
		//if (schemaName != null)  schemaName = schemaName.toUpperCase();
		List<DBTable> tables = null;

		try (Connection conn = getJdbConnector().getConnection()) {
			if (!conn.isClosed()) {
				tables = new ArrayList<DBTable>();
				DatabaseMetaData dbmd = conn.getMetaData();
				// System.out.println(dbmd.getDatabaseProductName());
				// System.out.println(dbmd.getDatabaseProductVersion());

				ResultSet rs = dbmd.getTables(null, null, null, null);
				// ResultSetMetaData columns = rs.getMetaData();
				// System.out.println("== column names ==");
				// int i = 0;
				// while (i < columns.getColumnCount()) {
				// i++;
				// System.out.println(columns.getColumnName(i));
				// }
				// System.out.print("\n");

				while (rs.next()) {
					String tableSchema = rs.getString("TABLE_SCHEM");
					String tableName = rs.getString("TABLE_NAME");

					// if schemaName is null or empty, get all tables.
					if (schemaName == null || schemaName.trim().equals("")) {
						//if showSystemTables is true, include all tables.
						if (showSystemTables) {
							tables.add(new DBTable(this, tableSchema, tableName));
						} 
						// else include only non-system tables
						else if (!getJdbConnector().isSystemTable(tableName.toUpperCase())) {
							tables.add(new DBTable(this, tableSchema, tableName));
						}
					}
					// else get only tables with specified schema
					else if (tableSchema.trim().equals(schemaName.trim())) {
						//if showSystemTables is true, include all tables.
						if (showSystemTables) {
							tables.add(new DBTable(this, tableSchema, tableName));
						} 
						// else include only non-system tables
						else if (!getJdbConnector().isSystemTable(tableName.toUpperCase())) {
							tables.add(new DBTable(this, tableSchema, tableName));
						}
					}

				}

			}
		} catch (SQLException e) {
			tables = null;
			System.err.println("error:getTables:" + e.getMessage());
			e.printStackTrace();

		}
		return tables;
	}

	public List<DBTable> getTableList(String schemaName) {
		return getTableList(schemaName, false);
	}

	public List<String> getTableNames(String schemaName) {
		return getTableNames(schemaName, false);
	}

	public List<String> getTableNames(String schemaName, boolean showSystemTables) {
		List<String> tableList = new ArrayList<String>();
		for (DBTable table : getTableList(schemaName, showSystemTables)) {
			tableList.add(table.getName());
		}
		return tableList;
	}

	// *** GET COLUMN ***

	public List<DBColumn> getColumnList(String schemaName, String tableName) {
//		schemaName = schemaName.toUpperCase();
//		tableName = tableName.toUpperCase();
		
		List<DBColumn> columns = null;

		try (Connection conn = getJdbConnector().getConnection()) {
			if (!conn.isClosed()) {
				columns = new ArrayList<DBColumn>();
				DatabaseMetaData dbmd = conn.getMetaData();
				// System.out.println(dbmd.getDatabaseProductName());
				// System.out.println(dbmd.getDatabaseProductVersion());

				ResultSet rs = dbmd.getColumns(null, schemaName, tableName, null);
//				ResultSetMetaData rscolumn = rs.getMetaData();
//				System.out.println("== column names ==");
//				int i = 0;
//				while (i < rscolumn.getColumnCount()) {
//					i++;
//					System.out.println(rscolumn.getColumnName(i));
//				}
//				System.out.print("\n");

				while (rs.next()) {
					String tabSchema = rs.getString("TABLE_SCHEM");
					String tabName = rs.getString("TABLE_NAME");
					String colName = rs.getString("COLUMN_NAME");
					String dataType = rs.getString("DATA_TYPE");
					String typeName = rs.getString("TYPE_NAME");
					String colSize = rs.getString("COLUMN_SIZE");
					String bufLen = rs.getString("BUFFER_LENGTH");
					String decDigits = rs.getString("DECIMAL_DIGITS");
					String nullable = rs.getString("NULLABLE");
					String isNullable = rs.getString("IS_NULLABLE");
					String defValue = rs.getString("COLUMN_DEF");
					String isAutoInc = rs.getString("IS_AUTOINCREMENT");

					DBColumn col = new DBColumn(this, tabSchema, tabName, colName);
					col.setTableName(tabName);
					col.setSchemaName(tabSchema);
					col.setDataType(dataType);
					col.setTypeName(typeName);
					col.setColSize(colSize);
					col.setBufLen(bufLen);
					col.setDecDigits(decDigits);
					col.setNullable(nullable);
					col.setIsNullable(isNullable);
					col.setDefValue(defValue);
					col.setIsAutoInc(isAutoInc);
					columns.add(col);

				}
			}
		} catch (SQLException e) {
			columns = null;
			System.err.println("error:getTables:" + e.getMessage());
			e.printStackTrace();

		}
		return columns;
	}

	public List<String> getColumnNames(String schemaName, String tableName) {
		List<String> columns = new ArrayList<String>();
		for (DBColumn col : getColumnList(schemaName, tableName)) {
			columns.add(col.getName());
		}
		return columns;
	}

	public DBColumn getColumn(String schemaName, String tableName, String columnName) {
//		schemaName = schemaName.toUpperCase();
//		tableName = tableName.toUpperCase();
//		columnName = columnName.toUpperCase();
		List<DBColumn> allColumns = getColumnList(schemaName, tableName);
		for (DBColumn col : allColumns) {
			if (col.getName().trim().toLowerCase().equals(columnName.trim().toLowerCase())) {
				return col;
			}
		}
		return null;
	}

	// *** GET PRIMARY KEYS ***
	
	public DBPrimaryKey getPrimaryKey(String schemaName, String tableName) {
		
		DBPrimaryKey priKey = null;

		try (Connection conn = getJdbConnector().getConnection()) {
			if (!conn.isClosed()) {
				DatabaseMetaData dbmd = conn.getMetaData();
				// System.out.println(dbmd.getDatabaseProductName());
				// System.out.println(dbmd.getDatabaseProductVersion());

				ResultSet rs = dbmd.getPrimaryKeys(null, schemaName, tableName);
//				ResultSetMetaData columns = rs.getMetaData();
//				System.out.println("== column names of getPrimaryKeys ==");
//				int i = 0;
//				while (i < columns.getColumnCount()) {
//					i++;
//					System.out.println(columns.getColumnName(i));
//				}
//				System.out.print("\n");

				priKey = new DBPrimaryKey(this, schemaName, tableName);
				
				while (rs.next()) {
					String column = rs.getString("COLUMN_NAME");
					priKey.addColumn(column);
				}
			}
		} catch (SQLException e) {
			System.err.println("error:getTables:" + e.getMessage());
			e.printStackTrace();

		}
		return priKey;
	}

	public List<String> getPrimaryKeyColumnNames(String schemaName, String tableName) {
		
		return getPrimaryKey(schemaName, tableName).getColumnNames();
	}
	
	// *** GET FOREIGN KEYS ***
	
	public List<String> getForeignKeyColumnNames(String schemaName, String tableName){
		List<String> colNames = new ArrayList<String>();

		try (Connection conn = getJdbConnector().getConnection()) {
			if (!conn.isClosed()) {
				DatabaseMetaData dbmd = conn.getMetaData();
				// System.out.println(dbmd.getDatabaseProductName());
				// System.out.println(dbmd.getDatabaseProductVersion());

				ResultSet rs = dbmd.getImportedKeys(null, schemaName, tableName);
				ResultSetMetaData columns = rs.getMetaData();
				System.out.println("== column names of getImportedKeys ==");
				int i = 0;
				while (i < columns.getColumnCount()) {
					i++;
					System.out.println(columns.getColumnName(i));
				}
				
				System.out.print(" before while");
				while (rs.next()) {
					System.out.println("inwhile");
					String column = rs.getString("FKCOLUMN_NAME");
					colNames.add(column);
				}
			}
		} catch (SQLException e) {
			System.err.println("error:getTables:" + e.getMessage());
			e.printStackTrace();

		}
		return colNames;
	}
	
	
	

	public DBType getDbType() {
		return dbType;
	}

	public void setDbType(DBType dbType) {
		this.dbType = dbType;
	}

	public JDBConnector getJdbConnector() {
		return jdbConnector;
	}

	/**
	 * @param jdbConnector the jdbConnector to set
	 */
	public void setJdbConnector(JDBConnector jdbConnector) {
		this.jdbConnector = jdbConnector;
	}
}
