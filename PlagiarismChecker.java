import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlagiarismChecker extends JFrame {

    private JLabel title = new JLabel("Detector de Plagio:");
    private JLabel text = new JLabel("Haga click en el botón seleccionar archivo y espere a que se muestre una ventana con los resultados.");
    private ResultChecker result;
    
    private String[] paths;
    private String suspicius;
    private ArrayList<String> BD;

    public PlagiarismChecker() {
        // Constructor
        BD = new ArrayList<>();
        build();
    }

    public void readFiles(String[] paths) {
        int i = 0;
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
                result = new ResultChecker();
                result.newSession(this.paths.length, suspicius, BD);
                verifyPlagiarism().setVisible(true);
            } 
            catch (IOException e) {
                System.err.println("Error al leer el archivo: " + filename);
                e.printStackTrace();
            }
        } 
        else {
            System.out.println("No se seleccionó ningún archivo.");
        }

    }

    private void build() {
        setTitle("Plagiarism Detector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Crear componentes de la GUI
        
        JButton forUpload = new JButton("Subir un archivo");

        // Definir el diseño de la GUI
        setLayout(new BorderLayout());
        
        add(this.title, BorderLayout.NORTH);
        add(this.text, BorderLayout.CENTER);
        add(forUpload, BorderLayout.SOUTH);

        // Agregar el ActionListener para el botón "Detectar Plagio"
        forUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });

    }
    
    public JFrame verifyPlagiarism() {
        return this.result.getResults();
    }

    public static void main(String[] args) {
        String[] paths = { "BD/article 1.pdf", "BD/article 2.pdf", "BD/article 3.pdf", "BD/article 4.pdf", "BD/article 5.pdf", "BD/article 6.pdf", "BD/article 7.pdf" };
        PlagiarismChecker checker = new PlagiarismChecker();

        checker.readFiles(paths);

        // Completa el método uploadFile
        checker.uploadFile();

        System.out.print(checker.BD.get(0) + checker.BD.get(1) + "  :::::" + checker.suspicius);
    }
}
