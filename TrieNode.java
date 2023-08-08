import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    
    public Map<Character, TrieNode> children = new HashMap<>();
    public boolean endOfWord = false;

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }
}
