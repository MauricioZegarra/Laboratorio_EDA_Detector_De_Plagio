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

    }
}
