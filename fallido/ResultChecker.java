package fallido;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultChecker extends JFrame {

    private final boolean[] result;
    private String[] nombres;
    
    public ResultChecker(int n) {
        this.result = new boolean[n];
        this.nombres = new String[n];
        build();
    }
    
    public void setResult(int i, String nombre, boolean result) {
        this.nombres[i] = nombre;
        this.result[i] = result;
    }

    private boolean build() {
        
        this.setTitle("RESULT");
        this.setSize(400,400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        // Comprobar si hay resultados para mostrar
        if (result == null || result.length == 0) {
            JLabel noResultsLabel = new JLabel("No hay resultados por mostar.");
            this.add(noResultsLabel);
            this.setVisible(true);
            return false;
        }

        // Crear una matriz para almacenar los datos de la tabla
        Object[][] tableData = new Object[result.length][2];

        // Llenar la matriz con los datos de los arreglos result y type
        for (int i = 0; i < result.length; i++) {
            tableData[i][0] = nombres[i];
            tableData[i][1] = result[i];
        }

        // Etiquetas para las columnas de la tabla
        String[] columnNames = {"Nombre", "Result"};

        // Crear el modelo de tabla con los datos y etiquetas de columna
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);

        // Crear la tabla con el modelo
        JTable table = new JTable(tableModel);

        // Agregar la tabla a un JScrollPane para que sea desplazable si tiene muchos datos
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregar el JScrollPane al panel
        this.add(scrollPane);

        this.setVisible(true);
        
        return true;
    }
    
}
