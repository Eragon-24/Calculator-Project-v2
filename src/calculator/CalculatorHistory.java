package calculator.history;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class CalculationHistory {

    private final String fileName;
    private final List<CalculationRecord> records = new ArrayList<>();

    public CalculationHistory() {
        this("history.txt");
    }

    public CalculationHistory(String fileName) {
        this.fileName = fileName;
        load();
    }


    public void add(String expression, double result) {
        CalculationRecord record = new CalculationRecord(expression, result);
        records.add(record);
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(record.toFileLine());
        } catch (IOException e) {
            System.out.println("Δεν μπόρεσα να γράψω στο " + fileName + ": " + e.getMessage());
        }
    }


    public List<CalculationRecord> getAll() {
        return new ArrayList<>(records);
    }


    public CalculationRecord getLast() {
        return records.isEmpty() ? null : records.get(records.size() - 1);
    }

    public int size() {
        return records.size();
    }


    public void print() {
        if (records.isEmpty()) {
            System.out.println("Το ιστορικό είναι άδειο.");
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            System.out.println((i + 1) + ". " + records.get(i));
        }
    }


    public void clear() {
        records.clear();
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
        } catch (IOException e) {
            System.out.println("Δεν μπόρεσα να αδειάσω το " + fileName + ": " + e.getMessage());
        }
    }


    private void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = in.readLine()) != null) {
                CalculationRecord record = CalculationRecord.fromFileLine(line);
                if (record != null) {
                    records.add(record);
                }
            }
        } catch (IOException e) {

        }
    }
}