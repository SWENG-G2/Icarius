package icarius.services;

public class UtilService {
    
    public static String removeLastCharacter(String s) {
        if (s == null || s.length() == 0) {
            return null;
        } else {
            return s.substring(0, s.length() - 1);
        }
    }
    
}
