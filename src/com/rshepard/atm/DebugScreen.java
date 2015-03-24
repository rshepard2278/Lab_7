/**
 * @author Richard Shepard
 * @version Mar 18, 2015
 */
package com.rshepard.atm;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *Debug Screen displays account numbers, PINs, and
 *balances used for the process of debugging when the
 *ATM boots up
 */
public class DebugScreen {
	
	private ArrayList<Account> accounts;
	private HashMap<String, Account> pins;
	
	/**Constructor initializes the accounts and PINs to
	 * be displayed when the ATM boots up
	 * @param accounts ArrayList from BankValidation class that holds the accounts
	 * @param pins HashMap from BankValidation class thats holds valid PINs associated with accounts
	 */
	public DebugScreen(ArrayList<Account> accounts, HashMap<String, Account> pins) {
		this.accounts = accounts;
		this.pins = pins;
	}
	
	/**Creates a separate window that
	 * wil hold account#, balances, and
	 * PINs.
	 * 
	 */
	public void start() {
		JFrame frame = new JFrame("Valid Accounts, balances and pins");
		JPanel content = setContent();
		frame.setContentPane(content);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(650, 70);
		frame.setSize(570, 150);
		frame.setVisible(true);
	}
	
	/**Returns a panel containing account information
	 * @return A JPanel containing account information
	 */
	private JPanel setContent() {
		JPanel content = new JPanel();
		content.setLayout(null);

		for(int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			String pin = getPin(account);
			String text = "Account #" + account.getAccountNumber() + "     PIN: " + pin + "     Checking Balance: " 
					+ account.getCheckingBalance() + "    Savings Balance: " + account.getSavingsBalance();
			JLabel label = new JLabel(text, SwingConstants.LEFT);
			label.setBounds(0, i * 25, 550, 25);
			content.add(label);
		}
		return content;
	}
	
	/**Returns a PIN associated with the passed account
	 * @param account to get the PIN for
	 * @return String value of the PIN
	 */
	private String getPin(Account account) {
		String pin = "";
		Account returnAccount = null;
		for(String key : pins.keySet()) {
			returnAccount = pins.get(key);
			if(account.equals(returnAccount)) {
				pin = key;
			}
		}
		return pin;
	}
}
