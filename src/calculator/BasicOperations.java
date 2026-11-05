package calculator;

public class BasicOperations {
    public static double add(double a, double b) { return a + b; }
    public static double subtract(double a, double b) { return a - b; }
    public static double multiply(double a, double b) { return a * b; }
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return a / b;
    }

    public static void main(String[] args) {
        System.out.println("Add: " + add(12, 3));
        System.out.println("Subtract: " + subtract(12, 3));
        System.out.println("Multiply: " + multiply(12, 3));
        try {
            System.out.println("Divide: " + divide(12, 3));
            System.out.println("Divide by zero: " + divide(12, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}