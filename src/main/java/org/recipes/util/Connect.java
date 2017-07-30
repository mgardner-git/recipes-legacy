package org.recipes.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class Connect {

	
	public static void main(String[] args) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://138.68.246.193:3306/recipes","recipes","r3cip3s");
		
		
	}
}
   