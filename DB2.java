/*	DB2.java
	Author:		Brian Newby	
	Date:		04/23/2017
	Purpose: 	To read the Customer.csv file and insert its data into a database.	*/
	
import java.util.Scanner;
import java.io.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DB2 extends JPanel
{
		//	Declare variables
		private int number = 0;
		private String gender = null, givenName = null, middleInitial = null, surName = null, streetAddress = null,
			city = null, state = null, zipCode = null, birthDay = null, ccType = null, ccNumber = null, ccExpires = null;		
		private Scanner inFile = null;	
		private JButton tableButton, exitButton;
	
	public DB2() throws IOException
	{
		
		// JPanel properties.
		setPreferredSize(new Dimension(400, 70));
		setBackground(Color.lightGray);
		
		//	Button components
		tableButton = new JButton("CREATE TABLE");
		exitButton = new JButton("EXIT APP");
		
		//	Add buttons to panel
		add(tableButton);
		add(exitButton);
		
		//	Decalre button listeners.
		tableButton.addActionListener(new TableListener());
		exitButton.addActionListener(new ExitListener());
	}
		
		//Listener for the report button to create a text file for the customers who live in KY.
	private class TableListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{			
		
		try
		{
			//	Dialog box with message indicating report is being filled.
			JOptionPane.showMessageDialog(null, "Customer table is populating.");
			
			// 	Connect to the StudentsReg database.
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:mysql://localhost/studentsreg","root","");
			
			if (connect != null)
			{
				//	Create the Customers table in the StudentsReg database.				
				Statement statement = connect.createStatement();
				boolean result = statement.execute("CREATE TABLE Customers (Number INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
				"PRIMARY KEY (Number), Gender char(10), GivenName varchar(255), MiddleInitial varchar(100), " +
				"SurName varchar(255), StreetAddress varchar (255), City varchar(255), State varchar(20), " +
				"ZipCode varchar(255), Birthday varchar(255), CCType varchar(255), CCNumber varchar(255), " +
				"CCExpires varchar(255));");
				
				//	Open stream to read from Customers.csv.
				inFile = new Scanner (new BufferedReader(new FileReader("Customers.csv")));
			
				//  Open connection to StudentsReg database.
			
				if (connect != null)
				{
				   Statement statement2 = connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
						
				   //	Ignore header in Customers.csv (first record).
				   inFile.nextLine();
			
				   //	Read data from Customers.csv.
				   while (inFile.hasNext())
				   {
				       inFile.useDelimiter(",");				
					   number = inFile.nextInt();
					   gender = inFile.next();
					   givenName = inFile.next();
					   middleInitial = inFile.next();
					   surName = inFile.next();
					   streetAddress = inFile.next();
					   city = inFile.next();
					   state = inFile.next();
					   zipCode = inFile.next();
					   birthDay = inFile.next();
					   ccType = inFile.next();
					   ccNumber = inFile.next();
					   ccExpires = inFile.nextLine();
				
					   //	Write data into Customers table. Use replace method to accomodate apostrophes in surnames and
					   //  eliminate quotations in addresse and city entries from the csv file.
					   int rowCount = statement2.executeUpdate("INSERT INTO Customers(Number, Gender, GivenName, " +
					   "MiddleInitial, SurName, StreetAddress, City, State, ZipCode, Birthday, CCType, CCNumber, CCExpires) " +
					   "VALUES ('"+number+"', '"+gender+"', '"+givenName+"', '"+middleInitial+"', " +
					   "'"+surName.replace("'","''")+"', '"+streetAddress.replace("\"", "")+"', '"+city.replace("\"", "")+"', " +
					   "'"+state+"', '"+zipCode+"', '"+birthDay+"', '"+ccType+"', '"+ccNumber+"', '"+ccExpires.substring(1)+"');");				
				    }
				
				      // Close connection.
				      connect.close();
			    }		
		    }
		}
		catch (SQLException ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		catch (Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			// Close the input stream from Customer.csv.
			if (inFile != null)
			{
				inFile.close();
			}
		}
		// Dialog box with message indicating report is ready.
			JOptionPane.showMessageDialog(null, "Customer table is ready.");		
		
	
	}
}
	// Listener for the exit button. It closes the application
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}

}	