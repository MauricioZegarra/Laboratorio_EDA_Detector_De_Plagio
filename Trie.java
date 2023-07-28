public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (Character.isLetter(ch)) { // Verificar si el carácter es una letra
                char lowerCaseCh = Character.toLowerCase(ch);
                if (!node.contains(lowerCaseCh)) {
                    node.put(lowerCaseCh, new TrieNode());
                }
                node = node.get(lowerCaseCh);
            }
        }
        node.setEndOfWord(true);
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (Character.isLetter(ch)) { // Verificar si el carácter es una letra
                char lowerCaseCh = Character.toLowerCase(ch);
                if (!node.contains(lowerCaseCh)) {
                    return false;
                }
                node = node.get(lowerCaseCh);
            }
        }
        return node.isEndOfWord();
    }
}
