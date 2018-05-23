/*
	DB2Driver.java
	Author:	Brian Newby
	Date:	4/24/2017
	Demonstrates a graphical user interface and an event listener.
*/

import javax.swing.JFrame;
import java.io.*;
public class DB2Driver 
{
	/*Creates the frame for the DB1 program*/
	public static void main(String[] args) throws IOException
	{
		JFrame frame = new JFrame ("Create and Populate Customers Table");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DB2 panel = new DB2();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}