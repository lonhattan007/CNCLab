package cncLab01;

import java.util.HashMap;
import java.util.Map;

public class Caesar {
    static final int DOMAIN_SIZE = 26;
    static final int LOWER_BOUND = 65;
    static final int UPPER_BOUND = 90;

    static Boolean isAlphabet(Character character) {
        return (character >= 65 && character <= 90)
                || (character >= LOWER_BOUND && character <= UPPER_BOUND);
    }

    static Map<Character, Integer> countChars(String str) {
        if (str == null) {
            return new HashMap<Character, Integer>();
        }

        String tempStr = str.toUpperCase();

        Map<Character, Integer> result = new HashMap<Character, Integer>();

        for (int i = 0; i < tempStr.length(); i++) {
            char currentChar = tempStr.charAt(i);
            if (isAlphabet(currentChar)) {
                if (result.containsKey(tempStr.charAt(i))) {
                    result.put(currentChar, result.get(currentChar) + 1);
                } else {
                    result.put(currentChar, 1);
                }
            }
        }

        return result;
    }

    private static int getKey(char originalChar, char cipherChar) {
        if (cipherChar >= originalChar) {
            return cipherChar - originalChar;
        } else {
            return DOMAIN_SIZE - (originalChar - cipherChar);
        }
    }

    public static String caesarCipher(String plainText, int key) {
        StringBuilder result = new StringBuilder(plainText.length());
        char orginalChar, cipherChar;

        for (int i = 0; i < plainText.length(); i++) {
            orginalChar = plainText.charAt(i);

            if (orginalChar + key <= UPPER_BOUND) {
                cipherChar = (char) (orginalChar + key);
            } else {
                cipherChar = (char) (orginalChar + key - DOMAIN_SIZE);
            }

            result.append(cipherChar);
        }

        return result.toString();
    }

    public static String caesarDecipher(String cipherText, int key) {
        StringBuilder result = new StringBuilder(cipherText.length());
        char cipherChar, originalChar;

        // Uppercase goes from 65 to 90
        for (int i = 0; i < cipherText.length(); i++) {
            cipherChar = cipherText.charAt(i);

            if (cipherChar - key >= LOWER_BOUND) {
                originalChar = (char) (cipherChar - key);
            } else {
                originalChar = (char) (cipherChar - key + DOMAIN_SIZE);
            }

            result.append(originalChar);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        // Question 1
        System.out.println("Question 1 -------------------------------------------");
        final String str = "KNXMNSLKWJXMBFYJWGJSIXFIRNYXBTWIKNXMWFSITAJWMJQRNSLFSDIFD";
        final char[] cipherChars = {'J', 'K', 'N', 'S', 'W', 'X'};
        final char[] originalChars = {'E', 'T'};

        System.out.println(countChars(str).toString());

        int key;
        for (char originalChar: originalChars) {
            for (char cipherChar: cipherChars) {
                key = getKey(originalChar, cipherChar);
                System.out.format("%c -> %c: %d\n", originalChar, cipherChar, key);
                System.out.println(caesarDecipher(str, key));
            }
        }

        // Question 2
        System.out.println("Question 2 -------------------------------------------");
        final String str2 = "asvphgyt";
        for (int i = 0; i < 26; i++) {
            System.out.format("%d: %s\n", i, caesarDecipher(str2.toUpperCase(), i));
        }
    }
}

