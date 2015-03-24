/**
 * @author Richard Shepard
 * @version Mar 14, 2015
 */
package com.rshepard.atm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


/**
 *	Processor class creates and handles the 
 *	GUI, Controller, Bank, Logger, and Commands input by 
 *	the user for the ATM
 */
public class Processor {

	private GuiController gc;
	private Gui gui;
	private BankValidation bank;
	private Account account;
	private final int MAX_ATTEMPTS = 3;
	private Timer timer;
	private ActionListener aL;

	/**Creates an instance of an ATM. Initializes
	 * a timer, Logger, BankValidation, GUI, and GUI controller.
	 * If debug is set to true it will load and display account numbers,
	 * PINs, and balances that are used for debugging.
	 * @param debug If debug is true test data will be loaded 
	 * and displayed
	 */
	public Processor(boolean debug) {
		initTimerAndListener();
		Logger.getInstance();
		Logger.log("ATM Boot up");
		bank = new BankValidation(debug);
		if(debug) {
			loadTestData();
		}
		gui = new Gui();
		gc = new GuiController(gui);
	}

	/**
	 * Starts the ATM, sets the screens, processes the inputs
	 * from the user, and validates transactions
	 * through the bank.
	 */
	public void start() {
		int attemptCount = 0;
		int accountNumber = 0;
		account = null;
		boolean running = true;
		boolean isShutDown = false;
		String outputText = "";
		while(!isShutDown) {
			gui.setWelcomeScreen();
			if(gc.isCardInserted()) {
				Logger.log("Card inserted");
				while(running && attemptCount < MAX_ATTEMPTS) {
					gui.setGetAccountScreen(outputText);
					Command command = gc.getAccountNumber();
					if(command == null || command.getCommandCode() == 10) {   				// Sorry about the magic number -see command class definition for code
						running = false;
					} else {
						accountNumber = (int)command.getCommandCode();
					}
					if(bank.verifyAccountNumber(accountNumber)) {
						outputText = "";
						attemptCount = 0;
						while(running && attemptCount < MAX_ATTEMPTS) {
							gui.setPinScreen(outputText);
							Command pinCommand = gc.getPINValue();
							if(pinCommand.getCommandCode() == 10) {     // Sorry about the magic number -see command class definition for code
								running = false;
							} else if(bank.verifyPin(pinCommand, accountNumber)) {
								running = true;
								account = bank.getAccount(accountNumber);
								while(running) {
									gui.setMainScreen(account.getFirstName(),Integer.toString(account.getAccountNumber()));
									Selection mainSelection = gc.getMainSelection();
									running = processMainSelection(mainSelection);
								}
							} else {
								attemptCount++;
								outputText = "Invalid PIN. Try Again";
							}
						}
					} else {
						attemptCount++;
						outputText = "Invalid account. Try Again";
					}
					
				}
			}
			if(attemptCount == 3) {
				gui.setPinErrorScreen();
				Logger.endReceipt(accountNumber, "Card Retained. Please Contact Bank Manager");
				Logger.log("Card #" + accountNumber + " retained");
				sleep(3000);
			} else if(account == null) {
				Logger.log("Transaction Cancelled by User");
			} else {
				Logger.endReceipt(account, "Tranaction ended");;
				Logger.log("Card #" + accountNumber + " returned");
			}
			Logger.printReceipt();
			gui.setEndScreen();
			isShutDown = processEndCommand(gc.getEndSelection());
			System.out.println("Processing System Shut down");
		}
		System.out.println("System Shut down");
		gui.destroy();
		Logger.printLog();
	}

