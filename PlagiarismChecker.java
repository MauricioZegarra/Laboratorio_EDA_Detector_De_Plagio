import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PlagiarismChecker extends JFrame implements design {

    private JLabel title = new JLabel("Detector de Plagio:");
    private JLabel text = new JLabel(
            "Haga clic en el botón 'Seleccionar Archivo' y espere a que se muestren los resultados.");
    private ResultChecker result;

    private String[] paths;
    private String suspicious;
    private ArrayList<String> database;

    public PlagiarismChecker() {
        // Constructor
        database = new ArrayList<>();
        build();
    }

    public void readFiles(String[] paths) {
        this.paths = paths;
        for (String path : paths) {
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(path));
                String content = new String(fileContent);
                database.add(content);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + path);
                e.printStackTrace();
            }
        }
    }

    public void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filename = selectedFile.getName();

            try {
                byte[] fileContent = Files.readAllBytes(selectedFile.toPath());

                suspicious = new String(fileContent);
                result = new ResultChecker();
                result.newSession(this.paths.length, suspicious, database);
                verifyPlagiarism().setVisible(true);
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

        JButton forUpload = new JButton("Seleccionar Archivo");
        forUpload.setFont(buttonFont);
        forUpload.setBackground(buttonColor);

        setLayout(new GridLayout(3, 1));

        add(this.title);
        add(this.text);
        add(forUpload);

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
        String[] paths = { "BD/article 1.pdf", "BD/article 2.pdf", "BD/article 3.pdf", "BD/article 4.pdf",
                "BD/article 5.pdf", "BD/article 6.pdf", "BD/article 7.pdf" };
        PlagiarismChecker checker = new PlagiarismChecker();

        checker.readFiles(paths);

        checker.setVisible(true);
    }
}
