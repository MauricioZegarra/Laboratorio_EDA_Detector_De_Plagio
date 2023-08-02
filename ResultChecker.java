import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultChecker {
    private boolean[] result;
    private String[] type;
    private int count;

    public void newSession(int n, String sospechoso, List <String> BD) {
        result = new boolean[n];
        type = new String[n];
        count = 0;
        for (String original : BD) {
            compare(sospechoso, original);
        }
    }

    private void compare(String sospechoso, String original) {
        String originalText = original.toLowerCase();
        String suspiciousText = sospechoso.toLowerCase();

        // Calcular el índice de similitud de Jaccard entre los documentos
        double jaccardSimilarity = calculateJaccardSimilarity(originalText, suspiciousText);

        // Mostrar el resultado del análisis en una ventana de diálogo
        if (jaccardSimilarity < 0.2) {
            this.type[count] = "nulo";
            this.result[count] = false;
        } 
        else if(jaccardSimilarity < 0.9) {
            this.type[count] = "parcial";
            this.result[count] = true;
        }
        else {
            this.type[count] = "total";
            this.result[count] = true;
        }
        
        count ++;
    
    }

    private double calculateJaccardSimilarity(String text1, String text2) {
        Set<String> wordsSet1 = new HashSet<>(Arrays.asList(text1.split("\\W+")));
        Set<String> wordsSet2 = new HashSet<>(Arrays.asList(text2.split("\\W+")));

        // Calcular el tamaño de la intersección entre los conjuntos de palabras
        Set<String> intersection = new HashSet<>(wordsSet1);
        intersection.retainAll(wordsSet2);

        // Calcular el tamaño de la unión entre los conjuntos de palabras
        Set<String> union = new HashSet<>(wordsSet1);
        union.addAll(wordsSet2);

        // Calcular el índice de similitud de Jaccard
        double jaccardSimilarity = (double) intersection.size() / union.size();
        return jaccardSimilarity;
    }

    public JFrame getResults() {
        //ventana con los resultados de la última comparación.
        JFrame panel = new JFrame();

        // Comprobar si hay resultados para mostrar
        if (result == null || type == null || result.length == 0 || type.length == 0 || result.length != type.length) {
            JLabel noResultsLabel = new JLabel("No hay resultados por mostar.");
            panel.add(noResultsLabel);
            return panel;
        }

         // Crear una matriz para almacenar los datos de la tabla
         Object[][] tableData = new Object[result.length][2];

         // Llenar la matriz con los datos de los arreglos result y type
         for (int i = 0; i < result.length; i++) {
             tableData[i][0] = result[i];
             tableData[i][1] = type[i];
         }
 
         // Etiquetas para las columnas de la tabla
         String[] columnNames = {"Result", "Type"};
 
         // Crear el modelo de tabla con los datos y etiquetas de columna
         DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
 
         // Crear la tabla con el modelo
         JTable table = new JTable(tableModel);
 
         // Agregar la tabla a un JScrollPane para que sea desplazable si tiene muchos datos
         JScrollPane scrollPane = new JScrollPane(table);
 
         // Agregar el JScrollPane al panel
         panel.add(scrollPane);
 
         return panel;

    }
}
