import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlagioDetector {
    private Trie trie;

    public PlagioDetector() {
        this.trie = new Trie();
    }
    public PlagioDetector(String originalText) {
        trie = new Trie();
        List<String> subsequences = generateAllSubsequences(tokenize(originalText));
        for (String subseq : subsequences) {
            trie.insert(subseq);
        }
    }

    private List<String> tokenize(String text) {
        // Estoes una simplificación. En un caso real, podrías considerar un tokenizador más robusto.
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
    
}
