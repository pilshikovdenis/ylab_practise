package homework3.transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator{
    private Map<String, String> dictionary;
    public TransliteratorImpl() {
        dictionary = new HashMap<>();
        dictionary.put("А", "A");
        dictionary.put("Б", "B");
        dictionary.put("В", "V");
        dictionary.put("Г", "G");
        dictionary.put("Д", "D");

        dictionary.put("Е", "E");
        dictionary.put("Ё", "E");
        dictionary.put("Ж", "ZH");
        dictionary.put("З", "Z");
        dictionary.put("И", "I");
        dictionary.put("Й", "I");

        dictionary.put("К", "K");
        dictionary.put("Л", "L");
        dictionary.put("М", "M");
        dictionary.put("Н", "N");
        dictionary.put("О", "O");
        dictionary.put("П", "P");

        dictionary.put("Р", "R");
        dictionary.put("С", "S");
        dictionary.put("Т", "T");
        dictionary.put("У", "U");
        dictionary.put("Ф", "F");

        dictionary.put("Х", "KH");
        dictionary.put("Ц", "TS");
        dictionary.put("Ч", "CH");
        dictionary.put("Ш", "SH");
        dictionary.put("Щ", "SHCH");
        dictionary.put("Ы", "Y");

        dictionary.put("Ь", "");
        dictionary.put("Ъ", "IE");
        dictionary.put("Э", "E");
        dictionary.put("Ю", "IU");
        dictionary.put("Я", "IA");

    }
    @Override
    public String transliterate(String source) {
        String translatedString = source;

        for(Map.Entry<String, String> letter : dictionary.entrySet()) {
            translatedString = translatedString.replace(letter.getKey(), letter.getValue());
        }
        return translatedString;
    }
}
