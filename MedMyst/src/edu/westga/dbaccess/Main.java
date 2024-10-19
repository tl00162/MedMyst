package edu.westga.dbaccess;

import java.sql.SQLException;
import java.util.List;

import edu.westga.dbaccess.dal.LoginDAL;
import edu.westga.dbaccess.model.Login;

public class Main {

	public static void main(String[] args) {
		
		LoginDAL dal = new LoginDAL();
		try {
			//printEmployees(dal.getEmployeesByDepartment(5));
			//printEmployees(dal.getEmployeesByGenderSalary('M', 38000));
			printEmployees(dal.getEmployeesByGenderSalary());
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	private static void printEmployees(List<Login> logins) {
		for(Login l: logins) {
			System.out.println(l.getUsername() + "\t\t" + l.getPassword());
		}
		
	}

}
