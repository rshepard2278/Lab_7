/**
 * @author Richard Shepard
 * @version Mar 14, 2015
 */
package com.rshepard.atm;

/**
 * Objects of type Command are used to store data
 * that is passed from the Gui to the GuiController.
 * Each instance will hold 2 fields: one is the string from
 * of the command and the other is a integer code. The key for 
 * the codes is as follows:
 *  Command code Key:
======================
	0 = 0
	1 = 1
	2 = 2
	3 = 3
	4 = 4
	5 = 5
	6 = 6
	7 = 7
	8 = 8
	9 = 9
	10 = cancel
	11 = enter
	21 = wB1
	22 = wb2
	23 = wb3
	24 = wb4
	31 = eb1
	32 = eb2
	33 = eb3
	34 = eb4
	41 = cardSlotButton
	42 = depositSlotButton
	100 = PIN

 * 
 */
public class Command {
	
	private String command;
	private double commandCode;

	
	/**Constructor creates a command String
	 * and command code (int)
	 * @param command String value of command
	 * @param code int value of command
	 */
	public Command(String command, double code) {
		this.command = command;
		this.commandCode = code;
	}
	
	/**
	 * Returns a formatted String of the
	 * Command that is utilized by the logger
	 * @return String form of the command
	 */
	public String toString() {
		return "Command Key Pressed: " + command;
	}
	/**Returns the command String
	 * @return the command String
	 */
	public String getCommand() {
		return this.command;
	}
	
	/**Returns the command code
	 * @return the commandCode int
	 */
	public double getCommandCode() {
		return commandCode;
	}
}
