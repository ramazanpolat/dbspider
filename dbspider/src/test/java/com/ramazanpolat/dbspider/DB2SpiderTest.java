package com.ramazanpolat.dbspider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import org.junit.Test;
import com.ramazanpolat.dbspider.model.DBColumn;
import com.ramazanpolat.dbspider.model.DBPrimaryKey;
import com.ramazanpolat.dbspider.model.DBSchema;
import com.ramazanpolat.jdbconnector.JDBConnector;
import com.ramazanpolat.jdbconnector.DBType;

@SuppressWarnings("unused")
public class DB2SpiderTest {
	private static JDBConnector dbServer;
	private static DBSpider dbs;
	static {
				
		dbServer = new JDBConnector(DBType.DB2, "192.168.101.130", "50000", "LOCALDB2", "Administrator", "qwe123");

		dbs = new DBSpider(dbServer);
		assertEquals(1,1);
	}
	//@Ignore
	@Test
	public void getSchemasAsStringListTest1(){
		System.out.println("* testing getSchemasAsStringList()");
		boolean hasResults = false;
		for (String str	: dbs.getSchemaNames()) {
			System.out.println(str);			
			hasResults = true;
		}
		System.out.println("* finished");
		assertEquals(true, hasResults);
		
	}
	//@Ignore
	@Test
	public void getSchemasAsStringListTest2(){
		System.out.println("* testing getSchemasAsStringList(true)");
		boolean hasResults = false;
		for (String str	: dbs.getSchemaNames(true)) {
			System.out.println(str);		
			hasResults = true;
		}
		System.out.println("* finished");
		assertEquals(true, hasResults);
	}
	//@Ignore
	@Test
	public void getSchemaTest1(){
		System.out.println("* testing getSchema(tableName).getTablesAsStringList()");
		boolean hasResults = false;
		
		String tableName = "LM";
		
		for (String str	: dbs.getSchema(tableName).getTableNames()) {
			System.out.println(tableName + "." + str);		
			hasResults = true;
		}
		System.out.println("* finished");
		assertEquals(true, hasResults);
	}
	//@Ignore
	@Test
	public void getSchemaTest2(){
		System.out.println("* testing getSchemaList(false)");
		boolean hasResults = false;
		
		for (DBSchema schema: dbs.getSchemaList(false)) {
			System.out.println(schema.getName());
			hasResults = true;
		}
		System.out.println("* finished");
		assertEquals(true, hasResults);
	}
	
	//@Ignore
	@Test
	public void getTablesAsStringListTest1(){
		System.out.println("* test getTablesAsStringList(null)");
		boolean hasResults = false;
		for (String str	: dbs.getTableNames(null)) {
			System.out.println(str);	
			hasResults = true;
		}
		System.out.println("* finished");
		assertEquals(true, hasResults);
	}
	
	//@Ignore
	@Test
	public void getColumnListTest1(){
		System.out.println("* test getColumnList(LM,APP_USER)");
		//dbs.getColumnList("LM", "APP_USER")
		boolean hasResults = false;
		
		for (DBColumn column: dbs.getColumnList("LM","APP_USER")) {
			String info = String.format("colName:%s\n dataType:%s\n typeName:%s\n colSize:%s\n bufLen:%s\n decDigits:%s\n nullable:%s\n isNullable:%s\n defValue:%s\n isAutoInc:%s\n",
					column.getName(),
					column.getDataType(),
					column.getTypeName(),
					column.getColSize(),
					column.getBufLen(),
					column.getDecDigits(),
					column.getNullable(),
					column.getIsNullable(),
					column.getDefValue(),
					column.getIsAutoInc()
					);
			hasResults = true;
			System.out.println(info);			
		}
		System.out.println("* finished");
		assertEquals(true, hasResults);
	}
	//@Ignore
	@Test
	public void getColumnTest1(){
		System.out.println("* test getColumnList(LM,APP_USER,USERNAME)");
		
		DBColumn column = dbs.getColumn("LM", "APP_USER", "USERNAME");
		if (column != null) {
			String info = String.format("colName:%s\n dataType:%s\n typeName:%s\n colSize:%s\n bufLen:%s\n decDigits:%s\n nullable:%s\n isNullable:%s\n defValue:%s\n isAutoInc:%s\n",
					column.getName(),
					column.getDataType(),
					column.getTypeName(),
					column.getColSize(),
					column.getBufLen(),
					column.getDecDigits(),
					column.getNullable(),
					column.getIsNullable(),
					column.getDefValue(),
					column.getIsAutoInc()
					);
			System.out.println(info);
		}
		assertNotNull(column);
	}
	
	//@Ignore
	@Test
	public void getColumnTest2(){
		System.out.println("* test getColumnList(LM,APP_USER,USERNAME)");
		
		DBColumn column = dbs.getSchema("LM").getTable("APP_USER").getColumn("USERNAME");
		
		//Column column = dbs.getColumn("LM", "APP_USER", "USERNAME");
		System.out.println("LM.APP_USER.USERNAME info:");
		String info = String.format("colName:%s\n dataType:%s\n typeName:%s\n colSize:%s\n bufLen:%s\n decDigits:%s\n nullable:%s\n isNullable:%s\n defValue:%s\n isAutoInc:%s\n",
				column.getName(),
				column.getDataType(),
				column.getTypeName(),
				column.getColSize(),
				column.getBufLen(),
				column.getDecDigits(),
				column.getNullable(),
				column.getIsNullable(),
				column.getDefValue(),
				column.getIsAutoInc()
				);
		System.out.println(info);	
		
		System.out.println("LM.APP_USER info:");
		for (String str : dbs.getSchema("LM").getTable("APP_USER").getColumnNames()) {
			System.out.println(str);
		}
		
		assertEquals(1,1);
	}
	
	//@Ignore
	@Test
	public void getPrimaryKeys1(){
		System.out.println("* test getPrimaryKeys(LM,APP_USER)");
		
		DBPrimaryKey priKey = dbs.getPrimaryKey("LM", "APP_USER");
		
		DBColumn column = priKey.getColumns().get(0);
		
		String info = String.format("colName:%s\n dataType:%s\n typeName:%s\n colSize:%s\n bufLen:%s\n decDigits:%s\n nullable:%s\n isNullable:%s\n defValue:%s\n isAutoInc:%s\n",
				column.getName(),
				column.getDataType(),
				column.getTypeName(),
				column.getColSize(),
				column.getBufLen(),
				column.getDecDigits(),
				column.getNullable(),
				column.getIsNullable(),
				column.getDefValue(),
				column.getIsAutoInc()
				);
		System.out.println(info);	
		
		System.out.println("LM.APP_USER has primary key ? = "+dbs.getSchema("LM").getTable("APP_USER").hasPrimaryKey());
		System.out.println("LM.LIST_ITEM has primary key ? = "+dbs.getSchema("LM").getTable("LIST_ITEM").hasPrimaryKey());
		
		
		System.out.println("* finished getPrimaryKeys");
		assertEquals(1,1);
	}
	
	//@Ignore
	@Test
	public void getForeignKeys1(){
		System.out.println("* test getForeignKeyColumnNames(LM,CHILD)");
		
		for (String str : dbs.getForeignKeyColumnNames("LM", "CHILD2")) {
			System.out.println("fk column:"+str);
		}
				
		System.out.println("* finished getPrimaryKeys");
		assertEquals(1,1);
	}
	
	

}
