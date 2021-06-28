import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    
    public HashSet readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<String>();
        for(String line : fr.lines()) {
            line = line.toLowerCase();
            dictionary.add(line);
        }
        return dictionary;
    }
    
    public Integer countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        for(String word : message.split("\\W+")) {
            word = word.toLowerCase();
            if(dictionary.contains(word)){
                count ++;
            }
        }
        return count;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary) {
        char mostCommon = 'e';
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        //put alphabetic characters to a hashmap
        for(char c = 'a'; c <= 'z'; c++) {
            map.put(c, 0);
        }
        int[] counts = new int[26];
        for(String word : dictionary) {
            //break word into characters
            word = word.toLowerCase();
            for(char c : word.toCharArray()) {
                if(map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c,1);
                }
            }
        }
        // find the max word count 
        int MaxValue = 0;
        for(char c : map.keySet()) {
            if(map.get(c) > MaxValue) {
                MaxValue = map.get(c);
                mostCommon = c;
            }
        }
        return mostCommon;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        // a HashMap, called languages, mapping a String representing the name
        //of a language to a HashSet of Strings containing the words in that language
        int maxCount = 0;
        //find the max number count
        for(String s : languages.keySet()) {
            //find the dictionary of language s
            HashSet<String> dictionary = languages.get(s);
            String testDecryption = breakForLanguage(encrypted, dictionary);
            int realWordCount = countWords(testDecryption, dictionary);
            if(realWordCount > maxCount) {
                maxCount = realWordCount;
            }
        }
        //find the language that has the max number count
        for(String s : languages.keySet()) {
            HashSet<String> dictionary = languages.get(s);
            String decrypted = breakForLanguage(encrypted, dictionary);
            int currCount = countWords(decrypted, dictionary);
            if(currCount == maxCount) {
                System.out.println(s + " was the language used to decrypt the message!");
                System.out.println(s);
            }
        }
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int[] counts = new int[101];
        String[] decrypted = new String[101];
        int maxCount = 0;
        int maxIndex = 0;
        //try keylength from 1 to 100 and see which one is the most realistic
        char mostCommon = mostCommonCharIn(dictionary);
        for(int k = 1; k < 101; k ++) {
            int[] keys = tryKeyLength(encrypted, k, mostCommon);
            VigenereCipher vc = new VigenereCipher(keys);
            decrypted[k] = vc.decrypt(encrypted);
            counts[k] = countWords(decrypted[k], dictionary);
        }
        // find the keylength that has the most realistic words 
        for(int k = 1; k < 101; k ++) {
            if(counts[k] > maxCount) {
                maxCount = counts[k];
                maxIndex = k;
            }
        }
        return decrypted[maxIndex];
    }
    
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder sb = new StringBuilder();
        for(int k = whichSlice; k < message.length(); k += totalSlices) {
            char c = message.charAt(k);
            sb.append(c);
        }
        //System.out.println(sb);
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for(int k = 0; k < klength; k ++) {
            String kslice = sliceString(encrypted, k, klength);
            CaesarCracker kcc = new CaesarCracker(mostCommon);
            int kKey = kcc.getKey(kslice);
            key[k] = kKey;
        }
        return key;
    }
    
    public void breakVigenere () {
        //below is code when you don't know the language nor the keylength
        FileResource fr = new FileResource();
        String message = fr.asString();
        String[] languages = {"Danish","Dutch","English","French","German","Italian",
                              "Portuguese","Spanish"};
        HashMap<String, HashSet<String>> dictionary = new HashMap<String, HashSet<String>>();
        for (int k = 0; k < languages.length; k ++) {
            FileResource file = new FileResource("dictionaries/" + languages[k]);
            HashSet<String> languageDictionary = readDictionary(file);
            dictionary.put(languages[k], languageDictionary);
        }
        //find the language
        breakForAllLangs(message, dictionary);
        //use the language to decrypte message
        String decrypted = breakForLanguage(message, dictionary.get("French"));
        System.out.println(decrypted);
        
        //below is code when you know the language but not the keyLength
        //FileResource fr = new FileResource();
        //String message = fr.asString();
        //read dictionary
        //FileResource dict = new FileResource();
        //HashSet dictionary = readDictionary(dict);
        //String decrypted = breakForLanguage(message, dictionary);
        //System.out.println(decrypted);
        
        //Below is code when you know the language and keyLength
        // length of the cipher slices
        //int keyLength = 4;
        // mostcommon character in that language
        //char mostCommon = 'e';
        //int[] keys = tryKeyLength(message, keyLength, mostCommon);
        //VigenereCipher vc = new VigenereCipher(keys);
        //String decrypted = vc.decrypt(message);
        // System.out.println(decrypted);
    }
    
}
