/**
 * @author Richard Shepard
 * @version Mar 14, 2015
 */
package com.rshepard.atm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**Creates an interactive JFrame representation of
 * an ATM. It contains all the usual buttons and 
 * displays of a typical ATM.
 */
/**
 *
 */
public class Gui implements ActionListener{
	
	private JFrame frame;

	private JLabel title;
	private JLabel outputLabel;
	private JLabel inputLabel;
	private JLabel messageLabel1;
	private JLabel messageLabel2;

	private JButton wButton1;
	private JButton wButton2;
	private JButton wButton3;
	private JButton wButton4;
	
	private JButton eButton1;
	private JButton eButton2;
	private JButton eButton3;
	private JButton eButton4;
	
	private JButton sKey1;
	private JButton sKey2;
	private JButton sKey3;
	private JButton sKey4;
	private JButton sKey5;
	private JButton sKey6;
	private JButton sKey7;
	private JButton sKey8;
	private JButton sKey9;
	private JButton sKeyCancel;
	private JButton sKey0;
	private JButton sKeyEnter;
	private JButton cardSlotButton;
	private JButton depositSlotButton;
	
	private Icon cardSlotIcon;
	private Icon depositSlotIcon;
	
	private Command systemCommand;
	
	private boolean commandButtonPressed;
	private boolean enterButtonPressed;
	private boolean cancelButtonPressed;
	private boolean numericButtonPressed;
	private boolean cardInserted;
	
	private int gap = 8;
	private int pad = 5;
	
	private Font titleFont = new Font("SansSerif", Font.BOLD, 30);
	private Font errorFont = new Font("SansSerif", Font.BOLD, 20);
	private Font smallMessageFont = new Font("SansSerif", Font.PLAIN, 12);
	private Font largeMessageFont = new Font("SansSerif", Font.PLAIN, 20);
	private Font buttonFont = new Font("SansSerif", Font.PLAIN, 20);
	private Font numberFont = new Font("Serif", Font.BOLD, 35);
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 500;
	private static final int SIDE_BAR_WIDTH = 150;
	private static final int SIDE_BAR_HEIGHT = 150;
	
	private static final String TITLE = "Bank of CIS-225";
	private static final String BUTTON_PAD = "             ";
	
	
	/**
	 * Creates and initializes
	 * the basic framework of the GUI
	 * for the ATM
	 */
	public Gui() {
		createFrame();
	}
	
