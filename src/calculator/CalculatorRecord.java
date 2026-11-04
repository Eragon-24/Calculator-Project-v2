package calculator.history;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CalculationRecord {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SEPARATOR = " | ";

    private final String expression;
    private final double result;
    private final LocalDateTime time;

    public CalculationRecord(String expression, double result) {
        this(expression, result, LocalDateTime.now());
    }

    public CalculationRecord(String expression, double result, LocalDateTime time) {
        this.expression = expression;
        this.result = result;
        this.time = time;
    }

    public String getExpression() {
        return expression;
    }

    public double getResult() {
        return result;
    }

    public LocalDateTime getTime() {
        return time;
    }


    public String toFileLine() {
        return time.format(FORMAT) + SEPARATOR + expression + " = " + result;
    }


    public static CalculationRecord fromFileLine(String line) {
        try {
            int sep = line.indexOf(SEPARATOR);
            int eq = line.lastIndexOf(" = ");
            LocalDateTime time = LocalDateTime.parse(line.substring(0, sep), FORMAT);
            String expression = line.substring(sep + SEPARATOR.length(), eq);
            double result = Double.parseDouble(line.substring(eq + 3));
            return new CalculationRecord(expression, result, time);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return toFileLine();
    }
}