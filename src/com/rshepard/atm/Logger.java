/**
 * @author Richard Shepard
 * @version Mar 14, 2015
 */
package com.rshepard.atm;

import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**	
 * 	Logger class is used for logging
 * 	ATM actions and generating receipts
 * 	one per customer.  This is a singleton
 * 	class with static methods so that only one
 * 	logger can be instantiated and used across all classes
 * 	in the package without passing the logger
 * 	object to every class that uses it.
 */
public class Logger {
	
	private static ArrayList<String> log;
	private static ArrayList<String> receipt;
	private static int entryCount;
	private static Logger Logger;
	
	/**
	 * Creates a new object of 
	 * type Logger and initializes two arraylists: one for
	 * the ATM log and one for the customers receipt. Also
	 * has a static entry counter to track log entries
	 * and adds a title to the log. This constructor is
	 * set to private so that only one logger
	 * can be created through the
	 * getInstance method
	 */
	private Logger() {
		log = new ArrayList<>();
		receipt = new ArrayList<>();
		entryCount = 1000;
		addTitle();
	}
	
	/**Used to create a single Logger object by
	 * using an if statement to test if a logger
	 * has already been created. If so the logger
	 * that was created is passed back. If not it
	 * creates a new logger and stores it in the
	 * logger field
	 * @return Returns the Logger that is or was created
	 * depending on if it is the first time this method is
	 * called
	 */
	public static Logger getInstance() {
		if(Logger == null) {
			Logger = new Logger();
		}
		return Logger;
	}
	
	/**Adds a title to the receipt including ATM location,
	 * account number, transaction number, and beginning balances
	 * for the checking and savings account.
	 * @param account An account object that contains the account
	 * number to be printed on the receipt
	 * @param transactionNumber Adds the specific transaction number
	 * to the receipt
	 */
	public static void addReceiptTitle(Account account, int transactionNumber) {
		receipt.add("           Bank of CIS-225");
		receipt.add("------------------------------------------\n");
		receipt.add(LocalDateTime.now().toString());
		receipt.add("Location: 1111 Bridge St");
		receipt.add("          Charlevoix, MI  49720");
		receipt.add("Account #" + account.getAccountNumber());
		receipt.add("Transaction #" + transactionNumber);
		receipt.add("Beginning Balances: ");
		receipt.add("   Checking--------------$" + account.getCheckingBalance());
		receipt.add("   Savings---------------$" + account.getSavingsBalance());
	}
	
	/**Adds the footer to the receipt for transactions that are
	 * cancelled before any transactions are made
	 * @param account The account number that was accessed
	 * @param messageText The message text that is printed on 
	 * the receipt
	 */
	public static void endReceipt(int account, String messageText) {
		receipt.add(messageText);
		receipt.add("Transaction ended for Account #" + account);
	}
	
	/**Adds the footer to the receipt for transactions that are 
	 * made by the user including the ending balances after 
	 * transactions are made.
	 * @param account An account object containing the account number
	 * @param messageText Any additional messages that are added
	 * to the receipt when transactions are finished.
	 */
	public static void endReceipt(Account account, String messageText) {
		receipt.add(messageText);
		receipt.add("Transaction ended for Account #" + account.getAccountNumber());
		receipt.add("Ending Balances: ");
		receipt.add("   Checking--------------$" + account.getCheckingBalance());
		receipt.add("   Savings---------------$" + account.getSavingsBalance());
	}
	
	/**Adds the title to the log
	 * including the date and time
	 * it was booted up.
	 */
	private static void addTitle() {
		log.add("           Log Start: " + LocalDateTime.now().toString());
		log.add("\nLine#     Date:                                      Action:");
		log.add("---------------------------------------------------------------------------");
	}

	/**Adds an entry to the log arraylist in the
	 * form of a String
	 * @param text The string that is to be added to 
	 * the log
	 */
	public static void log(String text) {
		entryCount++;
		log.add(entryCount + "      " + LocalDateTime.now().toString() + "               " + text);
	}
	
	/**Adds an entry to the receipt arraylist in the
	 * form of a String
	 * @param text The string that is to be added to 
	 * the receipt
	 */
	public static void receipt(String text) {
		receipt.add(text);
	}
	
	/**Adds the text string to both the log and
	 * the receipt arraylist
	 * @param text The string that is to be added to 
	 * the log and receipt.
	 */
	public static void addToBoth(String text) {
		log(text);
		receipt(text);
	}
	
	/**Creates a new JFrame windows and prints
	 * the contents of the log arraylist
	 * to the window 
	 */
	public static void printLog() {
		JFrame logPrint = new JFrame();
		logPrint.setTitle("LOG");
		logPrint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logPrint.setLocation(750, 70);
		logPrint.setSize(570, 500);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(0,1));
		
		int i = 0;
		for (String line : log) {
			JLabel label = new JLabel();
			label.setBounds(0, i * 25, 550, 25);
			label.setText(line);
			content.add(label);
			i++;
		}
		JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logPrint.setContentPane(scrollPane);
		logPrint.setVisible(true);
	}
	
	/**Creates a new JFrame windows and prints
	 * the contents of the receipt arraylist
	 * to the window 
	 */
	public static void printReceipt() {
		JFrame receiptPrint = new JFrame();
		receiptPrint.setTitle("Receipt");
		receiptPrint.setLocation(350, 400);
		receiptPrint.setSize(250, 350);
		receiptPrint.setBackground(Color.white);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(0,1));
		content.setBackground(Color.white);
		
		int i = 0;
		for (String line : receipt) {
			JLabel label = new JLabel();
			label.setBounds(0, i * 25, 550, 25);
			label.setText(line);
			label.setAlignmentX(SwingConstants.CENTER);
			content.add(label);
			i++;
		}
		JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		receiptPrint.setContentPane(scrollPane);
		receiptPrint.setVisible(true);
		receipt.clear();
	}
}
