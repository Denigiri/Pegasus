import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private static final Map<Pattern, String> tokenMap = new HashMap<>();

    public static void put(String regex, String token_name) {
        tokenMap.put(Pattern.compile(regex), token_name);
    }

    public static void put(String regex, String token_name, String flag) {
        int flags = 0;  // Default to no flags
    
        if (flag.equalsIgnoreCase("CASE_INSENSITIVE")) {
            flags = Pattern.CASE_INSENSITIVE;
        }
        tokenMap.put(Pattern.compile(regex, flags), token_name);
    }

    //Repetitive Tokens
    static String EXCLUDE_NOISE_WORD = "\\b(?!A|The|An|a|the|an)\\b";
    
    static String EXCLUDE_RESERVED_WORDS = "(?i)\\b((?!A|An|the|let|be|while|out of|is|are|if|then|else|it|each|this|was|Remember|shorten|represent))\\b";;
    static String IDENTIFIER = EXCLUDE_RESERVED_WORDS + "\\b(?!Remember)\\b[a-zA-Z$_\\p{Sc}_][a-zA-Z0-9$_]*$";
    
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

    //Operators
        //Arithmethic Operators
        static {
            put("\\+", "ADDITION_OPERATOR");
            put("\\-", "SUBTRACTION_OPERATOR");
            put("\\*", "MULTIPLICATION_OPERATOR");
            put("\\/", "DIVISION_OPERATOR");
            put("\\%", "PERCENTAGE_OPERATOR");
        }

    //Relational Operators
            static {
            put("\\<", "LESS_THAN_OPERATOR");
            put("\\>", "GREATER_THAN_OPERATOR");
            put("\\<=", "LESS_THAN_OR_EQUAL_OPERATOR");
            put("\\>=", "GREATER_THAN_OR_EQUAL_OPERATOR");
            put("\\==", "EQUIVALENCE_OPERATOR");
            put("\\!=", "INEQUIVALENCE_OPERATOR");
        }

    //Conditional Operators
        static {
            put("\\&\\&", "AND_OPERATOR");
            put("\\|\\|", "OR_OPERATOR");
            put("\\!", "NOT_OPERATOR");
        }

    

    //Flexible Data Types
    static {
        put("\\bAn\\b", "NOISE_WORD");
        put("\\bA\\b", "NOISE_WORD");
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
        String IDENTIFER = EXCLUDE_RESERVED_WORDS + "[a-zA-Z$_][a-zA-Z0-9$_]*";
        put(IDENTIFIER, "IDENTIFIER");
    }

    //Literals, Expressions
    static {
        put("[0-9]+", "DECIMAL_INTEGER_LITERAL");
        put("0x[0-9A-F]+", "HEX_INTEGER_LITERAL");
        // put("0[0-7]+", "OCTAL_INTEGER_LITERAL");
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
        // String LOWERCASE_LETTERS = EXCLUDE_NOISE_WORD + "[a-zA-Z$_][a-zA-Z0-9$_]*";
        // put(LOWERCASE_LETTERS, "LOWERCASE_LETTERS");
        // String UPPERCASE_LETTERS = EXCLUDE_NOISE_WORD + "[A-Z]+";
        // put("UPPERCASE_LETTERS", "UPPERCASE_LETTERS");
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

    //Blanks and Spaces
    static {
        put("\\s{2,}", "WARNING: EXCESS WHITESPACES");
    }

    //ERROR HANDLING
            //Invalid Operators
            static {
                // put("^(?!(\\+|-|\\*|/|%|&&|\\|\\||!)$).*", "INVALID_OPERATOR");
            }

    public static void main(String[] args) {
        String character_set         = "Hello, world! This is a test string with 123 numbers, symbols like +-*/ and escape sequences \n\t\'\"\\.";
        String variable_assignment   = "int  x = 123; float y = 3.14e-2; boolean flag = true; char ch = 'A'; String str = \"Hello, world!\"; null";
        String flexible_datatype     = "The A An Score could only be < 100\nA Score could only be \"Very Good\", \"Good\", \"Pass\", \"Fail\" "
                                        + "\nA Choice could only be true or false";
        String replace_long_names    = "\nRemember Principles_of_Programming_Languages as \"PPL\" "
                                        + "\nShorten \"Print_Line\" to \"println\" "
                                        + "\nRepresent \"Sum_of_A_Finite_Number_of_Terms\" as \"∑\" "; 
        String test                 = "+ - << <= if else then";
        tokenize(test);
    }

    public static void tokenize(String code) {
        String[] lexemes = code.split(  "\\s"  ); // Split by spaces 
        StringBuilder table = new StringBuilder();
        for (String lexeme : lexemes) {
            String tokenName = getTokenName(lexeme);
            table.append(String.format("%-40s %-20s\n", lexeme, tokenName));
        }
        System.out.println(table);
    }

    public static String getTokenName(String lexeme) {
        for (Map.Entry<Pattern, String> entry : tokenMap.entrySet()) {
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
