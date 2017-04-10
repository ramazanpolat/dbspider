package com.ramazanpolat.dbspider;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import com.ramazanpolat.dbspider.model.DBColumn;
import com.ramazanpolat.dbspider.model.DBSchema;
import com.ramazanpolat.dbspider.model.DBTable;
import com.ramazanpolat.jdbconnector.JDBConnector;
import com.ramazanpolat.jdbconnector.DBType;
public class SybaseSpiderTest {
	private static JDBConnector dbServer;
	private static DBSpider dbs;
	static {
		dbServer = new JDBConnector(DBType.Sybase, "192.168.101.139", "5000", "pubs3", "sa", "qwe123");
		dbs = new DBSpider(dbServer);
	}
	
	//@Ignore
	@Test
	public void getSchemasNamesTest1(){
		System.out.println("* testing getSchemasNamesTest1 ");
		
		for (String str	: dbs.getSchemaNames()) {
			System.out.println(str);			
		}
		System.out.println("* finished");
		assertEquals(1,1);
	}
	
	@Ignore
	@Test
	public void getSchemaListTest1(){
		System.out.println("* testing getSchemaListTest1 ");
		
		for (DBSchema sc: dbs.getSchemaList()) {
			System.out.println(sc.getName());			
		}
		System.out.println("* finished");
		assertEquals(1,1);
	}
	
		
	@Ignore
	@Test
	public void getSchemaTest1(){
		System.out.println("* test getSchemaTest1()");
		
		DBSchema schema =  dbs.getSchema("dbo");
		System.out.println(schema.getName());
		
		System.out.println("* finished");
		assertEquals(1,1);
	}
	
	@Ignore
	@Test
	public void getTableListTest1(){
		System.out.println("* test getTableListTest1");
		
		for(DBTable table: dbs.getTableList("dbo")){
			System.out.println(table.getFullName());
		}
		
		System.out.println("* finished");
		assertEquals(1,1);
	}
	
	@Ignore
	@Test
	public void getSchema_getTableNamesTest1(){
		System.out.println("* test getSchema_getTableTest1()");
		
		DBSchema schema =  dbs.getSchema("dbo");
		for(String table :  schema.getTableNames()){
			System.out.println(schema.getName() +"."+ table);			
		}
		System.out.println("* finished");
		assertEquals(1,1);
	}
	
	@Ignore
	@Test
	public void getColumnListTest1(){
		System.out.println("* test getColumnList(LM,APP_USER)");
		//dbs.getColumnList("LM", "APP_USER")
		for (DBColumn column: dbs.getColumnList("dbo","NewTable")) {
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
		System.out.println("* finished");
		assertEquals(1,1);
	}
	
	@Ignore
	@Test
	public void getColumnTest1(){
		System.out.println("* test getColumnList(LM,APP_USER,USERNAME)");
		
		DBColumn column = dbs.getColumn("LM", "APP_USER", "USERNAME");
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
		assertEquals(1,1);
	}
	@Ignore
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
	
	

}
