import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PlagiarismChecker extends JFrame implements design {

    private JLabel title = new JLabel("Detector de Plagio:");
    private ResultChecker result;
    private List<File> selectedFiles = new ArrayList<>();
    private final String BD_FOLDER_PATH = "./plagio/BD/.";
    private String[] paths;
    private String suspicious;
    private ArrayList<String> database;
    private JTable resultTable;
    private JScrollPane resultScrollPane;

    public PlagiarismChecker() {
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
                updateResults(result.getResults());
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + filename);
                e.printStackTrace();
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

    private void build() {
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea inputTextArea = new JTextArea(10, 35);
        JLabel inputLabel = new JLabel("Ingresar texto:");
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        leftPanel.add(inputLabel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);

        JPanel docButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton loadFromDBButton = new JButton("Cargar Base de Datos");
        JButton loadToCheckButton = new JButton("Cargar Documento");
        docButtonPanel.add(loadFromDBButton);
        docButtonPanel.add(loadToCheckButton);

        JLabel docLabel = new JLabel("Cargar documentos:");
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JPanel docPanel = new JPanel(new BorderLayout());
        docPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        docPanel.add(docLabel, BorderLayout.NORTH);
        docPanel.add(docButtonPanel, BorderLayout.CENTER);
        rightPanel.add(docPanel, BorderLayout.NORTH);

        docLabel = new JLabel("Resultados:");
        resultTable = new JTable();
        resultScrollPane = new JScrollPane(resultTable);
        docPanel = new JPanel(new BorderLayout());
        docPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        docPanel.add(docLabel, BorderLayout.NORTH);
        docPanel.add(resultScrollPane, BorderLayout.CENTER);
        rightPanel.add(docPanel, BorderLayout.CENTER);

        JButton checkButton = new JButton("Comprobar plagio");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(checkButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        loadFromDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(PlagiarismChecker.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFiles.clear();
                    File[] files = fileChooser.getSelectedFiles();
                    for (File file : files) {
                        selectedFiles.add(file);
                    }
                }
                copyFilesToBDFolder();
            }
        });

        loadToCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    public void updateResults(DefaultTableModel model) {
        resultTable.setModel(model);
        resultScrollPane.setViewportView(resultTable);
    }

    public static String[] getBD() {
        String folderPath = "./plagio/BD/.";
        File folder = new File(folderPath);
        File[] fileList = folder.listFiles();

        if (fileList != null) {
            String[] filePaths = new String[fileList.length];
            for (int i = 0; i < fileList.length; i++) {
                filePaths[i] = fileList[i].getPath();
            }
            return filePaths;
        } else {
            System.out.println("La carpeta no contiene archivos.");
            return null;
        }
    }

    private void copyFilesToBDFolder() {
        if (!selectedFiles.isEmpty()) {
            File bdFolder = new File(BD_FOLDER_PATH);
            if (!bdFolder.exists()) {
                bdFolder.mkdirs();
            }
            for (File file : selectedFiles) {
                Path source = file.toPath();
                Path destination = Path.of(BD_FOLDER_PATH + file.getName());
                try {
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            selectedFiles.clear();
        }
    }

    public static void main(String[] args) {
        String[] paths = getBD();
        PlagiarismChecker checker = new PlagiarismChecker();

        checker.readFiles(paths);

        checker.setVisible(true);
    }
}
