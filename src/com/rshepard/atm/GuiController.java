/**
 * @author Richard Shepard
 * @version Mar 14, 2015
 */
package com.rshepard.atm;

import java.math.BigDecimal;

/**
 *	GuiController listens for and 
 *	sends input from the user on 
 *	GUI to the Processor class of
 *	the ATM
 */
public class GuiController {

	private Gui gui;
	private double inputValue;
	private int piNumbers[];
	private int cancelCount;
	private int accountNumber;
	private String pin;
	private static final int PIN_LENGTH = 4;

	/**Constructor creates a controller object 
	 * and links it to the GUI
	 * @param gui the GUI that is created by the 
	 * processor
	 */
	public GuiController(Gui gui) {
		this.gui = gui;
		cancelCount = 0;
		inputValue = 0;
	}

	/**Gets the is card inserted value of
	 * the GUI and returns it to the processor
	 * @return True if card inserted button
	 * is pressed
	 */
	public boolean isCardInserted() {
		boolean isCardInserted = false;
		while (!isCardInserted) {
			if (gui.isCardInserted()) {
				isCardInserted = true;
			}
			sleep(20);
		}
		return isCardInserted;
	}

	/**Passes the account selected by the user to
	 * the processor
	 * @return Returns value of type Account
	 */
	public Selection getAccountSelection() {
		Selection selection = null;
		Command command = null;
		command = getCommand();
		selection = processSelection(command);

		return selection;
	}

	/**Waits for input from
	 * the user  and passes the 
	 * account number to the processor
	 * when the enter key is pressed
	 * @return Returns a Command that contains
	 * the account number
	 */
	public Command getAccountNumber() {
		accountNumber = 0;
		int input = 0;
		boolean isProcessed = false;
		Command command = null;

		while (!isProcessed) {
			if(gui.isEnterButtonPressed()) {
				isProcessed = true;
			} else if (gui.isNumericButtonPressed()) {
				cancelCount = 0;
				input = (int) gui.getCommand().getCommandCode();
				setAccountValue(input);
				gui.setInputText(Integer.toString(accountNumber));
				command = new Command("account number", accountNumber);
				gui.setNumericButtonPressed(false);
			} else if (gui.isCancelButtonPressed() && accountNumber == 0) {
				isProcessed = true;
				command = gui.getCommand();
			} else if (gui.isCancelButtonPressed()) {
				cancelCount++;
				gui.setCancelButtonPressed(false);
				gui.setInputText("");
				command = getAccountNumber();
				gui.setEnterButtonPressed(true);
			}
			sleep(20);
		}
		gui.setInputText("");
		gui.setCancelButtonPressed(false);
		gui.setEnterButtonPressed(false);
		return command;
	}

	/**Displays and returns a numeric value inputed
	 * by the user to the processor when the enter key
	 * is press
	 * @return Returns a value of double to the processor
	 */
	public double getNumericInputValue() {
		inputValue = 0;
		double input = 0;
		cancelCount = 0;
		gui.setInputText("$" + Double.toString(inputValue));
		while (!gui.isEnterButtonPressed() && cancelCount != 1) {
			if (gui.isNumericButtonPressed()) {
				cancelCount = 0;
				input = gui.getCommand().getCommandCode();
				setInputValue(input);
				inputValue = truncateDouble(inputValue);
				gui.setInputText("$" + Double.toString(inputValue));
				gui.setNumericButtonPressed(false);
			} else if (gui.isCancelButtonPressed()) {
				cancelCount++;
				gui.setCancelButtonPressed(false);
				//getNumericInputValue();
				gui.setEnterButtonPressed(true);
			}
			sleep(20);
		}
		gui.setInputText("");
		gui.setEnterButtonPressed(false);
		return inputValue;
	}

	/**Retrieves and returns an amount selected by
	 * the user for the withdraw screen in values
	 * of twenty
	 * @return Returns a Selection Enum containing an amount
	 */
	public Selection getAmount() {
		Command command = null;
		command = getCommand();
		Selection selection = processSelection(command);
		return selection;
	}
	
	/**Retrieves and returns the boolean true
	 * is the deposit envelope is pressed
	 * @return Returns true if a deposit enevelope
	 * has been inseted.
	 */
	public boolean getDepositEvelope() {
		Command command = getCommand();
		boolean isDeposited = false;
		if(command.getCommandCode() == 42) {
			isDeposited = true;
		}
		gui.setCommandButtonPressed(false);
		return isDeposited;
	}

	/**Retrieves and returns an Enum of type Selection
	 * containing the selection of the user to the
	 * processor
	 * @return Returns an Enum Selection
	 */
	public Selection getMainSelection() {
		Command command = null;
		command = getCommand();
		Selection selection = processSelection(command);
		return selection;

	}