	/**Sets, processes, and validates selections that are made on the main screen.
	 * Type of transaction: withdraw, balance, transfer, and deposit
	 * @param selection
	 * @return The return command to quit or not
	 */
	private boolean processMainSelection(Selection selection) {

		boolean isRunning = true;
		String selectionText = selection.toString().toLowerCase();
		switch (selectionText) {
		case "withdraw":
			processWithdrawCommand(selection);
			break;
		case "deposit":
			processDepositCommand(selection);
			break;
		case "transfer":
			processTransferCommand(selection);
			break;
		case "balance":
			processBalanceCommand(selection);
			break;
		case "cancel":
			isRunning = false;
			break;
		}
		return isRunning;

	}

	/**Sets, processes, and validates withdraw amounts by setting the GUI and
	 * getting inputs from the controller
	 * @param prevSelection An Enum of type Selection that is used to display 
	 * information on the subsequent screen.
	 */
	private void processWithdrawCommand(Selection prevSelection) {
		boolean isProcessed = false;
		Selection amountSelected = null;
		while(!isProcessed) {
			Selection accountSelection = getAccountTypeSelection(prevSelection);
			if(accountSelection == Selection.CANCEL) {
				isProcessed = true;
			} else {
				while(!isProcessed) {
					amountSelected = getAmount(prevSelection);
					if(amountSelected == Selection.CANCEL) {
						isProcessed = true;
					} else {
					double amount = Double.parseDouble(amountSelected.toString());
						if(bank.verifyBalance(accountSelection, amount)) {
							bank.withdraw(accountSelection, amount);
							isProcessed = true;
							gui.setMessageScreen("Dispensing $" + amount, false);
							sleep(1500);
						} else {
							gui.setMessageScreen("Invalid Amount. Try again.", true);
							sleep(1500);
						}
						
					}
				}
			}
		}
	}
	
	/**Sets, processes, and validates the balance selection
	 * @param prevSelection An Enum of type Selection that is used to display 
	 * information on the subsequent screen.
	 */
	private void processBalanceCommand(Selection prevSelection) {
		boolean isProcessed = false;
		double amount = 0;
		Selection accountSelection = null;
		while(!isProcessed) {
			accountSelection = getAccountTypeSelection(prevSelection);
			if(accountSelection == Selection.CANCEL) {
				isProcessed = true;
			} else {
				amount = bank.getBalance(accountSelection);
				gui.setBalanceScreen(accountSelection, amount);
				sleep(1000);
				isProcessed = true;
			}
		}
	}

	/**Sets, processes, and validates the Deposit selection
	 * @param prevSelection An Enum of type Selection that is used to display 
	 * information on the subsequent screen.
	 */
	private void processDepositCommand(Selection prevSelection) {
		boolean isProcessed = false;
		while(!isProcessed) {
			Selection accountSelection = getAccountTypeSelection(prevSelection);
			if(accountSelection == Selection.CANCEL) {
				isProcessed = true;
			} else {
				while(!isProcessed) {
					gui.setNumericInputScreen("Deposit into", accountSelection.toString());
					double amount = gc.getNumericInputValue();
					if(gui.isCancelButtonPressed()) {
						isProcessed = true;
						Logger.log(accountSelection.toString() + " cancelled");
					} else {
						if(verifyDeposit(accountSelection, amount)) {
							bank.depositValidated(accountSelection, amount, true);
						} else {
							bank.depositValidated(accountSelection, amount, false);
						}
						isProcessed = true;
					}
				}
			}
		}
	}

	/**Sets, processes, and validates the transfer selection.
	 * @param prevSelection An Enum of type Selection that is used to display 
	 * information on the subsequent screen.
	 */
	private void processTransferCommand(Selection prevSelection) {
		boolean isProcessed = false;
		while(!isProcessed) {
			gui.setInputText("Transfer from:");
			Selection accountFrom = getAccountTypeSelection(prevSelection);
			if(accountFrom == Selection.CANCEL) {
				isProcessed = true;
			} else {
				while(!isProcessed) {
					gui.setInputText("Transfer to:");
					Selection accountTo = getAccountTypeSelection(prevSelection);
					if(accountTo == Selection.CANCEL) {
						isProcessed = true;
					} else {
						while(!isProcessed) {
							gui.setNumericInputScreen("Please input amount to transfer:", accountTo.toString());
							double amount = gc.getNumericInputValue();
							if(gui.isCancelButtonPressed()) {
								isProcessed = true;
							} else {
								if(bank.verifyAndTransfer(accountFrom, accountTo, amount)) {
									gui.setMessage2Text("Transfer Complete");
								} else {
									gui.setMessage2Text("Invalid amount.  Transfer Cancelled");
								}
								sleep(1800);
								isProcessed = true;
							}
						}
					}
				}
			}
		}
	}

