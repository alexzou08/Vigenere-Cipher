import edu.duke.*;

public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int theKey;
    
    public CaesarCipher(int key) {
        theKey = key;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) +
                            alphabet.substring(0,key);
        alphabet = alphabet + alphabet.toLowerCase();
        shiftedAlphabet = shiftedAlphabet + shiftedAlphabet.toLowerCase();
        // includes both uppercase and lowercase
    }
    
    private char transformLetter(char c, String from, String to) {
        // change a single character if in String "from" to string "to", using the same index
        int idx = from.indexOf(c);
        if (idx != -1) {
            return to.charAt(idx);
        }
        return c;
    }
    
    public char encryptLetter(char c) {
        return transformLetter(c, alphabet, shiftedAlphabet);
    }
    
    public char decryptLetter(char c) {
        return transformLetter(c, shiftedAlphabet, alphabet);
    }
    
    private String transform(String input, String from, String to){
        // encrypt all characters in String input
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            c = transformLetter(c, from, to);
            sb.setCharAt(i, c);
        }
        return sb.toString();
    }
    
    public String encrypt(String input) {
        return transform(input, alphabet, shiftedAlphabet);
    }
    
    public String decrypt(String input) {
        return transform(input, shiftedAlphabet, alphabet);
    }
    
    public String toString() {
        return "" + theKey;
    }
    
}
