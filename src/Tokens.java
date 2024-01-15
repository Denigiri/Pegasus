import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Tokens {
    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    //USE ENGLISH! (CHARACTER SET)
    static {
        put("[a-zA-Z]+", "ALPHABET");
        put("[a-z]+", "LOWERCASE_LETTERS");
        put("[A-Z]+", "UPPERCASE_LETTERS");
        put("[0-9]+","DIGITS");
        put("[+]|[-]", "SIGN"); 
        put("[+|-|*|/|!|?]", "SYMBOL");
        put("[0-7]+", "OCTAL_DIGITS");
        put("[0-9A-F]+", "HEXADECIMAL_DIGITS"); 
        put("\\\\'", "SINGLE_QUOTE_ESCAPE");
        put("\\\\\"", "DOUBLE_QUOTE_ESCAPE");
        put("\\\\\\\\", "BACKSLASH_ESCAPE");
        put("\\\\t", "TAB_ESCAPE");
        put("\\\\b", "BACKSPACE_ESCAPE");
        put("\\\\n", "NEWLINE_ESCAPE");
    }

    //BLANKS AND SPACES
    static {
        // Add your regex patterns and token names here
        put("\\s{2,}", "** WARNING: EXCESS WHITESPACES **");
        //COPY THE put() funciton above and add your regex patterns and token names here
    }

    //INSERT TITLE OF YOUR PART
    static {
        // Add your regex patterns and token names here
        
        //COPY THE put() funciton above and add your regex patterns and token names here
    }

    public static void put(String regex, String token_name) {
        tokenMap.put(Pattern.compile(regex), token_name);
    }

    public static Map<Pattern, String> getTokenMap() {
        return tokenMap;
    }
}
