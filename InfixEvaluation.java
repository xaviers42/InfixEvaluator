import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author Xavier Sherman
 *
 */
public class InfixEvaluation {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		Scanner inputFile = new Scanner(new File("testCases.txt"));

		while (inputFile.hasNextLine()) {

			System.out.println(evaluateInfix(inputFile.nextLine()));

		}

		inputFile.close();
	}

	public static double evaluateInfix(String expression) {

		System.out.println(expression);

		Stack<String> operatorStack = new Stack<String>();
		Stack<Double> valueStack = new Stack<Double>();
		StringTokenizer tokenizer = new StringTokenizer(expression, " ");
		String[] operators = { "+", "-", "*", "/", "^" };
		int tokenPrecedence = 0;
		int operatorPrecedence = 0;
		int tokenOperationIndex = 0;

		while (tokenizer.hasMoreTokens()) {

			int index = 0;
			String nextCharacter = tokenizer.nextToken();

			boolean found = false;

			if (nextCharacter.matches("[-+]?[0-9]*\\.?[0-9]+")) {
				valueStack.push(Double.valueOf(nextCharacter));
			}

			if (nextCharacter.equals("^")) {
				if (!operatorStack.isEmpty()) {
					if (nextCharacter.equals(operatorStack.peek())) {

						System.out.println(operatorStack + ": operatorStack when nextCharacter == operatorStack.peek");
						String topOperator = operatorStack.pop();
						double operandTwo = valueStack.pop();
						double operandOne = valueStack.pop();
						valueStack.push(Math.pow(operandOne, operandTwo));
						operatorStack.push(nextCharacter);

					} else
						operatorStack.push(nextCharacter);
				} else
					operatorStack.push(nextCharacter);
			}

			if (nextCharacter.equals("+") || nextCharacter.equals("-") || nextCharacter.equals("*")
					|| nextCharacter.equals("/")) {

				while (!found && (index < operators.length)) {

					// Finding the precedence of nextCharacter
					if (operators[index].equals(nextCharacter)) {
						found = true;
						if (index == 0) {
							tokenPrecedence = 1;
							tokenOperationIndex = 0;
						}
						if (index == 1) {
							tokenPrecedence = 1;
							tokenOperationIndex = 1;
						}
						if (index == 2) {
							tokenPrecedence = 2;
							tokenOperationIndex = 2;
						}
						if (index == 3) {
							tokenPrecedence = 2;
							tokenOperationIndex = 3;
						}
					}

					if (!operatorStack.isEmpty()) {


						// Finding the precedence of operator
						if (operators[index].equals(operatorStack.peek())) {

							if (index == 0) {
								operatorPrecedence = 1;
							}
							if (index == 1) {
								operatorPrecedence = 1;
							}
							if (index == 2) {
								operatorPrecedence = 2;
							}
							if (index == 3) {
								operatorPrecedence = 2;
							}
							if (index == 4) {
								operatorPrecedence = 3;
							}

							index++;

						} else if (operatorStack.peek().equals("(")) {

							operatorPrecedence = 0;
							index++;

						} else {

							index++;
							found = false;

						}

					} else
						index++;
				}

				while (!operatorStack.isEmpty() && (tokenPrecedence <= operatorPrecedence)) {

					String topOperator = operatorStack.pop();
					double operandTwo = valueStack.pop();
					double operandOne = valueStack.pop();
					double result = 0;


					if (topOperator.equals("+")) {
						result = operandOne + operandTwo;
						operatorStack.push(nextCharacter);
					}
					else if (topOperator.equals("-")) {
						result = operandOne - operandTwo;
						operatorStack.push(nextCharacter);
					}
					else if (topOperator.equals("*")) {
						result = operandOne * operandTwo;
						operatorStack.push(nextCharacter);
					}
					else if (topOperator.equals("/")) {
						result = operandOne / operandTwo;
						operatorStack.push(nextCharacter);
					}
					else if (topOperator.equals("^")) {
						result = Math.pow(operandOne, operandTwo);
						operatorStack.push(nextCharacter);
					}

					valueStack.push(result);

				}

			}

			if (nextCharacter.equals("(")) {
				operatorStack.push(nextCharacter);
			}

			if (nextCharacter.equals(")")) {

				String topOperator = operatorStack.pop();
				while (!topOperator.equals("(")) {
					double operandTwo = valueStack.pop();
					double operandOne = valueStack.pop();
					double result = 0;

					if (topOperator.equals("+"))
						result = operandOne + operandTwo;
					else if (topOperator.equals("-"))
						result = operandOne - operandTwo;
					else if (topOperator.equals("*"))
						result = operandOne * operandTwo;
					else if (topOperator.equals("/"))
						result = operandOne / operandTwo;
					else if (topOperator.equals("^"))
						result = Math.pow(operandOne, operandTwo);

					valueStack.push(result);
					index = 0;
					topOperator = operatorStack.pop();

				}

			}

		}

		while (!operatorStack.isEmpty()) {
			String topOperator = operatorStack.pop();
			double operandTwo = valueStack.pop();
			double operandOne = valueStack.pop();

			double result = 0;

			if (topOperator.equals("+"))
				result = operandOne + operandTwo;
			else if (topOperator.equals("-"))
				result = operandOne - operandTwo;
			else if (topOperator.equals("*"))
				result = operandOne * operandTwo;
			else if (topOperator.equals("/"))
				result = operandOne / operandTwo;
			else if (topOperator.equals("^"))
				result = Math.pow(operandOne, operandTwo);

			valueStack.push(result);

		}
		return valueStack.peek();

	}

}