	/**Waits for 4 digits to be inputed by the user
	 * and returns them to the processor when the enter
	 * button is pressed. Also handles clearing the value when
	 * the cancel button is pressed
	 * @return Returns a Command object containing the PIN or a 
	 * cancel command
	 */
	public Command getPINValue() {
		gui.enableKeyPad();
		piNumbers = new int[PIN_LENGTH];
		int pinCount = 0;
		double input = 0;
		boolean isProcessed = false;
		String hidePin = "";
		Command pinCommand = null;

		while (!isProcessed) {
			if(gui.isCancelButtonPressed() && cancelCount > 1) {
				isProcessed = true;
				pinCommand = gui.getCommand();
			} else if (gui.isEnterButtonPressed()) {
				isProcessed = true;
				pin = pinToString();
				pinCommand = new Command(pin, 100);
			} else if (gui.isCancelButtonPressed()) {
				cancelCount++;
				gui.setCancelButtonPressed(false);
				pinCommand = getPINValue();
				isProcessed = true;
			}
			if (pinCount < PIN_LENGTH) {
				if (gui.isNumericButtonPressed()) {
					cancelCount = 0;
					input = gui.getCommand().getCommandCode();
					piNumbers[pinCount] = (int) input;
					pinCount++;
					hidePin += "*";
				}
				gui.setInputText(hidePin);
				gui.setNumericButtonPressed(false);
			} else {
				gui.disableNumericKeys();
			}
			sleep(20);
		}
		gui.setEnterButtonPressed(false);
		gui.setCancelButtonPressed(false);
		gui.enableKeyPad();
		
		return pinCommand;
	}
	
	/**Wait for a end Command to be selected and returns
	 * it to the processor
	 * @return Returns a Command object containing the users selection
	 */
	public Command getEndSelection(){
		Command endCommand = null;
		endCommand = getCommand();
		return endCommand;
	}

	// Thanks to "Neel" on stackOverFlow for this method
	/**Truncates a double to two places after the decimal point
	 * @param d A doulbe to be truncated
	 * @return Returns a truncated double to two decimal points
	 */
	private double truncateDouble(double d) {
		double toBeTruncated = new Double(d);
		double truncatedDouble = new BigDecimal(toBeTruncated).setScale(3,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		return truncatedDouble;
	}

	/**Waits for and retreives a command object from
	 * the user
	 * @return Returns a Command object containing the
	 * command input from the user
	 */
	public Command getCommand() {

		boolean isCommandProcessed = false;
		Command command = null;

		while (!isCommandProcessed) {
			if (gui.isCommandButtonPressed()) {
				command = gui.getCommand();
				isCommandProcessed = true;
				gui.setCommandButtonPressed(false);
			} else if (gui.isCancelButtonPressed()) {
				command = gui.getCommand();
				isCommandProcessed = true;
				gui.setCancelButtonPressed(false);
			}
			sleep(20);
		}

		return command;
	}

	/**Processes the command input by the user into an
	 * Enum of type Selection
	 * @param command The command from the GUI
	 * @return Returns the command converted into a selection
	 * null if no corresponding selection is found.
	 */
	public Selection processSelection(Command command) {
		Selection selection = null;
		String commandString = command.getCommand().toLowerCase();
		Logger.log(command.toString());
		switch (commandString) {
		case "withdraw":
			selection = Selection.WITHDRAW;
			break;
		case "deposit":
			selection = Selection.DEPOSIT;
			break;
		case "transfer":
			selection = Selection.TRANSFER;
			break;
		case "balance":
			selection = Selection.BALANCE;
			break;
		case "$20":
			selection = Selection.TWENTY;
			break;
		case "$40":
			selection = Selection.FORTY;
			break;
		case "$60":
			selection = Selection.SIXTY;
			break;
		case "$80":
			selection = Selection.EIGHTY;
			break;
		case "$100":
			selection = Selection.HUNDRED;
			break;
		case "$120":
			selection = Selection.HUNDREDTWENTY;
			break;
		case "other":
			selection = Selection.OTHER;
			break;
		case "yes":
			selection = Selection.YES;
			break;
		case "no":
			selection = Selection.NO;
			break;
		case "checking":
			selection = Selection.CHECKING;
			break;
		case "savings":
			selection = Selection.SAVINGS;
			break;
		case "cancel":
			selection = Selection.CANCEL;
			break;
		default:
			selection = null;
			break;
		}

		return selection;
	}
	
	/**Allows the thread to sleep for a parameter of
	 * milliseconds. I had to use this because the
	 * program would not take input during certain
	 * "while" loops.
	 * @param milliseconds An integer for the amount of 
	 * milliseconds to sleep.
	 */
	private void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**Allows for display an input of type double so 
	 * that when a user pressed the one key it will display
	 * $0.01 instead of $1.00 and subsequent input to be displayed after 
	 * the decimal i.e. pressing the number two after the number one will 
	 * display $0.12
	 * @param input The amount that is inputed by the user.
	 */
	private void setInputValue(double input) {
		inputValue = (inputValue * 10) + (input * .01);
	}

	/**Allows for single digits to be input and display in 
	 * the order that they are pressed i.e. pressing one, four, five 
	 * will display 145.
	 * @param input The single numeric key pressed by the user.
	 */
	private void setAccountValue(int input) {
		accountNumber = (accountNumber * 10) + input;
	}

	/**Converts the numeric PIN value
	 * to a string so that PINs that begin
	 * with zero will contain the zero i.e. pressing zero, two, three, zero
	 * will be 0230 instead of 230.
	 * @return Returns the String value of the PIN
	 */
	private String pinToString() {
		String pin = "";
		for (int i = 0; i < PIN_LENGTH; i++) {
			pin += piNumbers[i];
		}
		return pin;
	}
}
