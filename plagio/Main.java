import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static PlagioDetector detector;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        detector = new PlagioDetector();

        try {
            List<String> fileContents = FileManager.readAllFilesFromDirectory("archivos/");
            for (String content : fileContents) {
                detector.addTextToTrie(content);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los archivos: " + e.getMessage());
            return;
        }

        boolean running = true;
        while (running) {
            showMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    detectPlagio();
                    break;
                case 2:
                    addNewOriginalText();
                    break;
                case 3:
                    showFileContents();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta nuevamente.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Detectar plagio.");
        System.out.println("2. Agregar nuevo parrafo original.");
        System.out.println("3. Ver textos.");
        System.out.println("4. Salir.");
        System.out.print("Elige una opcion: ");
    }

    private static void detectPlagio() {
        System.out.println("\nIntroduce el parrafo sospechoso:");
        String suspiciousText = scanner.nextLine();

        boolean isPlagiarized = detector.detectPlagio(suspiciousText);
        System.out.println(isPlagiarized ? "Plagio detectado." : "No se detecto plagio.");
    }

    private static void addNewOriginalText() {
        System.out.println("\nIntroduce el nuevo parrafo original:");
        String originalText = scanner.nextLine();

        System.out.println("Introduce un nombre para el archivo (sin extension):");
        String fileName = scanner.nextLine();
        
        try {
            FileManager.writeToFile("archivos/" + fileName + ".txt", originalText);
            detector.addTextToTrie(originalText);
            System.out.println("Parrafo original guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el parrafo: " + e.getMessage());
        }
    }

    private static void showFileContents() {
        try {
            List<String> files = FileManager.listAllFilesFromDirectory("archivos/");
            System.out.println("\n--- Lista de archivos ---");
            for (int i = 0; i < files.size(); i++) {
                System.out.println((i + 1) + ". " + files.get(i));
            }
            System.out.println("Elige un archivo para ver su contenido (por numero):");

            int fileChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (fileChoice > 0 && fileChoice <= files.size()) {
                String content = FileManager.readFile("archivos/" + files.get(fileChoice - 1));
                System.out.println("\nContenido del archivo:");
                System.out.println(content);
            } else {
                System.out.println("Seleccion no valida.");
            }
        } catch (IOException e) {
            System.out.println("Error al listar o leer los archivos: " + e.getMessage());
        }
    }
}