	/**Sets, processes, and validates the Account type (Checking or savings) account
	 * selection screen.
	 * @param prevSelection An Enum of type Selection that is used to display 
	 * information on the subsequent screen.
	 * @return Returns the Selection made by the user.
	 */
	private Selection getAccountTypeSelection(Selection prevSelection) {
		Selection accountSelection = null;
		boolean isProcessed = false;
		gui.setSelectAccoutScreen(prevSelection);
		while(!isProcessed) {
			accountSelection = gc.getAccountSelection();
			if(accountSelection == Selection.CANCEL) {
				isProcessed = true;
			} else if (accountSelection == Selection.CHECKING || accountSelection == Selection.SAVINGS) {
				isProcessed = true;
			}
			sleep(20);
		}
		return accountSelection;
	}
	
	/**Sets, processes, and verifies the deposit envelope slot
	 * @param accountType
	 * @param amount
	 * @return Returns the boolean value if the amount was 
	 * verified by the bank or not
	 */
	private boolean verifyDeposit(Selection accountType, double amount) {
		
		bank.deposit(accountType, amount);
		timer.start();
		gui.setDepositScreen(true);
		boolean isDeposited = false;
		isDeposited = gc.getDepositEvelope();
		sleep(3000);
		if(isDeposited) {
			timer.stop();
			isDeposited = true;
			gui.setMessage2Text("Deposit Accepted");
			sleep(1500);
		} else {
			timer.stop();
		}
		gui.setCommandButtonPressed(false);
		return isDeposited;
	}
	
	/**Sets and processes the end selection screen
	 * @param command Command to be processed (continue or quit)
	 * @return	Returns whether or not the ATM is to be shutdown.
	 */
	private boolean processEndCommand(Command command) {
		boolean isShutDown = false;
		String commandString = command.getCommand().toString().toLowerCase();
		if(commandString.equals("cancel")) {
			isShutDown = true;
		} else {
			isShutDown = false;
			start();
		}
		return isShutDown;
	}
	
	/**
	 * Initializes the timer and listener for
	 * the deposit envelope slot.
	 */
	private void initTimerAndListener() {
		timer = new Timer(5000, aL);
		aL = new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				gui.setDepositScreen(false);
				gui.setMessage2Text("Deposit Timed Out");
				gui.setCommandButtonPressed(true);
				sleep(1000);
				timer.stop();
			}
		};
		timer = new Timer(5000, aL);
	}
	
	/**Sets, processes, and returns the amount selected for 
	 * withdraw in multiples of 20
	 * @param prevSelection 
	 * @return An Enum of type Selection that is used to display 
	 * information on the subsequent screen.
	 */
	private Selection getAmount(Selection prevSelection) {
		Selection amountSelected = null;
		gui.setWithdrawScreen();
		amountSelected = gc.getAmount();
		return amountSelected;
	}
	
	/**The sleep command is used within "while" loops to pause the
	 * thread allowing for processing of user pressed buttons
	 * @param milliseconds The amount in milliseconds for the 
	 * system to pause.
	 */
	private void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Creates a new DebugScreen with dummy account numbers,
	 * PINS, and balances.
	 */
	private void loadTestData() {
		DebugScreen debugger = new DebugScreen(bank.getAccounts(), bank.getPins());
		debugger.start();
	}
}
