package calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


/**
 * Υπολογίζει αριθμητικές παραστάσεις με τη σωστή σειρά πράξεων.
 * Προτεραιότητα: παρενθέσεις > πολλαπλασιασμός/διαίρεση > πρόσθεση/αφαίρεση.
 *
 * Χρήση: double apotelesma = ExpressionEvaluator.evaluate("2 + 3 * 4"); // 14.0
 *
 * Επιτρέπονται: + - * /, παρενθέσεις, δεκαδικοί (2.5) και αρνητικοί αριθμοί (-5).
 * Εξαιρέσεις:
 *  - IllegalArgumentException: λάθος παράσταση ή άγνωστος χαρακτήρας
 *  - ArithmeticException: διαίρεση με το μηδέν
 */
public class ExpressionEvaluator {

    // Η μόνη μέθοδος που χρειάζονται οι υπόλοιποι
    public static double evaluate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Κενή παράσταση");
        }
        List<String> tokens = tokenize(expression);
        List<String> postfix = toPostfix(tokens);
        return evalPostfix(postfix);
    }

    // Σπάει το κείμενο σε κομμάτια (αριθμοί, τελεστές, παρενθέσεις)
    private static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                number.append(c);
                continue;
            }

            // Τελειώνει ο αριθμός που διαβάζαμε
            if (number.length() > 0) {
                if (number.toString().equals("-")) {
                    throw new IllegalArgumentException("Λάθος παράσταση");
                }
                tokens.add(number.toString());
                number.setLength(0);
            }

            boolean prosimoMeion = c == '-' && (tokens.isEmpty()
                    || tokens.get(tokens.size() - 1).equals("(")
                    || isOperator(tokens.get(tokens.size() - 1)));

            if (prosimoMeion) {
                number.append('-'); // αρνητικός αριθμός
            } else if (isOperator(String.valueOf(c)) || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
            } else {
                throw new IllegalArgumentException("Άγνωστος χαρακτήρας: " + c);
            }
        }

        if (number.length() > 0) {
            if (number.toString().equals("-")) {
                throw new IllegalArgumentException("Λάθος παράσταση");
            }
            tokens.add(number.toString());
        }
        return tokens;
    }

    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    // Προτεραιότητα: * και / = 2, + και - = 1
    private static int precedence(String op) {
        switch (op) {
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 0;
        }
    }

    // Αλγόριθμος Shunting-yard: μετατροπή σε postfix (π.χ. 2 3 4 * +)
    private static List<String> toPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())
                        && precedence(stack.peek()) >= precedence(token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Μη ισορροπημένες παρενθέσεις");
                }
                stack.pop(); // αφαιρεί την '('
            } else {
                output.add(token); // αριθμός
            }
        }

        while (!stack.isEmpty()) {
            String op = stack.pop();
            if (op.equals("(")) {
                throw new IllegalArgumentException("Μη ισορροπημένες παρενθέσεις");
            }
            output.add(op);
        }
        return output;
    }

    // Υπολογισμός της postfix μορφής
    private static double evalPostfix(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Λάθος παράσταση");
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("Διαίρεση με το μηδέν");
                        }
                        stack.push(a / b);
                        break;
                }
            } else {
                stack.push(Double.parseDouble(token));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Λάθος παράσταση");
        }
        return stack.pop();
    }
}