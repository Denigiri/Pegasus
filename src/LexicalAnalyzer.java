import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    private static final Map<Pattern, String> tokenMap = Tokens.getTokenMap(); // Access token map from separate file


    public static List<String[]> tokenize(String code) {
        List<String[]> symbolTable = new ArrayList<>();
        StringBuilder currentLexeme = new StringBuilder();

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            // Check for matching patterns
            for (Pattern pattern : tokenMap.keySet()) {
                Matcher matcher = pattern.matcher(code.substring(i));
                if (matcher.lookingAt()) {
                    String lexeme = matcher.group();
                    String tokenName = tokenMap.get(pattern);
                    symbolTable.add(new String[]{lexeme, tokenName});
                    i += lexeme.length() - 1; // Adjust index for next iteration
                    currentLexeme = new StringBuilder(); // Reset for next lexeme
                    break;
                }
            }

            // Handle single-character lexemes
            if (currentLexeme.length() == 0 && !Character.isWhitespace(c)) {
                // String lexeme = String.valueOf(c);
                // String tokenName = getClosestTokenName(lexeme); // Check for potential misspellings
                // symbolTable.add(new String[]{lexeme, tokenName});
            } else {
                // currentLexeme.append(c);
            }
        }

        // Add any remaining lexeme at the end
        if (currentLexeme.length() > 0) {
            // String lexeme = currentLexeme.toString();
            // String tokenName = getClosestTokenName(lexeme);
            // symbolTable.add(new String[]{lexeme, tokenName});
        }

        return symbolTable;
    }

    private static String getClosestTokenName(String lexeme) {
        // Implement your Levenshtein distance calculation here to find the closest match
        // For simplicity, return a default token name for now
        return "UNKNOWN";
    }

    // output aligned symbol table
    public static void showSymbolTable( List<String[]> symbolTable) {
        for (String[] entry : symbolTable) {
            System.out.println(entry[0] + "\t\t" + entry[1]);
        }
    }

    public static void main(String[] args) {
        String code = "Hello, world! This is a test string with 123 numbers, symbols like +-*/ and escape sequences \n\t\'\"\\.";
        String code2 = "int  x = 123; float y = 3.14e-2; boolean flag = true; char ch = 'A'; String str = \"Hello, world!\"; null";
        List<String[]> symbolTable = tokenize(code2);
        showSymbolTable(symbolTable);
    }
}
