import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Tokens {
    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    static {
        // Add your regex patterns and token names here
        tokenMap.put(Pattern.compile("\\b(int|float|char|bool)\\b"), "DATA_TYPE");
        tokenMap.put(Pattern.compile("[a-zA-Z]+"), "IDENTIFIER");
        tokenMap.put(Pattern.compile("\\d+"), "INTEGER_LITERAL");
        tokenMap.put(Pattern.compile("[=+\\-*/;]"), "OPERATOR");
        // ... Add more patterns as needed
    }

    public static Map<Pattern, String> getTokenMap() {
        return tokenMap;
    }
}
