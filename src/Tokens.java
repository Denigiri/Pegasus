import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Tokens {
    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    //NAME THAT THING (IDENTIFIER, VARIABLE ASSIGNMENT)
    static {
        put("[a-zA-Z$_][a-zA-Z0-9$_]*", "IDENTIFIER");

    }

    //SAY A THING (LITERALS, EXPRESSIONS)
    static {
        put("[0-9]+", "DECIMAL_INTEGER_LITERAL");
        put("0x[0-9A-F]+", "HEX_INTEGER_LITERAL");
        put("0[0-7]+", "OCTAL_INTEGER_LITERAL");
        put("0b[01]+", "BINARY_INTEGER_LITERAL");
        put("[0-9]+\\.[0-9]+(E[+-]?[0-9]+)?", "DECIMAL_FLOATING_POINT_LITERAL");
        put("0x[0-9A-F]+\\.[0-9A-F]+(P[+-]?[0-9]+)?", "HEX_FLOATING_POINT_LITERAL");
        put("true|false", "BOOLEAN_LITERAL");
        put("'([^'\\\\\\n]|\\\\.)*'", "CHARACTER_LITERAL");
        put("\"([^\"\\\\\\n]|\\\\.)*\"", "STRING_LITERAL");
        put("null", "NULL_LITERAL");

    }
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
