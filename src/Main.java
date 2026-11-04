package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalculatorUI {
    private JFrame frame;
    private JTextField display;
    private JPanel buttonPanel;

    public CalculatorUI() {
        frame = new JFrame("Calculator Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        display = new JTextField();
        display.setFont(new Font("SansSerif", Font.BOLD, 40));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(display, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 8, 8));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));

        // ΑΛΛΑΓΗ: Βάλαμε το "Hist" στη θέση του κενού
        String[] buttons = {
                "√", "x^y", "log", "C",
                "ln", "e^x", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "History", "0", ".", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            // Μικρύναμε λίγο τη γραμματοσειρά για να χωράει ωραία το "Hist"
            button.setFont(new Font("SansSerif", Font.BOLD, text.equals("History") ? 18 : 22));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder());

            // Χρωματισμός κουμπιών
            if (text.matches("[0-9]") || text.equals(".")) {
                button.setBackground(new Color(75, 75, 75));
                button.setForeground(Color.WHITE);
            } else if (text.equals("C")) {
                button.setBackground(new Color(212, 63, 58));
                button.setForeground(Color.WHITE);
            } else if (text.equals("=")) {
                button.setBackground(new Color(40, 167, 69));
                button.setForeground(Color.WHITE);
            } else if (text.matches("[+\\-*/]")) {
                button.setBackground(new Color(255, 149, 0));
                button.setForeground(Color.WHITE);
            } else if (text.equals("History")) {
                button.setBackground(new Color(23, 162, 184)); // Μπλε/Τιρκουάζ χρώμα για το History
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(100, 100, 100));
                button.setForeground(Color.WHITE);
            }

            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    public void showUI() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setDisplayText(String text) {
        display.setText(text);
    }

    public String getDisplayText() {
        return display.getText();
    }
    // Επιτρέπει στον Controller να βάλει "αυτιά" σε όλα τα κουμπιά του πάνελ
    public void addButtonListener(java.awt.event.ActionListener listener) {
        for (java.awt.Component c : buttonPanel.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(listener);
            }
        }
    }

    // ΝΕΑ ΜΕΘΟΔΟΣ: Εμφανίζει το pop-up με το ιστορικό
    public void showHistoryDialog(List<String> historyList) {
        JDialog historyDialog = new JDialog(frame, "Ιστορικό Υπολογισμών", true);
        historyDialog.setSize(300, 400);
        historyDialog.setLocationRelativeTo(frame);
        historyDialog.getContentPane().setBackground(new Color(40, 40, 40));

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(40, 40, 40));
        historyArea.setForeground(Color.WHITE);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyArea.setMargin(new Insets(10, 10, 10, 10));

        // Αν το ιστορικό είναι άδειο, βγάζει μήνυμα, αλλιώς τυπώνει τη λίστα
        if (historyList == null || historyList.isEmpty()) {
            historyArea.setText("Το ιστορικό είναι άδειο.");
        } else {
            for (String record : historyList) {
                historyArea.append(record + "\n");
            }
        }

        // Προσθέτουμε scrollbar σε περίπτωση που οι υπολογισμοί είναι πολλοί
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        historyDialog.add(scrollPane);

        historyDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorUI ui = new CalculatorUI();
            ui.showUI();
            ui.setDisplayText("0");

            // Παράδειγμα για το πώς θα φαίνεται το Ιστορικό (θα το καλεί ο Controller αυτό αργότερα)
            // ui.showHistoryDialog(java.util.Arrays.asList("5 + 5 = 10", "10 * 2 = 20"));
        });
    }
}