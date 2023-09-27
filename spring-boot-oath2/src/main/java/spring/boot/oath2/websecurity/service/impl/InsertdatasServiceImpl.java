package spring.boot.oath2.websecurity.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class InsertdatasServiceImpl {

	public String insertData() {
		String filePath="";
		try {
			Connection conn=DriverManager.getConnection("","","");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "insertDatas";
	}
	
	
}
