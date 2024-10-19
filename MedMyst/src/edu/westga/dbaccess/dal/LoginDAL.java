package edu.westga.dbaccess.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.westga.dbaccess.model.*;

/**
 * DAL for accessing employee table
 * @author lyang
 * @verison 1.0
 */
public class LoginDAL {
	
//	public List<Employee> getEmployeesByDepartment(int dno) throws SQLException{
//		
//		List<Employee> list = new ArrayList<Employee>();
//		String query = "select fname, bdate, dno from employee where dno = ?";
//		try ( Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING); 
//				PreparedStatement stmt = connection.prepareStatement(query)){ 
//		    
//			stmt.setInt(1, dno);
//	
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next() ) {
//				String name = rs.getString("fname");
//				Date bdate = rs.getDate("bdate");
//				int dnumber = rs.getInt("dno");
//			  	Employee emoloyee = new Employee(name, bdate, dnumber);
//			  	list.add(emoloyee);
//			}
//
//        } 
//		return list;
//	}	
	
	
	
	public List<Login> getEmployeesByGenderSalary() throws SQLException{
		
		List<Login> list = new ArrayList<Login>();
		String query = "select username, password from login_test";
		try ( Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING); 
				PreparedStatement stmt = connection.prepareStatement(query)){

			ResultSet rs = stmt.executeQuery();
			while (rs.next() ) {
				String username = rs.getString("username");
				String password = rs.getString("password");
			  	Login emoloyee = new Login(username, password);
			  	list.add(emoloyee);
			}

        } 
		return list;
	}
}
