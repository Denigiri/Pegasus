import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private static final Map<Pattern, String> patterns = new HashMap<>();

    static {
        patterns.put(Pattern.compile("\\bint\\b"), "INTEGER_DATA_TYPE");
        patterns.put(Pattern.compile("\\bif\\b"), "IF_KEYWORD");
        patterns.put(Pattern.compile("\\belse\\b"), "ELSE_KEYWORD");
        // Add more patterns here
        patterns.put(Pattern.compile("\\d+"), "NUMBER");
        patterns.put(Pattern.compile("[a-zA-Z]+"), "IDENTIFIER");
        patterns.put(Pattern.compile("[=;+\\-*/]"), "OPERATOR"); // Multiple operators in one pattern
        patterns.put(Pattern.compile(";"), "OPERATOR"); // Multiple operators in one pattern
    }

    public static void main(String[] args) {
        String code = "int num = 3; asdfasdf 123 sf";

        tokenize(code);
    }

    public static void tokenize(String code) {
        String[] lexemes = code.split("\\s+"); // Split by spaces

        for (String lexeme : lexemes) {
            String tokenName = getTokenName(lexeme);
            System.out.println(lexeme + "\t\t" + tokenName);
        }
    }

    public static String getTokenName(String lexeme) {
        for (Map.Entry<Pattern, String> entry : patterns.entrySet()) {
            Matcher matcher = entry.getKey().matcher(lexeme);
            String TOKEN_NAME = entry.getValue();
            if (matcher.matches()) {
                return TOKEN_NAME;
            }
            if (matcher.find()) {
                return "ERROR: DO YOU MEAN " + TOKEN_NAME;
            }
            //if mispelled (check using Levinstein algo), return suggestions
        }
        return "UNKNOWN";
    }
}
