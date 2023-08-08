import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultChecker implements design {

    private boolean[] result;
    private String[] type;
    private int count;

    public void newSession(int n, String suspicious, List<String> database) {
        result = new boolean[n];
        type = new String[n];
        count = 0;
        for (String original : database) {
            compare(suspicious, original);
        }
    }

    private void compare(String suspicious, String original) {
        String originalText = original.toLowerCase();
        String suspiciousText = suspicious.toLowerCase();

        double jaccardSimilarity = calculateJaccardSimilarity(originalText, suspiciousText);

        if (jaccardSimilarity < 0.2) {
            this.type[count] = "Nulo";
            this.result[count] = false;
        } else if (jaccardSimilarity < 0.9) {
            this.type[count] = "Parcial";
            this.result[count] = true;
        } else {
            this.type[count] = "Total";
            this.result[count] = true;
        }

        count++;
    }

    private double calculateJaccardSimilarity(String text1, String text2) {
        Set<String> wordsSet1 = new HashSet<>(Arrays.asList(text1.split("\\W+")));
        Set<String> wordsSet2 = new HashSet<>(Arrays.asList(text2.split("\\W+")));

        Set<String> intersection = new HashSet<>(wordsSet1);
        intersection.retainAll(wordsSet2);

        Set<String> union = new HashSet<>(wordsSet1);
        union.addAll(wordsSet2);

        double jaccardSimilarity = (double) intersection.size() / union.size();
        return jaccardSimilarity;
    }

    public JFrame getResults() {
        JFrame panel = new JFrame("Resultados de Plagio");
        panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setSize(500, 400);
        panel.setLocationRelativeTo(null);

        if (result == null || type == null || result.length == 0 || type.length == 0 || result.length != type.length) {
            JLabel noResultsLabel = new JLabel("No hay resultados para mostrar.");
            noResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noResultsLabel.setFont(tableFont);
            panel.add(noResultsLabel);
            return panel;
        }

        Object[][] tableData = new Object[result.length][3];

        for (int i = 0; i < result.length; i++) {
            tableData[i][0] = i + 1; // Index
            tableData[i][1] = result[i] ? "Sí" : "No";
            tableData[i][2] = type[i];
        }

        String[] columnNames = { "Índice", "Plagio", "Tipo" };

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);

        JTable table = new JTable(tableModel);
        table.getTableHeader().setFont(tableFont);
        table.setFont(tableFont);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(480, 320));

        panel.add(scrollPane);

        return panel;
    }
}
