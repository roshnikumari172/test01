package com.nt.nit;

import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ALLStmtsTest extends JFrame implements ActionListener {
	private JLabel lno,lname,lm1,lm2,lm3,lres;
	private JTextField tname,tm1,tm2,tm3,tres;
	private JButton bdetails,bresult;
	private JComboBox tno;
	private Connection con;
	private Statement st;
	private ResultSet rs1,rs2;
	private PreparedStatement ps;
	private CallableStatement cs;
	//constructor
	public ALLStmtsTest(){
		System.out.println("ALLStmtsTest:0-param constructor");
		setTitle("Mini Project");
		setSize(300,300);
		setLayout(new FlowLayout());
		setBackground(Color.gray);
		//add comps
		lno=new JLabel("student Id");
		add(lno);
		tno=new JComboBox();
		add(tno);
		bdetails=new JButton("details");
		bdetails.addActionListener(this);
		add(bdetails);
		lname=new JLabel("Name");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		lm1=new JLabel("Marks1");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		lm2=new JLabel("Marks2");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		lm3=new JLabel("Marks3");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		
		bresult=new JButton("Result");
		bresult.addActionListener(this);
		add(bresult);
		lres=new JLabel("Result");
		add(lres);
		tres=new JTextField(10);
		add(tres);
		//disable editing of comps
		tname.setEditable(false);
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		tres.setEditable(false);
		setVisible(true);
		this.addWindowListener(new MyWindowAdapter(this));
		loaditems();
	}
	//constructor
	/*private void addWindowListener(MyWindowAdapter myWindowAdapter) {
		// TODO Auto-generated method stub
		
	}*/
	private void loaditems(){
		System.out.println("load ltems()");
		try{
			//register driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
					//establish the  connection
					con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","google");
							//create statement object
							if(con!=null)
								st=con.createStatement();
					//execute query
					if(st!=null)
						rs1=st.executeQuery("select sno from ALL_STUDENT");
					if(rs1!=null){
						while(rs1.next()){
							tno.addItem(rs1.getInt(1));
						}//while
					}//if
					//create prepareStatement object
					if(con!=null)
						ps=con.prepareStatement("SELECT * FROM ALL_STUDENT WHERE SNO=?");
					//CREATE CALLABLESTATEMENT OBJ
					if(con!=null){
						cs=con.prepareCall("{call FIND_PASS_FAIL(?,?,?,?)}");
				        cs.registerOutParameter(4,Types.VARCHAR);
	
					}//IF
	
	}//TRY

	  catch(SQLException se){
	se.printStackTrace();
	}
	catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	}//loaditems

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int m1=0,m2=0,m3=0;
		String result=null;
		System.out.println("action perfomed");
		if(e.getSource()==bdetails){
			System.out.println("Details btn is clicked");
			try{
				//get the selected values from combox
				int no=(Integer)tno.getSelectedItem();
				//set value to quesry param
				if(ps!=null){
					ps.setInt(1, no);
					//execute the query
					rs2=ps.executeQuery();
				}
				//set ResultSet object record to textboxes
				if(rs2!=null){
					if(rs2.next()){
						tname.setText(rs2.getString(2));
						tm1.setText(rs2.getString(3));
						tm2.setText(rs2.getString(4));
						tm3.setText(rs2.getString(5));
					}//if
				}//try
			}
				catch(SQLException se){
					se.printStackTrace();
				}//catch
			}//eif
			else{
				System.out.println("result btn is clicked");
				try{
					//read text box values(m1,m2,m3)
					m1=Integer.parseInt(tm1.getText());
					m2=Integer.parseInt(tm2.getText());
					m3=Integer.parseInt(tm3.getText());
					//set values to IN param
                  if(cs!=null){
                	  cs.setInt(1, m1);
                	  cs.setInt(2,m2);
                	  cs.setInt(3, m3);
                	  //execute PL/SQL Procedure
                	  cs.execute();
                	  //gather value from out param and get results
                	  result=cs.getString(4);
                	  //set result to text box
                	  tres.setText(result);
                  }//if
					
					}//try
				catch(SQLException se){
					se.printStackTrace();
				}//catch
				}//else
		}//actionPerformed

		public static void main(String[] args) {		
			// TODO Auto-generated method stub
			System.out.println("main(-)method");
			ALLStmtsTest test=new ALLStmtsTest();
		}//main
		private class MyWindowAdapter extends WindowAdapter{
			public MyWindowAdapter(ALLStmtsTest allStmtsTest) {
				// TODO Auto-generated constructor stub
			}

			public void windowClosing(WindowEvent e){
				System.out.println("windowClosing");
				try{
					if(rs1!=null)
						rs1.close();
				}
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(rs2!=null)
						rs2.close();
					}
					catch(SQLException se){
						se.printStackTrace();
					}
				try{
					if(cs!=null)
						cs.close();
				}
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(st!=null)
						st.close();
				}
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(ps!=null)
						ps.close();
				}
				catch(SQLException se){
					se.printStackTrace();
				}
				try{
					if(con!=null)
						con.close();
				}
				catch(SQLException se){
					se.printStackTrace();
				}
			}//method
		}//inner class
	}//class
				
			