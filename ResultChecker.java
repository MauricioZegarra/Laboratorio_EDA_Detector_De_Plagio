import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ResultChecker {
    boolean[] result;
    String[] type;

    public ResultChecker() {
        //constructor básico
    }

    public ResultChecker(String sospechoso, String[] paths) {
        //llama a newSession
    }

    private void clean() {
        //declara como nulos los atributos.
    }

    public void newSession(String sospechoso, String[] paths) {
        //llama a clean
        //crea los objetos, result y type debe tener la misma longitud que paths
        //hará tantas pasadas como la longitud de path y en cada pasada llamaa compare y además declara true o false en result y el tipo segun el método compare.
    }

    private String compare(String sospechoso, String original) {
        //hace la comparación y devuelve nulo, parcial o total.
    }

    public JPanel getResults() {
        //ventana con los resultados de la última comparación.
        JPanel panel = new JPanel();

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
