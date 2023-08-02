public class TrieNode {
    private TrieNode[] children;
    private boolean isEndOfWord;

    public TrieNode() {
        children = new TrieNode[26]; // Tamaño del alfabeto inglés
        isEndOfWord = false;
    }
    
    public boolean contains(char ch) {
        int index = ch - 'a';
        return index >= 0 && index < 26 && children[index] != null;
    }

    public TrieNode get(char ch) {
        return children[ch - 'a'];
    }

    public void put(char ch, TrieNode node) {
        children[ch - 'a'] = node;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void insert(String word) {
        TrieNode current = this;
        for (char ch : word.toCharArray()) {
            // Convertimos el carácter a minúscula antes de procesarlo
            ch = Character.toLowerCase(ch);
            if (!Character.isLetter(ch)) {
                // Si el carácter no es una letra, lo ignoramos
                continue;
            }
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = this;
        for (char ch : word.toCharArray()) {
            // Convertimos el carácter a minúscula antes de procesarlo
            ch = Character.toLowerCase(ch);
            if (!Character.isLetter(ch)) {
                // Si el carácter no es una letra, no puede estar en el trie
                return false;
            }
            int index = ch - 'a';
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord;
    }
}
