import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static List<String> readAllFilesFromDirectory(String path) throws IOException {
        List<String> contents = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                contents.add(content);
            }
        }
        return contents;
    }
}
