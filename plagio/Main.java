import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PlagioDetector detector = new PlagioDetector();

        try {
            List<String> fileContents = FileManager.readAllFilesFromDirectory("archivos/");
            for (String content : fileContents) {
                detector.addTextToTrie(content);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los archivos: " + e.getMessage());
            return;
        }

        String suspiciousText = "Hay muchos lenguajes en el campo de la programación, pero algunos son verdaderamente icónicos. Java, por ejemplo, ha dejado una huella duradera desde que apareció en los años 90. Actualmente, es uno de los lenguajes más populares y ampliamente adoptados. Parte de este logro se debe a la JVM, que permite que el código Java funcione en diferentes plataformas sin cambios. También es importante mencionar que Java es un lenguaje que prioriza la orientación a objetos, facilitando el desarrollo de soluciones modulares.\n" + //
                "";
        boolean isPlagiarized = detector.detectPlagio(suspiciousText);
        System.out.println(isPlagiarized ? "Plagio detectado." : "No se detectó plagio.");
    }
}
