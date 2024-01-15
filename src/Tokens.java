import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Tokens {
    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    static {
        // Add your regex patterns and token names here
        put("\\b(int|float|char|bool)\\b", "DATA_TYPE");
        put("[a-zA-Z]+", "IDENTIFIER");
        put("[0-9]", "INTEGER_LITERAL");
        put("[=+\\-*/;]", "OPERATOR");
        // ... Add more patterns as needed
    }

    public static void put(String regex, String token_name) {
        tokenMap.put(Pattern.compile(regex), token_name);
    }

    public static Map<Pattern, String> getTokenMap() {
        return tokenMap;
    }
}
