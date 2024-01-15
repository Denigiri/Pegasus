import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Tokens {
    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    //Pronouns (Pointers)
    static {
        put("(?i)\\bIT\\b", "PRONOUNS_IT");
        put("(?i)\\bEACH\\b", "PRONOUNS_EACH");
        put("(?i)\\bTHIS\\b", "PRONOUNS_THIS");
        put("(?i)\\bWAS\\b", "PRONOUNS_WAS");
    }

    //Conjunction (If-Else)
    static {
        put("(?i)\\bIF\\b", "CONJUNCTION_IF");
        put("(?i)\\bTHEN\\b", "CONJUNCTION_THEN");
        put("(?i)\\bELSE\\b", "CONJUNCTION_ELSE");
    }

    // //Operators
    //     //Arithmethic Operators
        static {
            put("\\+", "ADDITION_OPERATOR");
            put("\\-", "SUBTRACTION_OPERATOR");
            put("\\*", "MULTIPLICATION_OPERATOR");
            put("\\/", "DIVISION_OPERATOR");
            put("\\%", "PERCENTAGE_OPERATOR");
        }

    //Relational Operators
            static {
            put("\\+", "ADDITION_OPERATOR");
            put("\\-", "SUBTRACTION_OPERATOR");
            put("\\*", "MULTIPLICATION_OPERATOR");
            put("\\/", "DIVISION_OPERATOR");
            put("\\%", "PERCENTAGE_OPERATOR");
        }

    //Conditional Operators
        static {
            put("\\&&", "AND_OPERATOR");
            put("\\||", "OR_OPERATOR");
            put("\\!", "NOT_OPERATOR");
        }

    //Repetitive Tokens
    static String NOISE_WORD = "\\b(A|The|An|a|the|an)\\b";
    static String IDENTIFIER = "(?i)^(?!(RESERVED_WORDS))[a-zA-Z$_\\p{Sc}_][a-zA-Z0-9$_]*$";
    static String RESERVED_WORDS = "(the|let|be|while|out of|is|are|if|then…)";

    //Flexible Data Types
    static {
        put("\\bAn\\s", "NOISE_WORD");
        put("\\bA", "NOISE_WORD");
        put("\\b(The)\\b", "NOISE_WORD");
        put("\\bcould\\s+only\\s+be\\b", "COULD_ONLY_BE_KEYWORD");
        put("\\bfrom", "FROM");
        put(",", "COMMA");
        put("<", "LESS_THAN_OPERATOR");

    }
    //Replace Long Names
    static {
        put("\\bRemember\\s\\b", "REMEMBER_KEYWORD");
        put("\\bShorten\\b", "SHORTEN_KEYWORD");
        put("\\bRepresent\\b", "REPRESENT_KEYWORD");
        put("\\bto\\b", "CONVERT_KEYWORD");
        put("\\bas\\b", "CONVERT_KEYWORD");
    }

    //Identifier, Variable Assignment
    static {
        put("[a-zA-Z$_][a-zA-Z0-9$_]*", "IDENTIFIER");
    }

    //Literals, Expressions
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

    //Delimiter
     static {
        put("\\n", "DELIMITER");
    }
    
    //Character Set
    static {
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

    //Blanks and Spaces
    static {
        put("\\s{2,}", "WARNING: EXCESS WHITESPACES");
    }

    //Insert title of your part
    static {
        // Add your regex patterns and token names here
        
    }

    public static void put(String regex, String token_name) {
        tokenMap.put(Pattern.compile(regex), token_name);
    }

    public static Map<Pattern, String> getTokenMap() {
        return tokenMap;
    }
}
