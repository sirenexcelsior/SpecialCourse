import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Calculator {
    private static final Set<Character> OPERATORS = new HashSet<>() {{
        add('+');
        add('-');
        add('*');
        add('/');
    }};

    public float evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        List<String> postfix = infixToPostfix(tokens);
        return evaluatePostfix(postfix);
    }

    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                numberBuffer.append(c);
            } else if (OPERATORS.contains(c) || c == '(' || c == ')') {
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer = new StringBuilder();
                }
                tokens.add(Character.toString(c));
            }
        }

        if (numberBuffer.length() > 0) {
            tokens.add(numberBuffer.toString());
        }

        return tokens;
    }

    private List<String> infixToPostfix(List<String> tokens) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                postfix.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                stack.pop(); // Pop the '('
            } else if (OPERATORS.contains(token.charAt(0))) {
                while (!stack.isEmpty() && getPrecedence(token) <= getPrecedence(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int getPrecedence(String op) {
        if (op.equals("+") || op.equals("-")) {
            return 1;
        } else if (op.equals("*") || op.equals("/")) {
            return 2;
        }
        return -1;
    }

    private float evaluatePostfix(List<String> postfix) {
        Stack<Float> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumeric(token)) {
                stack.push(Float.parseFloat(token));
            } else {
                float secondOperand = stack.pop();
                float firstOperand = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(firstOperand + secondOperand);
                        break;
                    case "-":
                        stack.push(firstOperand - secondOperand);
                        break;
                    case "*":
                        stack.push(firstOperand * secondOperand);
                        break;
                    case "/":
                        if (secondOperand == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                        stack.push(firstOperand / secondOperand);
                        break;
                }
            }
        }

        return stack.pop();
    }
}
