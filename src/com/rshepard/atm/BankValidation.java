/**
 * @author Richard Shepard
 * @version Mar 14, 2015
 */
package com.rshepard.atm;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *	Creates a virtual bank that stores account numbers,
 *	PINs, and a current account that is utilized by the 
 *	Processor class for balance queries, validation, and 
 *	transactions. This class also makes use of the singleton
 *	class Logger that logs transactions and generates receipts.
 *	PINs are in the form of String so that a number that starts
 *	with a zero can be used ex. "0001"
 */
public class BankValidation {

	private boolean debug;
	private static int transactionNumber;
	private Account currentAccount;
	private ArrayList<Account> accounts;
	private HashMap<String, Account> pins;

	/**
	 * This constructor creates an object of a bank,
	 * initializes the collections and transaction number fields,
	 * and enables debug information.
	 * 
	 * @param debug If this value is true it will load and display
	 * account information, PINS, and balances that are used for testing
	 * and debugging.
	 */
	public BankValidation(boolean debug) {
		this.debug = debug;
		accounts = new ArrayList<>();
		pins = new HashMap<>();
		currentAccount = new Account();
		transactionNumber = 10000;
		
		if (debug) {
			loadTestAccounts();
			loadTestPins();
		}
	}

	/**
	 *	This method is used to verify the pin number associated
	 *	with the account number and returns a boolean. If PIN is verified
	 *	it will log the action, initialize a receipt, and increment the transaction
	 *	number
	 * @param pinCommand The PIN is wrapped in a command object
	 * @param accountNumber The account number to be check against the PIN
	 * @return True if verified, False if not 
	 */
	public boolean verifyPin(Command pinCommand, int accountNumber) {
		String pin = pinCommand.getCommand();
		Account account = null;
		boolean isValid = false;
		for (String key : pins.keySet()) {
			if (pin.equals(key)) {
				account = pins.get(key);
				if (account.getAccountNumber() == accountNumber) {
					isValid = true;
				}
			}
		}
		Logger.log("Pin validated: " + isValid);
		if(isValid) {
			transactionNumber++;
			Logger.addReceiptTitle(currentAccount, transactionNumber);
		} 
		return isValid;
	}

	/**Verifies the account number passed in against the 
	 * account numbers that are loaded into this class, returns
	 * validation in the form of a boolean. Once verified it will load
	 * the account to be used in subsequent transactions. Will also log
	 * accesses on the passed account number and if process was verified. 
	 * @param accountNumber Integer representing the account number to 
	 * be verified.
	 * @return Returns True if verified and False if not.
	 */
	public boolean verifyAccountNumber(int accountNumber) {
		boolean isValid = false;
		Logger.log("Access Attempt on Account #" + accountNumber);
		for (Account account : accounts) {
			if (account.getAccountNumber() == accountNumber) {
				isValid = true;
				currentAccount = account;
			}
		}
		Logger.log("Account Verified: " + isValid);
		return isValid;
	}

	/**
	 * getAccount returns the instance of the current
	 * account being used and creates a log entry of the
	 * action performed
	 * @param accountNumber Integer representation of the account number
	 * @return Account The instance of the current account.
	 */
	public Account getAccount(int accountNumber) {
		Logger.log("Account #" + currentAccount.getAccountNumber()
				+ " is loaded.");
		return currentAccount;
	}

	/**Subtracts the given amount associated with the selected account
	 * type (checking, savings) from the currentAccount and creates a 
	 * log and receipt entry.
	 * @param selection Emun value indicating the account type (checking, savings) to be withdrawn from
	 * @param amount A double amount of money to be withdrawn from selected account type
	 */
	public void withdraw(Selection selection, double amount) {
		switch (selection.toString().toLowerCase()) {
		case "checking":
			currentAccount.setCheckingBalance(currentAccount
					.getCheckingBalance() - amount);
			break;
		case "savings":
			currentAccount.setSavingsBalance(currentAccount.getSavingsBalance()
					- amount);
			break;
		}
		Logger.receipt("Withdraw from " + selection.toString() + "   $" + amount);
		Logger.log("Withdraw from " + selection.toString() + "   $" + amount);
		Logger.log("Dispensing $" + amount);
	}

	/**Returns the value of balance that is associated with the
	 * account selecion type (checking, savings) that is passed and
	 * creates a log/receipt entries.
	 * @param selection Enum value indicating checking or savings
	 * @return double balance of the current account and type (checking or savings)
	 */
	public double getBalance(Selection selection) {
		double balance = 0;
		switch (selection.toString().toLowerCase()) {
		case "checking":
			balance = currentAccount.getCheckingBalance();
			break;
		case "savings":
			balance = currentAccount.getSavingsBalance();
			break;
		}
		Logger.receipt(selection + " balance obtained: $" + balance);
		Logger.log(selection + " balance obtained: $" + balance);
		return balance;
	}

