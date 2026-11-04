
package calculator.operations;

public class AdvancedOperations {

    public double power(double a, double b) {
        return Math.pow(a, b);
    }

    public double log(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException(
                    "Ο λογάριθμος απαιτεί θετικό αριθμό."
            );
        }
        return Math.log10(a);
    }

    public double ln(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException(
                    "Ο φυσικός λογάριθμος απαιτεί θετικό αριθμό."
            );
        }
        return Math.log(a);
    }

    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException(
                    "Δεν επιτρέπεται τετραγωνική ρίζα αρνητικού αριθμού."
            );
        }
        return Math.sqrt(a);
    }

    public double percent(double a) {
        return a / 100.0;
    }

    public double percentOf(double a, double b) {
        return (a * b) / 100.0;
    }

    public double addPercentage(double a, double b) {
        return a + (a * b) / 100.0;
    }

    public double expo(double a) {
        return Math.exp(a);
    }
}
