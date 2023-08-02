import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class PlagiarismChecker {
    private String[] paths;
    private String suspicius;
    private ArrayList<String> BD;

    public PlagiarismChecker() {
        // Constructor
        BD = new ArrayList<>();
    }

    public void readFiles(String[] paths) {
        for (String path : paths) {
            try {
                // Hace lectura del archivo en formato .txt, .docx y .pdf
                byte[] fileContent = Files.readAllBytes(Paths.get(path));

                // Convierte el contenido del archivo a una cadena
                String content = new String(fileContent);
                BD.add(content);

            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + path);
                e.printStackTrace();
            }
        }
    }

    public void uploadFile() {
        // Permite subir un archivo del ordenador y lo almacena en suspicius
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filename = selectedFile.getName();

            try {
                byte[] fileContent = Files.readAllBytes(selectedFile.toPath());

                suspicius = new String(fileContent);

                System.out.println("Archivo '" + filename + "' subido exitosamente.");
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + filename);
                e.printStackTrace();
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }

    }

    private void build() {
        // 6UI (No está claro qué debe hacer exactamente, es necesario completar este
        // método)
    }

    public static void main(String[] args) {
        String[] paths = { "archivo1.txt", "archivo2.txt" };//Aca colocar direccion de los archivos
        PlagiarismChecker checker = new PlagiarismChecker();

        checker.readFiles(paths);

        // Completa el método uploadFile
        checker.uploadFile();

        System.out.print(checker.BD.get(0) + checker.BD.get(1) + "  :::::" + checker.suspicius);
    }
}