import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import net.miginfocom.swing.MigLayout;


public class Calculator extends JFrame {
	
	// These items will be used within both the main class and
	// its inner classes so they need to be declared globally
	
	// The display at the top of the application
	JTextField display;
	
	// Two stacks - the first to hold operators (+, -, etc.),
	// the second to hold the operands (numbers)
	Stack<String> operatorStack;
	Stack<Double> operandStack;
	
	// Flag used to indicate when a fresh value is to be entered
	// after an operator key has been pressed
	boolean cleared = false;
	
	// Flag used to allow decimal points to only be displayed once
	// the following digit has been typed in
	boolean waitingOnDecimal = false;

    public Calculator() {

    	setTitle("Calculator");

    	MigLayout layout = new MigLayout("fillx");
    	JPanel panel = new JPanel(layout);
    	getContentPane().add(panel);

    	display = new JTextField("0");
    	display.setHorizontalAlignment(JTextField.RIGHT);
    	display.setEditable(false);
    	panel.add(display, "span, growx");
    	JButton seven = new JButton("7");
    	JButton eight = new JButton("8");
    	JButton nine = new JButton("9");
    	JButton divide = new JButton("/");
    	JButton clear = new JButton("C");
    	panel.add(seven, "");
    	panel.add(eight, "");
    	panel.add(nine, "");
    	panel.add(divide, "");
    	panel.add(clear, "span 2 1, growy, wrap");
    	JButton four = new JButton("4");
    	JButton five = new JButton("5");
    	JButton six = new JButton("6");
    	JButton multiply = new JButton("*");
    	panel.add(four, "");
    	panel.add(five, "");
    	panel.add(six, "");
    	panel.add(multiply, "wrap");
    	JButton one = new JButton("1");
    	JButton two = new JButton("2");
    	JButton three = new JButton("3");
    	JButton subtract = new JButton("-");
    	JButton evaluate = new JButton("=");
    	panel.add(one, "");
    	panel.add(two, "");
    	panel.add(three, "");
    	panel.add(subtract, "");
    	panel.add(evaluate, "span 2 1, growy, wrap");
    	JButton zero = new JButton("0");
    	JButton decimal = new JButton(".");
    	JButton add = new JButton("+");
    	panel.add(zero, "span 2");
    	panel.add(decimal, "");
    	panel.add(add, "");
    	
    	// Because all the number buttons behave in the same way, it
    	// makes sense to group their event handling routines together
    	// into a single actionPerformed() method in an inner class
    	NumberButtonListener numberList = new NumberButtonListener();
    	
    	one.addActionListener(numberList);
    	two.addActionListener(numberList);
    	three.addActionListener(numberList);
    	four.addActionListener(numberList);
    	five.addActionListener(numberList);
    	six.addActionListener(numberList);
    	seven.addActionListener(numberList);
    	eight.addActionListener(numberList);
    	nine.addActionListener(numberList);
    	zero.addActionListener(numberList);
    	
    	// Because the behaviour associated with the decimal point
    	// button is unique to that button, then we can implement
    	// its action listener as an anonymous inner class
    	decimal.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			// Checks to see if the string already
    			// contains a decimal point - if it does,
    			// no change is made
        		if (display.getText().contains(".")) 
        			;
        		else {
        			// Flag set to true
        			waitingOnDecimal = true;
        		}
        	}
    	});
    	
    	// Again, because the behaviour associated with the clear
    	// button is unique, we can implement its action listener
    	// as an anonymous inner class
    	clear.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			// resets display to "0"
        		display.setText("0");
        	}
    	});
    	
    	// Because all the operator buttons behave in a similar way,
    	// we can group their event handling routines together into a
    	// single method in an inner class
    	OperatorButtonListener opList = new OperatorButtonListener();
    	
    	add.addActionListener(opList);
    	subtract.addActionListener(opList);
    	multiply.addActionListener(opList);
    	divide.addActionListener(opList);
    	
    	evaluate.addActionListener(opList);
    	
    	// We initialise the stacks
    	operandStack = new Stack<Double>();
    	operatorStack = new Stack<String>();
    	
    	// Define the size and behaviour of the application window
    	setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    class NumberButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		// This will hold the character representing the number pressed
    		String charToAdd = e.getActionCommand();
    		// If the display is currently empty...
    		if (display.getText().equals("0")) {
    			// If the decimal point has been pressed, we will display
    			// "0." and whatever number was pressed
    			if (waitingOnDecimal) {
    				display.setText(display.getText() + "." + charToAdd);
    				// Reset flag to false
    				waitingOnDecimal = false;
    			}
    			else
    				// Replace the "0" with the character typed in
    				display.setText(charToAdd);
    		}
    		// If the display currently contains digits
    		else {
    			// Check to see if an operator key has just been pressed
    			// If it has, then the number typed in will be the start
    			// of a new operand and should replace the existing display
    			if (cleared) {
    				if (waitingOnDecimal) {
    					// If the decimal point has been pressed after the
    					// operator key, then our value will be of the 
    					// form "0." followed by the remaining numbers
    					display.setText("0." + charToAdd);
    					// Reset flag to false
    					waitingOnDecimal = false;
    				}
    				else
    					// If the decimal point hasn't been pressed, then
    					// we simply replace the display with the number
    					// pressed
    					display.setText(charToAdd);
    				// We're now into our new operand, so we reset the cleared
    				// flag
    				cleared = false;
    			}
    			// If cleared is false, then the digits in the display 
    			// represent the current operand and should be added to
    			else
    				// If a decimal point has been pressed, then it should
    				// be inserted into the string before the number
    				if (waitingOnDecimal) {
    					display.setText(display.getText() + "." + charToAdd);
    					waitingOnDecimal = false;
    				}
    				// Add the number to the display
    				else
    					display.setText(display.getText() + charToAdd);
    		}
    	}	
    }
        

    class OperatorButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		// Pressing an operator key means that the value in the display
    		// needs to be converted into a number
    		Double parsedValue = Double.parseDouble(display.getText());
    		// This will hold the result of a calculation
    		Double result = new Double(0);
    		// We push the parsed display contents onto the operand stack
    		operandStack.push(parsedValue);
    		// If there is already an entry on the operator stack, then it
    		// represents the current mathematical operator (because we're
    		// using infix notation) and we need to calculate the value of
    		// this earlier part of the expression
    		if (!operatorStack.isEmpty()) {
    			// Because operations such as subtraction and division
    			// aren't commutative, we need to take account of which
    			// is the first operand and which is the second - the older
    			// value will be further down on the stack
    			Double secondOperand = operandStack.pop();
    			Double firstOperand = operandStack.pop();
    			// We get the first character of the operator because a
    			// switch statement requires a type with a finite range of
    			// values - there are an infinite number of strings so we
    			// can't use a string as the parameter for the switch
    			// statement
    			char operator = operatorStack.pop().charAt(0);
    			// Calculate result given operator and values
    			switch (operator) {
    				case '+':
    					result = firstOperand + secondOperand;
    					break;
    				case '-':
    					result = firstOperand - secondOperand;
    					break;
    				case '*':
    					result = firstOperand * secondOperand;
    					break;
    				case '/':
    					result = firstOperand / secondOperand;
    					break;
    			}
    			// Push the result back onto the stack
    			operandStack.push(result);
    			// Update the display
    			display.setText(result.toString());
    		}
    		// If there's no operator on the stack, we're not calculating a
    		// result, so we just update the display
    		else {
    			display.setText(parsedValue.toString());
    		}
    		// The operator stack should only contain +, -, * and /
    		// The = operator is not pushed onto the stack
    		String newOperator = e.getActionCommand();
    		if (!newOperator.equals("=")) {
    			operatorStack.push(newOperator);
    		}
    		// Set the flag indicating that subsequent digits represent a new
    		// operand
    		cleared = true;
    	}
    }

    public static void main(String[] args) {

        Calculator calc = new Calculator();
        calc.setVisible(true);

    }
}
