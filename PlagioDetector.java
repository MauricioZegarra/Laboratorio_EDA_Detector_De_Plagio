import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PlagioDetector extends JFrame {

    private Trie trie;
    private final EmptyBorder border = new EmptyBorder(10, 10, 10, 10);
    private String suspicius;

    public PlagioDetector() {
        build();
    }

    public PlagioDetector(String originalText) {
        trie = new Trie();
        List<String> subsequences = generateAllSubsequences(tokenize(originalText));
        for (String subseq : subsequences) {
            trie.insert(subseq);
        }
    }

    private List<String> tokenize(String text) {
        // Esto es una simplificación. En un caso real, podrías considerar un tokenizador más robusto.
        String[] tokens = text.split("\\s+");
        return new ArrayList<>(Arrays.asList(tokens));
    }

    private List<String> generateAllSubsequences(List<String> tokens) {
        List<String> subsequences = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            StringBuilder subseq = new StringBuilder();
            for (int j = i; j < tokens.size(); j++) {
                subseq.append(tokens.get(j)).append(" ");
                subsequences.add(subseq.toString().trim());
            }
        }
        return subsequences;
    }

    public boolean detectPlagio(String suspiciousText) {
        List<String> subsequences = generateAllSubsequences(tokenize(suspiciousText));
        for (String subseq : subsequences) {
            if (trie.search(subseq)) {
                return true;
            }
        }
        return false;
    }

    public void addTextToTrie(String text) {
        List<String> subsequences = generateAllSubsequences(tokenize(text));
        for (String subseq : subsequences) {
            trie.insert(subseq);
        }
    }

    private void build() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel content = new JPanel(new GridLayout(3, 1));

        JLabel title = new JLabel("Detector de Plagio");

        title.setFont(new Font("Verdana", Font.BOLD, 15));

        JButton analyze = new JButton("Analizar archivo");
        JButton toBD = new JButton("Subir archivos a la BD");

        title.setBorder(border);
        content.add(title);
        content.add(analyze);
        content.add(toBD);

        this.add(content);

        setTitle("APP");
        setSize(300, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setResizable(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        analyze.addActionListener((ActionEvent e) -> {
            // Permite subir un archivo del ordenador y lo almacena en suspicius
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                try {
                    byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
                    
                    suspicius = new String(fileContent);
                    
                    JOptionPane.showConfirmDialog(null,"Archivo subido exitosamente.");
                    
                    verifyPlagiarism();
                }
                catch (IOException io) {
                    JOptionPane.showConfirmDialog(null,"Error al leer el archivo");
                }
            } else {
                System.out.println("No se seleccionó ningún archivo.");
            }
        });
    }

    private void close() {
        int confirmation = JOptionPane.showConfirmDialog(this, "¿Está seguro de querer cerrar la aplicación?", "Advertencia",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmation == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    public ResultChecker verifyPlagiarism() throws IOException {
        
        ResultChecker result = new ResultChecker(FileManager.readAllFilesFromDirectory("BD/").size());
        
        int i = 0;
        
        for(String original : FileManager.readAllFilesFromDirectory("BD/")) {
            this.trie = new Trie();
            this.addTextToTrie(original);
            result.setResult(i, this.detectPlagio(suspicius));
            i++;
        }
        
        return result;
    }
}
