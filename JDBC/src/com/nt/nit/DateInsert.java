package com.openfx.share;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DateInsert {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Scanner sc = null;
		int sno = 0;
		String sname = null, dob = null, doj = null,dom=null;

		SimpleDateFormat sdf = null;
		java.util.Date udob = null;
		java.sql.Date sqdob = null, sqdoj = null,sqdom=null;
		long ms = 0;
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			sc = new Scanner(System.in);
			if (sc != null) {
				System.out.println("Enter no");
				sno = sc.nextInt();
				System.out.println("Enter name");
				sname = sc.next();
				System.out.println("Enter DOB(dd-MM-YYYY)");
				dob = sc.next();
				System.out.println("Enter DOJ(YYYY-MM-dd)");
				doj = sc.next();
				System.out.println("Enter DOM(YYYY-MM-dd)");
				dom = sc.next();
			} //
				// convert String date values to java.sql.Date class object
				// For DOB
			sdf = new SimpleDateFormat("dd-MM-yyyy");
			if (sdf != null)
				udob = sdf.parse(dob);// gives java.util.Date object
			if (udob != null)
				ms = udob.getTime();
			sqdob = new java.sql.Date(ms);// gives java.sql.Date class object
			// FOR DOJ
			sqdoj = java.sql.Date.valueOf(doj);
			//FOR DOM
			sqdom = java.sql.Date.valueOf(doj);
			// register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "google");
			if (con != null)
				ps = con.prepareStatement("insert into person_tab values(?,?,?,?,?)");
			if (ps != null) {
				ps.setInt(1, sno);
				ps.setString(2, sname);
				ps.setDate(3, sqdob);
				ps.setDate(4, sqdoj);
				ps.setDate(5,sqdom);

			} // execute query
			if (ps != null)
				result = ps.executeUpdate();
			// process the result
			if (result == 0)
				System.out.println("record not inserted");
			else
				System.out.println("record inserted");
		} // try
		catch (SQLException se) {
			se.printStackTrace();
			System.out.println("record insertion failed ");
		} catch (ClassNotFoundException cnf) {// to handle new exception
			System.out.println("record insertion failed");
			cnf.printStackTrace();
		} catch (Exception e) {
			System.out.println("record insertion failed");
			e.printStackTrace();
		} finally {
			// close JDBC object
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (sc != null)
					sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // finally
	}// main
}// class
