package oauth2AuthorizeServer.service.impl;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
