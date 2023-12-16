package com.example.obfuscatorge;

import java.util.HashMap;
import java.util.Map;

public class Encoder {

    // Instance fields
    private final Map<Character, String> dictionary = new HashMap<>();

    /**
     * Creates new Encoder object that contains functionality for converting string into hard-readable sequence
     */
    Encoder() {
        // Lowercase letters
        dictionary.put('a', "DDDD");
        dictionary.put('b', "DDDO");
        dictionary.put('c', "oODQ");
        dictionary.put('d', "ODDQ");
        dictionary.put('e', "oDOQ");
        dictionary.put('f', "OODQ");
        dictionary.put('g', "DOOQ");
        dictionary.put('h', "QOOD");
        dictionary.put('i', "DDOQ");
        dictionary.put('j', "QODD");
        dictionary.put('k', "DoOQ");
        dictionary.put('l', "ODOQ");
        dictionary.put('m', "ODQQ");
        dictionary.put('n', "OQDQ");
        dictionary.put('o', "oODQ");
        dictionary.put('p', "oDOQ");
        dictionary.put('q', "OQoD");
        dictionary.put('r', "OQOO");
        dictionary.put('s', "oDoQ");
        dictionary.put('t', "DOoQ");
        dictionary.put('u', "oDDQ");
        dictionary.put('v', "DODQ");
        dictionary.put('w', "DOOD");
        dictionary.put('x', "QOQD");
        dictionary.put('y', "QOoD");
        dictionary.put('z', "QDOD");

        // Uppercase letters
        dictionary.put('A', "QDoO");
        dictionary.put('B', "OQoO");
        dictionary.put('C', "oODO");
        dictionary.put('D', "QOOO");
        dictionary.put('E', "OQOO");
        dictionary.put('F', "QODO");
        dictionary.put('G', "QOOQ");
        dictionary.put('H', "OQOQ");
        dictionary.put('I', "oODo");
        dictionary.put('J', "DOOo");
        dictionary.put('K', "ODOo");
        dictionary.put('L', "OQDo");
        dictionary.put('M', "DQOO");
        dictionary.put('N', "ODOQ");
        dictionary.put('O', "OoOQ");
        dictionary.put('P', "DOOQ");
        dictionary.put('Q', "DQOQ");
        dictionary.put('R', "DOQQ");
        dictionary.put('S', "OQOD");
        dictionary.put('T', "QOQQ");
        dictionary.put('U', "OQQD");
        dictionary.put('V', "QODo");
        dictionary.put('W', "OQQQ");
        dictionary.put('X', "DQoO");
        dictionary.put('Y', "DOQO");
        dictionary.put('Z', "DQOD");

        // Digits
        dictionary.put('0', "QQOO");
        dictionary.put('1', "QOOQ");
        dictionary.put('2', "ODOQ");
        dictionary.put('3', "OOQQ");
        dictionary.put('4', "OQDO");
        dictionary.put('5', "QOOD");
        dictionary.put('6', "QOQO");
        dictionary.put('7', "OODQ");
        dictionary.put('8', "OQOD");
        dictionary.put('9', "QOQD");

        // Special symbols
        dictionary.put('_', "QODQ");
    }

    /**
     * Encodes the string into a hard-readable sequence
     *
     * @param string The string to convert
     * @return Encoded string
     */

    // Methods
    public String encode(String string) {
        StringBuilder encodedString = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char symbol = string.charAt(i);

            if (dictionary.containsKey(symbol)) {
                String result = dictionary.get(symbol);
                encodedString.append(result);
            }
        }

        return encodedString.toString();
    }
}