	/**Creates the JFrame that contains 
	 * the dispaly and buttons
	 * for the ATM
	 */
	private void createFrame() {
		
		frame = new JFrame(TITLE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout(new BorderLayout(gap, gap));
		panel.setBorder(new EmptyBorder(pad, pad, pad, pad));
		title = new JLabel(TITLE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(titleFont);
		panel.add(title, BorderLayout.NORTH);
		
		panel.add(addKeyPad(), BorderLayout.SOUTH);
		panel.add(addWestButtons(), BorderLayout.WEST);
		panel.add(addIOText(), BorderLayout.CENTER);
		panel.add(addEastButtons(), BorderLayout.EAST);
		panel.setVisible(true);
		frame.setVisible(true);
	}
	
	/**Creates a JPanel that contains 4 JLabels
	 * that are used for displaying text on the
	 * screen of the ATM. Sets text alignment, colors,
	 * and fonts.
	 * @return JPanel to be added to the JFrame 
	 */
	private JPanel addIOText() {
		
		JPanel IOtext = new JPanel(new GridLayout(4, 1));
		IOtext.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		IOtext.setBackground(Color.white);
		
			messageLabel1 = new JLabel("");
			messageLabel1.setFont(smallMessageFont);
			messageLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			IOtext.add(messageLabel1);
			
			messageLabel2 = new JLabel("");
			messageLabel2.setFont(smallMessageFont);
			messageLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			IOtext.add(messageLabel2);
			
			inputLabel = new JLabel("");
			inputLabel.setFont(largeMessageFont);
			inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
			IOtext.add(inputLabel);
			
			outputLabel = new JLabel("");
			outputLabel.setFont(largeMessageFont);
			outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
			IOtext.add(outputLabel);
			
		return IOtext;
	}
	
	/**Creates and enables four buttons to be used on
	 * the west side of the JFrame. Initializes the size, font,
	 * and action listeners for the buttons
	 * @return JPanel containing the interactive buttons
	 */
	private JPanel addWestButtons() {
		
		JPanel westButtons = new JPanel(new GridLayout(4,1));
		westButtons.setSize(SIDE_BAR_WIDTH, SIDE_BAR_HEIGHT);
		
			wButton1 = new JButton(BUTTON_PAD);
			wButton1.setFont(buttonFont);
			wButton1.addActionListener(this);
			westButtons.add(wButton1);
			
			wButton2 = new JButton(BUTTON_PAD);
			wButton2.setFont(buttonFont);
			wButton2.addActionListener(this);
			westButtons.add(wButton2);
			
			wButton3 = new JButton(BUTTON_PAD);
			wButton3.setFont(buttonFont);
			wButton3.addActionListener(this);
			westButtons.add(wButton3);
			
			wButton4 = new JButton(BUTTON_PAD);
			wButton4.setFont(buttonFont);
			wButton4.addActionListener(this);
			westButtons.add(wButton4);
		
		return westButtons;
	}
	
	/**Creates and enables four buttons to be used on
	 * the east side of the JFrame. Initializes the size, font,
	 * and action listeners for the buttons
	 * @return JPanel containing the interactive buttons
	 */
	private JPanel addEastButtons() {
		
		JPanel eastButtons = new JPanel(new GridLayout(4, 1));
		
			eButton1 = new JButton(BUTTON_PAD);
			eButton1.setFont(buttonFont);
			eButton1.addActionListener(this);
			eastButtons.add(eButton1);
			
			eButton2 = new JButton(BUTTON_PAD);
			eButton2.setFont(buttonFont);
			eButton2.addActionListener(this);
			eastButtons.add(eButton2);
			
			eButton3 = new JButton(BUTTON_PAD);
			eButton3.setFont(buttonFont);
			eButton3.addActionListener(this);
			eastButtons.add(eButton3);
			
			eButton4 = new JButton(BUTTON_PAD);
			eButton4.setFont(buttonFont);
			eButton4.addActionListener(this);
			eastButtons.add(eButton4);
			
		return eastButtons;
	}
	
	/**Creates three large buttons along the bottom of the 
	 * JFrame. The two outer buttons used for ATM card entry and deposit envelopes. 
	 * They both utilize anonymous nested classes for custom icons.
	 * The inner button in a container that holds a standard ATM keypad with digits
	 * 1-9, cancel, and enter buttons. Also enables and sets action
	 * listeners for all the buttons.
	 * 
	 * @return JPanel containing the bottom row of buttons and keypad for the ATM
	 */
	private JPanel addKeyPad() {
		
		JPanel bottomPadding = new JPanel(new GridLayout(1, 3));
		
		cardSlotIcon = new Icon() {
			
			/**
			 * Creates a custom icon for the card entry slot
			 */
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				int rect1Width = 170;
				int rect1Height = 25;
				int arc = 10;
				int rect2Width = 140;
				int rect2Height = 12;
				int textX = x - 45;
				int textY = 70;
				String cardText;
				
				g.setColor(Color.gray);
				g.fillRoundRect(x - (rect1Width/2) , y - (rect1Height/2), rect1Width, rect1Height, arc, arc);
				g.setColor(Color.black);
				g.drawRoundRect(x - (rect1Width/2) , y - (rect1Height/2), rect1Width, rect1Height, arc, arc);
				if(!cardInserted) {
					cardText = "Click to enter Card";
					g.setColor(Color.GREEN);
					g.fillRoundRect(x - (rect2Width/2), y - (rect2Height/2), rect2Width, rect2Height, arc, arc);
					g.setColor(Color.black);
					g.drawRoundRect(x - (rect2Width/2), y - (rect2Height/2), rect2Width, rect2Height, arc, arc);
				} else {
					cardText = "Card Accepted";
					g.setColor(Color.black);
					g.fillRoundRect(x - (rect2Width/2), y - (rect2Height/2), rect2Width, rect2Height, arc, arc);
				}
				g.setColor(Color.black);
				g.drawString(cardText, textX, textY);
			}
			
			@Override
			public int getIconWidth() {
				return 0;
			}
			
			@Override
			public int getIconHeight() {
				return 0;
			}
		};
		
		cardSlotButton = new JButton(cardSlotIcon);
		cardSlotButton.setEnabled(true);
		cardSlotButton.addActionListener(this);
		bottomPadding.add(cardSlotButton);
	
		
		JPanel keyPad = new JPanel(new GridLayout(4, 3));
		keyPad.setBorder(BorderFactory.createLineBorder(Color.black));
		
			sKey1 = new JButton("1");
			sKey1.setFont(numberFont);
			sKey1.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey1.addActionListener(this);
			keyPad.add(sKey1);
			
			sKey2 = new JButton("2");
			sKey2.setFont(numberFont);
			sKey2.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey2.addActionListener(this);
			keyPad.add(sKey2);
		
			sKey3 = new JButton("3");
			sKey3.setFont(numberFont);
			sKey3.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey3.addActionListener(this);
			keyPad.add(sKey3);
			
			sKey4 = new JButton("4");
			sKey4.setFont(numberFont);
			sKey4.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey4.addActionListener(this);
			keyPad.add(sKey4);
			
			sKey5 = new JButton("5");
			sKey5.setFont(numberFont);
			sKey5.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey5.addActionListener(this);
			keyPad.add(sKey5);
			
			sKey6 = new JButton("6");
			sKey6.setFont(numberFont);
			sKey6.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey6.addActionListener(this);
			keyPad.add(sKey6);
			
			sKey7 = new JButton("7");
			sKey7.setFont(numberFont);
			sKey7.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey7.addActionListener(this);
			keyPad.add(sKey7);
			
			sKey8 = new JButton("8");
			sKey8.setFont(numberFont);
			sKey8.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey8.addActionListener(this);
			keyPad.add(sKey8);
			
			sKey9 = new JButton("9");
			sKey9.setFont(numberFont);
			sKey9.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey9.addActionListener(this);
			keyPad.add(sKey9);
			
			sKeyCancel = new JButton("X");
			sKeyCancel.setForeground(Color.red);
			sKeyCancel.setFont(numberFont);
			sKeyCancel.setBorder(BorderFactory.createRaisedBevelBorder());
			sKeyCancel.addActionListener(this);
			keyPad.add(sKeyCancel);
			
			sKey0 = new JButton("0");
			sKey0.setFont(numberFont);
			sKey0.setBorder(BorderFactory.createRaisedBevelBorder());
			sKey0.setForeground(Color.black);
			sKey0.addActionListener(this);
			keyPad.add(sKey0);
			
			sKeyEnter = new JButton("Enter");
			sKeyEnter.setFont(new Font("Serif", Font.BOLD, 20));
			sKeyEnter.setBorder(BorderFactory.createRaisedBevelBorder());
			sKeyEnter.setForeground(Color.green);
			sKeyEnter.addActionListener(this);
			keyPad.add(sKeyEnter);
			
			bottomPadding.add(keyPad);
			
			depositSlotIcon = new Icon() {
				
				/**
				 * Creates a custom icon for the deposit slot button
				 */
				@Override
				public void paintIcon(Component c, Graphics g, int x, int y) {
					int rect1Width = 170;
					int rect1Height = 25;
					int arc = 10;
					int rect2Width = 140;
					int rect2Height = 12;
					int textX = x - 58;
					int textY = 70;
					String cardText = "";
					g.setColor(Color.gray);
					g.fillRoundRect(x - (rect1Width/2) , y - (rect1Height/2), rect1Width, rect1Height, arc, arc);
					g.setColor(Color.black);
					g.drawRoundRect(x - (rect1Width/2) , y - (rect1Height/2), rect1Width, rect1Height, arc, arc);
					if(depositSlotButton.isEnabled()) {
						cardText = "Click to enter Deposit";
						g.setColor(Color.GREEN);
						g.fillRoundRect(x - (rect2Width/2), y - (rect2Height/2), rect2Width, rect2Height, arc, arc);
						g.setColor(Color.black);
						g.drawRoundRect(x - (rect2Width/2), y - (rect2Height/2), rect2Width, rect2Height, arc, arc);
					} else {
						g.setColor(Color.black);
						g.fillRoundRect(x - (rect2Width/2), y - (rect2Height/2), rect2Width, rect2Height, arc, arc);
					}
					g.drawString(cardText, textX, textY);
				}
				
				@Override
				public int getIconWidth() {
					return 0;
				}
				
				@Override
				public int getIconHeight() {
					return 0;
				}
			};
			
			depositSlotButton = new JButton(depositSlotIcon);
			depositSlotButton.setEnabled(true);
			depositSlotButton.addActionListener(this);
			bottomPadding.add(depositSlotButton);
						
			return bottomPadding;
	}
	
	@Override
	/**
	 * Registers the actions listener actions for all
	 * the buttons. When a button is pressed it sets 
	 * SystemCommand with a String and a code.  The associated
	 * buttonPressed field is set to true so
	 * that the GuiController (that listens for an action) class may perform an 
	 * action associated with that button. When the GuiController is
	 * finished processing it will set the button pressed value
	 * back to false. 
	 * 
	 * 
		//		Command code Key:
		//		======================
		//		0 = 0
		//		1 = 1
		//		2 = 2
		//		3 = 3
		//		4 = 4
		//		5 = 5
		//		6 = 6
		//		7 = 7
		//		8 = 8
		//		9 = 9
		//		10 = cancel
		//		11 = enter
		//		21 = wB1
		//		22 = wb2
		//		23 = wb3
		//		24 = wb4
		//		31 = eb1
		//		32 = eb2
		//		33 = eb3
		//		34 = eb4
		//		41 = cardSlotButton
		//		42 = depositSlotButton
		
	 */
	public void actionPerformed(ActionEvent evt) {
		
		JButton testButton = (JButton)evt.getSource();
		
		if(testButton == sKey1) {
			numericButtonPressed = true;
			systemCommand = new Command("1", 1);
		} 
		else if(testButton == sKey2) {
			numericButtonPressed = true;
			systemCommand = new Command("2", 2);
		} 
		else if(testButton == sKey3) {
			numericButtonPressed = true;
			systemCommand = new Command("3", 3);
		} 
		else if(testButton == sKey4) {
			numericButtonPressed = true;
			systemCommand = new Command("4", 4);
		} 
		else if(testButton == sKey5) {
			numericButtonPressed = true;
			systemCommand = new Command("5", 5);	
		} 
		else if(testButton == sKey6) {
			numericButtonPressed = true;
			systemCommand = new Command("6", 6);
		}
		else if(testButton == sKey7) {
			numericButtonPressed = true;
			systemCommand = new Command("7", 7);
		} 
		else if(testButton == sKey8) {
			numericButtonPressed = true;
			systemCommand = new Command("8", 8);
		} 
		else if(testButton == sKey9) {
			numericButtonPressed = true;
			systemCommand = new Command("9", 9);
		} 
		else if(testButton == sKeyCancel) {
			systemCommand = new Command("cancel", 10);
			cancelButtonPressed = true;
		} 
		else if(testButton == sKey0) {
			numericButtonPressed = true;
			systemCommand = new Command("0", 0);
		} 
		else if(testButton == sKeyEnter) {
			systemCommand = new Command("enter", 11);
			enterButtonPressed = true;
		}
		else if(testButton == wButton1) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 21);
			commandButtonPressed = true;
		}
		else if(testButton == wButton2) {
			String buttonText = evt.getActionCommand();		
			systemCommand = new Command(buttonText, 22);
			commandButtonPressed = true;
		}
		else if(testButton == wButton3) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 23);
			commandButtonPressed = true;
		}
		else if(testButton == wButton4) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 24);
			commandButtonPressed = true;
		}
		else if(testButton == eButton1) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 31);
			commandButtonPressed = true;
		}
		else if(testButton == eButton2) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 32);
			commandButtonPressed = true;
		}
		else if(testButton == eButton3) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 33);
			commandButtonPressed = true;
		}
		else if(testButton == eButton4) {
			String buttonText = evt.getActionCommand();
			systemCommand = new Command(buttonText, 34);
			commandButtonPressed = true;
		} 
		else if(testButton == cardSlotButton) {
			systemCommand = new Command("Card is Inserted", 41);
			cardInserted = true;
			cardSlotButton.setEnabled(false);			
		}
		else if(testButton == depositSlotButton) {
			systemCommand = new Command("Envelope Inserted", 42);
			depositSlotButton.setEnabled(false);
			commandButtonPressed = true;
		}
	}
	
	/**
	 * Disables just the numeric keys.
	 * Used when the GuiController registers
	 * that 4 digits have been input for
	 * the PIN
	 */
	public void disableNumericKeys() {
		sKey0.setEnabled(false);
		sKey1.setEnabled(false);
		sKey2.setEnabled(false);
		sKey3.setEnabled(false);
		sKey4.setEnabled(false);
		sKey5.setEnabled(false);
		sKey6.setEnabled(false);
		sKey7.setEnabled(false);
		sKey8.setEnabled(false);
		sKey9.setEnabled(false);
	}
	
	/**
	 * Disables the whole keypad including
	 * the numeric, cancel, and enter keys
	 */
	public void disableKeyPad() {
		disableNumericKeys();
		sKeyEnter.setEnabled(false);
		sKeyCancel.setEnabled(false);
	}
	
	
	/**
	 * Enables the keypad, all the 
	 * numeric, cancel, and enter
	 * keys
	 */
	public void enableKeyPad() {
		sKey0.setEnabled(true);
		sKey1.setEnabled(true);
		sKey2.setEnabled(true);
		sKey3.setEnabled(true);
		sKey4.setEnabled(true);
		sKey5.setEnabled(true);
		sKey6.setEnabled(true);
		sKey7.setEnabled(true);
		sKey8.setEnabled(true);
		sKey9.setEnabled(true);
		sKeyEnter.setEnabled(true);
		sKeyCancel.setEnabled(true);
	}
	
	/**
	 * Disables the command buttons
	 * 4 on the west side
	 * 4 on the east side
	 * and sets the text to the size
	 * of the BUTTON_PAD so that they
	 * don't shrink
	 */
	public void disableCommandButtons() {
		
		disableWestButtons();
		wButton1.setText(BUTTON_PAD);
		wButton2.setText(BUTTON_PAD);
		wButton3.setText(BUTTON_PAD);
		wButton4.setText(BUTTON_PAD);
		
		disableEastButtons();
		eButton1.setText(BUTTON_PAD);
		eButton2.setText(BUTTON_PAD);
		eButton3.setText(BUTTON_PAD);
		eButton4.setText(BUTTON_PAD);
	}
	
	/**
	 * Disables the 4 command buttons
	 * on the East side of the display
	 */
	public void disableEastButtons() {
		eButton1.setEnabled(false);
		eButton2.setEnabled(false);
		eButton3.setEnabled(false);
		eButton4.setEnabled(false);
	}
	
	/**
	 * Disables the 4 command buttons
	 * on the West side of the display
	 */
	public void disableWestButtons() {
		wButton1.setEnabled(false);
		wButton2.setEnabled(false);
		wButton3.setEnabled(false);
		wButton4.setEnabled(false);
	}
	
	/**
	 * Enables all the command buttons
	 * 4 on the East side
	 * 4 on the West side
	 */
	public void enableCommandButtons() {
		wButton1.setEnabled(true);
		wButton2.setEnabled(true);
		wButton3.setEnabled(true);
		wButton4.setEnabled(true);
		
		eButton1.setEnabled(true);
		eButton2.setEnabled(true);
		eButton3.setEnabled(true);
		eButton4.setEnabled(true);
	}
	
	/**
	 * Disables all buttons on the
	 * entire ATM display including
	 * the West, East, keypad, cardSlot,
	 * and depositSlot buttons.
	 */
	public void disableAllButtons() {
		disableCommandButtons();
		disableKeyPad();
	}
	
	/**
	 * Sets and enables buttons and text
	 * for the Welcome screen.
	 */
	public void setWelcomeScreen() {
		disableCommandButtons();
		disableKeyPad();
		cardInserted = false;
		cardSlotButton.setEnabled(true);
		depositSlotButton.setEnabled(false);
		inputLabel.setText("");
		outputLabel.setText("");
		messageLabel1.setFont(largeMessageFont);
		messageLabel1.setText("Welcome to " + TITLE);
		messageLabel2.setFont(smallMessageFont);
		messageLabel2.setText("Please insert card");
	}
	

	/**
	 * Sets and enables the specific buttons for the 
	 * get account screen. Has a parameter for a message
	 * output depending on the method who called it.
	 * @param outputText The extra informative text
	 * to be displayed along with the default message.
	 */
	public void setGetAccountScreen(String outputText) {
		enableKeyPad();
		messageLabel2.setText("Please input account #");
		inputLabel.setText("");
		outputLabel.setText(outputText);
	}

	
	/**
	 * Sets and enables text and buttons for the 
	 * error screen (PIN error and account error)
	 */
	public void setPinErrorScreen() {
		disableCommandButtons();
		inputLabel.setText("");
		outputLabel.setText("");
		messageLabel1.setFont(errorFont);
		messageLabel1.setForeground(Color.red);
		messageLabel1.setText("Too many incorrect Entries");
		messageLabel2.setFont(errorFont);
		messageLabel2.setForeground(Color.red);
		messageLabel2.setText("Card Retained");
		outputLabel.setForeground(Color.red);
		outputLabel.setFont(errorFont);
		outputLabel.setText("Please Contact Bank Manager");
	}
	
	/**
	 * Sets and enables text and buttons for the 
	 * PIN entry screen. Includes method specific
	 * output text as a parameter
	 * @param outputText Extra informative text to be displayed 
	 * to the user. i.e. in the case of an entry error
	 */
	public void setPinScreen(String outputText) {
		disableCommandButtons();
		enableKeyPad();
		inputLabel.setText("");
		messageLabel1.setFont(largeMessageFont);
		messageLabel1.setText("Please input pin");
		messageLabel2.setText("");
		outputLabel.setText(outputText);
		//sKeyCancel.setEnabled(false);
	}
	
	/**Sets and enables the text and buttons associated
	 * with the Main selection screen including: name, account,
	 * and transaction selection
	 * @param name The name on the account that is displayed on the screen
	 * @param account The account number of the user
	 */
	public void setMainScreen(String name, String account) {
		disableCommandButtons();
		disableKeyPad();
		
		inputLabel.setText("");
		outputLabel.setFont(smallMessageFont);
		outputLabel.setText("To End Session Press Cancel");
		
		eButton1.setText("Withdraw");
		eButton2.setText("Deposit");
		eButton3.setText("Transfer");
		eButton4.setText("Balance");
		wButton1.setText("Cancel");
		wButton1.setEnabled(true);
		
		messageLabel1.setFont(smallMessageFont);
		messageLabel1.setForeground(Color.black);
		messageLabel1.setText("Welcome " + name + "    Account# " + account );
		messageLabel2.setFont(largeMessageFont);
		messageLabel2.setText("Please make a selection");
		
		wButton1.setEnabled(true);
	
		eButton1.setEnabled(true);
		eButton2.setEnabled(true);
		eButton3.setEnabled(true);
		eButton4.setEnabled(true);
	}
	
	/**Sets and enables the text and buttons associated
	 * with the account selection screen: Checking or Savings.
	 * Also takes in a parameter of transaction type i.e. withdraw,
	 * deposit, transfer, or balance.
	 * @param transactionType the transaction to be performed i.e. withdraw,
	 * deposit, transfer, or balance.
	 */
	public void setSelectAccoutScreen(Selection transactionType) {
		disableKeyPad();
		disableWestButtons();
		inputLabel.setFont(largeMessageFont);
		outputLabel.setText("Press Cancel to return to previous screen");
		messageLabel1.setFont(largeMessageFont);
		messageLabel1.setText(transactionType.toString());
		messageLabel2.setFont(smallMessageFont);
		messageLabel2.setText("Please Select an account");
		eButton1.setText(Selection.CHECKING.toString());
		eButton2.setText(Selection.SAVINGS.toString());
		eButton3.setText(BUTTON_PAD);
		eButton4.setText(BUTTON_PAD);
		wButton1.setText(Selection.CANCEL.toString());
		wButton1.setEnabled(true);
		eButton3.setEnabled(false);
		eButton4.setEnabled(false);
	}
	
	/**Sets and enables the text and buttons associated
	 * with the balance selection screen including the account type
	 * (checking or savings) and the amount of the balance.
	 * @param selection The type of account (checking or savings)
	 * @param amount The amount of the balance
	 */
	public void setBalanceScreen(Selection selection, double amount) {
		disableAllButtons();
		messageLabel1.setText("Account Balance for " + selection.toString());
		messageLabel2.setFont(largeMessageFont);
		messageLabel2.setText("$" + amount);
	}
	
	/**Sets and enables the text and buttons associated
	 * with the numeric input screen i.e. input value account type
	 * and balance of the account
	 * @param account the current account being accessed 
	 * @param balance the current balance of the account
	 */
	public void setNumericInputScreen(String account, String balance) {
		disableCommandButtons();
		enableKeyPad();
		inputLabel.setFont(largeMessageFont);
		inputLabel.setText("$0.00");
		messageLabel1.setFont(smallMessageFont);
		messageLabel1.setText(account +" " + balance);
		messageLabel2.setFont(largeMessageFont);
		messageLabel2.setText("Please input amount:");
		outputLabel.setText("");
	}
	
	/**Sets and enables the text and buttons associated
	 * with the deposit screen and enables or disables the
	 * deposit slot button depending on the state of the
	 * transaction
	 * @param isActive enables or disables the
	 * deposit slot button depending on the state of the
	 * transaction
	 */
	public void setDepositScreen(boolean isActive) {
		disableAllButtons();
		disableKeyPad();
		depositSlotButton.setEnabled(isActive);
	}
	
	/**Sets and enables the text and buttons associated
	 * with the withdraw screen i.e. preset amounts in 
	 * sets of 20 
	 */
	public void setWithdrawScreen() {
		disableAllButtons();
		wButton1.setText("$" + Selection.TWENTY.toString());
		eButton1.setText("$" + Selection.FORTY.toString());
		wButton2.setText("$" + Selection.SIXTY.toString());
		eButton2.setText("$" + Selection.EIGHTY.toString());
		wButton3.setText("$" + Selection.HUNDRED.toString());
		eButton3.setText("$" + Selection.HUNDREDTWENTY.toString());
		wButton4.setText(Selection.CANCEL.toString());
		
		wButton1.setEnabled(true);
		eButton1.setEnabled(true);
		wButton2.setEnabled(true);
		eButton2.setEnabled(true);
		wButton3.setEnabled(true);
		eButton3.setEnabled(true);
		wButton4.setEnabled(true);
		
		messageLabel1.setText("Select Amount");
	}
	
	/**Sets and enables the text and buttons associated
	 * with the ending screen 
	 */
	public void setEndScreen() {
		disableAllButtons();
		messageLabel1.setFont(smallMessageFont);
		messageLabel1.setForeground(Color.black);
		messageLabel1.setText("Thank you for using");
		
		messageLabel2.setFont(largeMessageFont);
		messageLabel2.setForeground(Color.black);
		messageLabel2.setText("Bank of CIS-225");
		
		inputLabel.setFont(smallMessageFont);
		inputLabel.setText("Press Continue to for another transaction");
		
		outputLabel.setFont(smallMessageFont);
		outputLabel.setText("Press Cancel to shutdown and print log");
		
		wButton1.setText(Selection.CANCEL.toString());
		wButton1.setEnabled(true);
		
		eButton1.setText(Selection.CONTINUE.toString());
		eButton1.setEnabled(true);
	}
	

	/**Sets and enables the text associated
	 * with a message screen
	 * @param text String of text to inform the user of
	 * transaction
	 * @param error boolean if the message is an error screen
	 * true will set the text to red, false will set the text 
	 * to green
	 */
	public void setMessageScreen(String text, boolean error) {
		disableAllButtons();
		messageLabel1.setFont(errorFont);
		if(error) {
			messageLabel1.setForeground(Color.red);
		} else {
			messageLabel1.setForeground(Color.green);
		}
		messageLabel1.setText(text);
	}
	
	
	/**Disables and removes the
	 * ATM from the screen 
	 */
	public void destroy() {
		frame.setVisible(false); 
		frame.dispose();
	}

	/**Used by the GuiController class to change the
	 * value after a button pressed action is processed
	 * @param commandButtonPressed The type of button being reset
	 */
	public void setCommandButtonPressed(boolean commandButtonPressed) {
		this.commandButtonPressed = commandButtonPressed;
	}

	/**Used by the GuiController class to change the
	 * value after a button pressed action is processed
	 * @param enterButtonPressed the enterButtonPressed to set
	 */
	public void setEnterButtonPressed(boolean enterButtonPressed) {
		this.enterButtonPressed = enterButtonPressed;
	}

	/**Used by the GuiController class to change the
	 * value after a button pressed action is processed
	 * @param cancelButtonPressed the cancelButtonPressed to set
	 */
	public void setCancelButtonPressed(boolean cancelButtonPressed) {
		this.cancelButtonPressed = cancelButtonPressed;
	}

	/**Used by the GuiController class to change the
	 * value after a button pressed action is processed
	 * @param numericButtonPressed the numericButtonPressed to set
	 */
	public void setNumericButtonPressed(boolean numericButtonPressed) {
		this.numericButtonPressed = numericButtonPressed;
	}

	
	/**Used by the GuiController class to change the
	 * text for the specific JLabel
	 * @param messageText the messageText to set
	 */
	public void setMessage1Text(String messageText) {
		messageLabel1.setText(messageText);
	}

	
	/**Used by the GuiController class to change the
	 * text for the specific JLabel
	 * @param messageText the String to be diplayed
	 */
	public void setMessage2Text(String messageText) {
		messageLabel2.setText(messageText);
	}

	/**Used by the GuiController class to change the
	 * text for the specific JLabel
	 * @param input the inputText to set
	 */
	public void setInputText(String input) {
		inputLabel.setText(input);
	}

	/**Used by the GuiController class to change the
	 * text for the specific JLabel
	 * @param input the outputText to set
	 */
	public void setOutputText(String input) {
		outputLabel.setText(input);
	}


	/**Returns the current systemCommand to the GuiController
	 * @return the current system command
	 */
	public Command getCommand() {
		return systemCommand;
	}

	/**Used by the GuiController to listen for
	 * a new command
	 * @return isCommandButtonPressed notification when a command
	 * button is pressed
	 */
	public boolean isCommandButtonPressed() {
		return commandButtonPressed;
	}

	/**Used by the GuiController to listen for
	 * new digits being input on the keypad
	 * @return the numericButtonPressed notification that a numeric
	 * key is pressed
	 */
	public boolean isNumericButtonPressed() {
		return numericButtonPressed;
	}

	/**Used by the GuiController to listen for
	 * enter button presses
	 * @return the enterButtonPressed notification that
	 * the enter key is pressed
	 */
	public boolean isEnterButtonPressed() {
		return enterButtonPressed;
	}

	/**Used by the GuiController to listen for
	 * cancel button presses
	 * @return the cancelButtonPressed notification that
	 * the cancel key is pressed
	 */
	public boolean isCancelButtonPressed() {
		return cancelButtonPressed;
	}

	/**Used by the GuiController to listen for
	 * the card being inserted
	 * @return the cardInserted notification that
	 * the card is inserted
	 */
	public boolean isCardInserted() {
		return cardInserted;
	}
}
