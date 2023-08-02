import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class PlagiarismChecker extends JFrame {

    private JLabek title = new JLabel("Detector de Plagio:");
    private JLabel text = new JLabel("Haga click en el botón seleccionar archivo y espere a que se muestre una ventana con los resultados.");
    private ResultChecker result;

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
        setTitle("Plagiarism Detector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Crear componentes de la GUI
        JPanel originalPanel = new JPanel();


        // Definir el diseño de la GUI
        setLayout(new GridLayout(3,1));

        add();
        add(checkPanel);
        add(resultPanel);

        // Agregar el ActionListener para el botón "Detectar Plagio"
        detectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detectPlagiarism();
            }
        });

    }

    private void detectPlagiarism() {
        String originalText = originalTextArea.getText();
        String textToCheck = checkTextArea.getText();

        // Limpiamos el trie antes de cada detección de plagio
        root = new TrieNode();

        String[] originalWords = originalText.split("\\s+");
        for (String word : originalWords) {
            // Convertimos todas las palabras a minúsculas para ser insensibles a mayúsculas
            word = word.toLowerCase();
            root.insert(word);
        }

        String[] wordsToCheck = textToCheck.split("\\s+");
        List<String> plagiarizedWords = new ArrayList<>();
        for (String word : wordsToCheck) {
            // Convertimos todas las palabras a minúsculas para ser insensibles a mayúsculas
            word = word.toLowerCase();
            if (root.search(word)) {
                plagiarizedWords.add(word);
            }
        }

        // Actualizamos el área de resultado con las palabras plagiadas (si las hay)
        if (!plagiarizedWords.isEmpty()) {
            StringBuilder resultBuilder = new StringBuilder("Palabras plagiadas:\n");
            for (String word : plagiarizedWords) {
                resultBuilder.append(word).append("\n");
            }
            resultTextArea.setText(resultBuilder.toString());
        } else {
            resultTextArea.setText("No se encontraron palabras plagiadas.");
        }
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
