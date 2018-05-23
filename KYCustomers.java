/*	KYCustomers.java
	Author: 	Brian Newby
	Date:		4/24/2017
	Purpose:	This program reads data from the Customers table in StudentsReg database and produces a 
				report of customers who live in KY.
*/

import java.io.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class KYCustomers extends JPanel
{
	//	Declare variables, object, and components.
	private int number = 0;
	private String firstName = "", middleInitial = "", lastName = "", city = "", state = "", birthdate = "", fullName = "";
	private Connection connect = null;
	private JButton reportButton, exitButton;
	
	public KYCustomers() throws IOException
	{
		// JPanel properties.
		setPreferredSize(new Dimension(400, 70));
		setBackground(Color.lightGray);
		
		//	Button components
		reportButton = new JButton("CREATE REPORT");
		exitButton = new JButton("EXIT APP");
		
		//	Add buttons to panel
		add(reportButton);
		add(exitButton);
		
		//	Decalre button listeners.
		reportButton.addActionListener(new ReportListener());
		exitButton.addActionListener(new ExitListener());		
	}
	
	//Listener for the report button to create a text file for the customers who live in KY.
	private class ReportListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//Create instance of today's date and date object
			Calendar now = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			
			try
			{
				//	Open stream to output to text file.
				PrintWriter outFile = null;
				outFile = new PrintWriter(new FileWriter("KYCustomers.txt"));
				
				// 	Connect to the StudentsReg database.
				connect = DriverManager.getConnection("jdbc:mysql://localhost/studentsreg","root","");
			
				if (connect != null)
				{
					// Print header with current date.
					outFile.println("Date of report: " + df.format(now.getTime()));
					outFile.println();
					outFile.println("-------------------------------------------------------------------------------------");
					outFile.printf("%-15s %-24s %-20s %-10s %-10s", "Customer#", "Name", "City", "State", "Birthdate");
					outFile.println();
					outFile.println("-------------------------------------------------------------------------------------");
					outFile.println("-------------------------------------------------------------------------------------");
					
					//	Process SQL statement against the database.	
					Statement statement = connect.createStatement();
					
					//	Statements getting information of customers living in Kentucky.
					String 	   sqlStmnt = "SELECT Number, GivenName, MiddleInitial, SurName, City, State, Birthday";
					sqlStmnt = sqlStmnt + " FROM Customers WHERE State = 'KY'";					
					
					statement.execute(sqlStmnt);
				
					ResultSet rs = statement.getResultSet();
					
					//Print the KYCustomers report
					if (rs != null)
					{
						while (rs.next())
						{
							if(!(rs.getString(6).equals(state)))
							{
								fullName = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
								
								outFile.println("--------------------------------------------------------------------------" +
												"-----------");
								outFile.printf("%-15s %-24s %-20s %-10s %-10s", rs.getString(1), fullName, 
												rs.getString(5).replace("\"", ""), rs.getString(6), rs.getString(7));
								outFile.println();
							}
						}						
					}									
				}
				connect.close();
				outFile.close();	
			}
			//	Error checking
			catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage());
				ex.printStackTrace();

			} 
			catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
				ex.printStackTrace();
			}
			//	Dialog box with message indicating report is ready.
			JOptionPane.showMessageDialog(null, "Kentucky Customers report is ready. Open KYCustomersReport.txt to " + 
										"read the report.");
		}
	}	
	
	//Listener for the exit button. It closes the application
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
}