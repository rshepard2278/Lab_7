/**
 * @author Richard Shepard
 * @version Mar 21, 2015
 */
package com.rshepard.atm;

public class Start {

	/**Creates an ATM object of type Processor
	 * and starts the interface
	 * @param args Command line String args
	 */
	public static void main(String[] args) {
		Processor ATM = new Processor(true);
		ATM.start();
	}

}
