
/**
 * Write a description of tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class tester {
    public void testCaesarCipher() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        CaesarCipher cc = new CaesarCipher(5);
        System.out.println(cc);
        String encrypted = cc.encrypt(message);
        String decrypted = cc.decrypt(encrypted);
        System.out.println("message is: " + message);
        System.out.println("encrypted is: " + encrypted);
        System.out.println("decrypted is: " + decrypted);
    }
    
    public void testCaesarCracker() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        CaesarCracker cc = new CaesarCracker();
        String decrypted = cc.decrypt(message);
        System.out.println(decrypted);
    }
    
    public void testVigenereCipher() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        int[] key = {17,14,12,4};
        VigenereCipher vc = new VigenereCipher(key);
        String encrypted = vc.encrypt(message);
        String decrypted = vc.decrypt(encrypted);
        System.out.println("message is: " + message);
        System.out.println("encrypted is: " + encrypted);
        System.out.println("decrypted is: " + decrypted);
    }
    
    public void testTryKeyLength(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        int [] key = vb.tryKeyLength(message, 4, 'e');
        for(int k = 0;k < key.length;k++) {
            System.out.println(key[k]);
        }
    }
    
    
    
}