	/**Returns a boolean indicating if the current amount is less than
	 * or equal to the balance of the current account and type (checking or savings)
	 * and creates a log/receipt entries.
	 * @param selection Enum value indicating (checking or savings)
	 * @param amount Amount to be verified
	 * @return True if amount is less than or equal to the current (checking or savings) balance.
	 */
	public boolean verifyBalance(Selection selection, double amount) {
		boolean isValid = false;
		switch (selection.toString().toLowerCase()) {
		case "checking":
			if (currentAccount.getCheckingBalance() >= amount) {
				isValid = true;
			}
			break;
		case "savings":
			if (currentAccount.getSavingsBalance() >= amount) {
				isValid = true;
			}
			break;
		default:
			isValid = false;
		}
		Logger.log("Amount ($" + amount + ") for " + selection.toString()
				+ " verified: " + isValid);
		return isValid;
	}

	/**Creates a log entry indicating an attempt to deposit
	 * an amount before the envelope is inserted into the machine.
	 * @param accoutType Enum value indicating checking or savings.
	 * @param amount double value indicating the amount that is about to processed.
	 */
	public void deposit(Selection accoutType, double amount) {
		Logger.log("Atempting to deposit: $" + amount + " into "
				+ accoutType.toString() + " pending verification.");
	}

	/**Adds the amount from the (checking or savings) from the current account
	 * and creates log/receipt entries. This is called after the envelope has been
	 * inserted into the machine
	 * @param accountSelected Enum value indicating (checking or savings)
	 * @param amount double amount to be added to the current account type (checking or savings) 
	 * @param gotDeposit boolean indicating if the envelope was received by the ATM
	 */
	public void depositValidated(Selection accountSelected, double amount,
			boolean gotDeposit) {
		String accountType = accountSelected.toString().toLowerCase();
		if (gotDeposit) {
			switch (accountType) {
			case "checking":
				currentAccount.setCheckingBalance((currentAccount
						.getCheckingBalance() + amount));
				break;
			case "savings":
				currentAccount.setSavingsBalance((currentAccount
						.getSavingsBalance() + amount));
				break;
			}
			Logger.log("Deposit of $" + amount + " into " + accountType
					+ ": Accepted (Please check envelope box)");
			Logger.receipt("Deposited $" + amount + " into " + accountType);
			
		} else {
			Logger.log("Deposit into " + accountType
					+ ": timed out (No envelope inserted)");
		}
	}

	/**Verifies and transfers an amount from one account type (checking or savings) to another.
	 * Also creates a log/receipt entry
	 * @param accountFrom Selection (checking or savings) for amount to be subtracted from
	 * @param accountTo Selectin (checking or savings) for amount to be added to
	 * @param amount double amount to be transferred
	 * @return True is amount is valid and transaction is processed, False if not.
	 */
	public boolean verifyAndTransfer(Selection accountFrom, Selection accountTo, double amount) {
		boolean isVerified = false;
		Logger.log("Attempting to transfer $" + amount + " from "
				+ accountFrom.toString() + " to " + accountTo.toString());
		double checkingBalance = currentAccount.getCheckingBalance();
		double savingsBalance = currentAccount.getSavingsBalance();
		switch (accountFrom.toString().toLowerCase()) {
			
			case "checking":
				if (amount <= checkingBalance) {
					currentAccount.setCheckingBalance((checkingBalance - amount));
					currentAccount.setSavingsBalance(savingsBalance + amount);
					isVerified = true;
				} else {
					isVerified = false;
				}
				break;
	
			case "savings":
				if (amount <= savingsBalance) {
					currentAccount.setCheckingBalance((checkingBalance + amount));
					currentAccount.setSavingsBalance(savingsBalance - amount);
					isVerified = true;
				} else {
					isVerified = false;
				}
				break;
		}
		Logger.log("Transfer of $" + amount + " from " + accountFrom.toString() 
				+ " to " + accountTo.toString() + "  verified: " + isVerified);
		Logger.receipt("Transfer of $" + amount + " from " + accountFrom.toString() 
				+ " to " + accountTo.toString());
		return isVerified;
	}

	/**
	 * If debug is true when object is instantiated this will load
	 * 3 dummy accounts for testing.
	 */
	private void loadTestAccounts() {
		accounts.add(new Account("Richard", "Shepard", 80, 4000));
		accounts.add(new Account("John", "Doe", 500, 1820));
		accounts.add(new Account("Jane", "Doe", 4000, 10000));
	}

	/**
	 * If debug is true when object is instantiated this will load
	 * 3 PINs associated with the dummy accounts used for testing.
	 */
	private void loadTestPins() {
		pins.put("1111", accounts.get(0));
		pins.put("9876", accounts.get(1));
		pins.put("1023", accounts.get(2));
	}

	/**If debug is true this method is used to pass the accounts
	 * to the DebugScreen class to display the information
	 * @return an ArrayList of dummy accounts.
	 */
	public ArrayList<Account> getAccounts() {
		ArrayList<Account> queriedAccounts = null;
		if (debug) {
			queriedAccounts = accounts;
		}
		return queriedAccounts;
	}

	/**If debug is true this method is used to pass the PINs for
	 * the dummy accounts to the DebugScreen class to display the information
	 * @return Returns a hashmap of the pins and accounts
	 */
	public HashMap<String, Account> getPins() {
		HashMap<String, Account> queriedPins = null;
		if (debug) {
			queriedPins = pins;
		}
		return queriedPins;
	}

}
