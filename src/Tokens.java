import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Tokens {
    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    //PUT REPETITIVE TOKENS HERE
    static String NOISE_WORD = "\\b(a|A|the|an)\\b";
    static String IDENTIFIER = "(?i)^(?!(RESERVED_WORDS))[a-zA-Z$_\\p{Sc}_][a-zA-Z0-9$_]*$";
    static String RESERVED_WORDS = "(the|let|be|while|out of|is|are|if|then…)";

    //SAY WHAT A THING COULD BE (FLEXIBLE DATA TYPES)
    static {
        put("\\bAn\\s", "NOISE_WORD");
        put("\\bA", "NOISE_WORD");
        put("\\b(The)\\b", "NOISE_WORD");
        put("\\bcould\\s+only\\s+be\\b", "COULD_ONLY_BE_KEYWORD");
        put("\\bfrom", "FROM");
        put(",", "COMMA");
        put("<", "LESS_THAN_OPERATOR");

    }

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
        // put("[a-zA-Z]+", "ALPHABET");
        // put("[a-z]+", "LOWERCASE_LETTERS");
        // put("[A-Z]+", "UPPERCASE_LETTERS");
        // put("[0-9]+","DIGITS");
        // put("[+]|[-]", "SIGN"); 
        // put("[+|-|*|/|!|?]", "SYMBOL");
        // put("[0-7]+", "OCTAL_DIGITS");
        // put("[0-9A-F]+", "HEXADECIMAL_DIGITS"); 
        // put("\\\\'", "SINGLE_QUOTE_ESCAPE");
        // put("\\\\\"", "DOUBLE_QUOTE_ESCAPE");
        // put("\\\\\\\\", "BACKSLASH_ESCAPE");
        // put("\\\\t", "TAB_ESCAPE");
        // put("\\\\b", "BACKSPACE_ESCAPE");
        // put("\\\\n", "NEWLINE_ESCAPE");
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
