/**
 * @author Richard Shepard
 * @version Mar 16, 2015
 */
package com.rshepard.atm;

/**
 * This class creates a virtual bank account
 * with the typical information. This class uses
 * a static int for the account number so that 
 * every time an account is created it assigns a 
 * unique number for each account by incrementing 
 * the the account pool every time the constructor
 * is called. 
 *
 */
public class Account {
	
	private String firstName;
	private String lastName;
	private int accountNumber;
	private double checkingBalance;
	private double savingsBalance;
	private static int accountPool = 110;
	
	/**
	 * 	Default constructor:
	 * 	Creates a single account,
	 * 	sets all values to zero, assigns
	 * 	a unique account number, and increments 
	 * 	the account pool by one.
	 */
	public Account() {
		firstName = "";
		lastName = "";
		accountPool++;
		accountNumber = accountPool;
		checkingBalance = 0;
		savingsBalance = 0;
	}
	
	/**
	 * Creates a single bank account to be used in the validation class
	 * and sets all the proper information relating to the bank account.
	 * Also assigns a unique account number and increments 
	 * the account pool by one.
	 * 
	 * @param firstName Sets the first name on the account
	 * @param lastName Sets the last name on the account
	 * @param checkingBalance Initializes the beginning checking account balance on the account
	 * @param savingsBalance Initializes the beginning savings balance on the account
	 */
	public Account(String firstName, String lastName, double checkingBalance, double savingsBalance) {
		accountPool++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountNumber = accountPool;
		this.checkingBalance = checkingBalance;
		this.savingsBalance = savingsBalance;
	}

	/**
	 * @return a value of double for the checkingBalance
	 */
	public double getCheckingBalance() {
		return checkingBalance;
	}

	/**
	 * @param checkingBalance the checkingBalance to set
	 */
	public void setCheckingBalance(double checkingBalance) {
		this.checkingBalance = checkingBalance;
	}

	/**
	 * @return a value of double for the savingsBalance
	 */
	public double getSavingsBalance() {
		return savingsBalance;
	}

	/**
	 * @param savingsBalance the savingsBalance to set
	 */
	public void setSavingsBalance(double savingsBalance) {
		this.savingsBalance = savingsBalance;
	}

	/**
	 * @return A string for the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return A string for the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the accountNumber
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

}
