/*
	KYCustomersDriver.java
	Author:	Brian Newby
	Date:	4/24/2017
	Demonstrates a graphical user interface and an event listener.
*/

import javax.swing.JFrame;
import java.io.*;
public class KYCustomersDriver 
{
	/*Creates the frame for the DB1 program*/
	public static void main(String[] args) throws IOException
	{
		JFrame frame = new JFrame ("Create Kentucky Customer Report");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		KYCustomers panel = new KYCustomers();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}