/**
 * @author Richard Shepard
 * @version Mar 15, 2015
 */
package com.rshepard.atm;

/**	Selection Enum used to classify
 * 	selections made by the user
 *
 */
public enum Selection {
	
	WITHDRAW("Withdraw"),
	DEPOSIT("Deposit"),
	TRANSFER("Transfer"),
	BALANCE("Balance"),
	YES("yes"),
	NO("no"),
	TWENTY("20"),
	FORTY("40"),
	SIXTY("60"),
	EIGHTY("80"),
	HUNDRED("100"),
	HUNDREDTWENTY("120"),
	OTHER("Other"),
	CHECKING("Checking"),
	SAVINGS("Savings"),
	CONTINUE("Continue"),
	CANCEL("Cancel");
	
	private String selection;
	
	/**Sets the current selection to 
	 * the selection field
	 * @param selection The current selection
	 */
	Selection(String selection) {
		this.selection = selection;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	/**Converts the current Selection to a String.
	 * @return Returns the string value of the current Selection
	 */
	public String toString() {
		return this.selection;
	}
	
}
