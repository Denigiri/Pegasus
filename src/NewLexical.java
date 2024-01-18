import java.util.Scanner;

public class NewLexical {
    public static void main(String[] args) {
        String code = "int num = 2* 3 * 4 + \"hey\", \"hey world\" \"4+4\"";
        String code2 = "The A An Score could only be < 100\n"
                        + "A Score could only be \"Very Good\", \"Good\", \"Pass\", \"Fail\" ";
                        
        Scanner sc = new Scanner(code);
        String digits_and_operators = "(?<=\\d | \s+)(?=\\D)|(?<=\\D | \s+)(?=\\d)"; // Split between digits and operators with or without spaces
        String whitespaces = "\s+";
        String string_literals = "(?<=\")\\s+(?=\")|(?<=')\\s+(?=')";  

        // ATTEMPT #1
        // sc.useDelimiter("((?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)) | ((?<=\\d)(?=\\D)|(?<=\\D)(?=\\d))"
        //                 + "|\s|(?<=\")\\s+(?=\")|(?<=')\\s+(?=')");

        // ATTEMPT #2
        sc.useDelimiter("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)|(?<=\\d|\s)(?=\\D)|(?<=\")\\s+(?=\")|(?<=')\\s+(?=')");
        // sc.useDelimiter("\\s+(?=,)|(?<=,)\\s+(?=\"|\')");

        // ATTEMPT #3
            //separate keywords by whitespaces
            // sc.useDelimiter("\s");

            //separate literals from operators in expressions
            //4+4
            //4 + 4
            //4  +  4
            // sc.useDelimiter("(?<=\\d)(?=\\D) | (?<=\\D)(?=\\d)");

            //separate string literals only by double quotations
            //"hello world"

        //(?<=\")\\s+(?=\") If before ay " and after ay ", split it by 1 or more whitespaces


        while (sc.hasNext()) {
            String token = sc.next();
            System.out.println(token);
        }


        // while (sc.hasNext()) {
        //     if (sc.hasNextInt()) {
        //         int value = sc.nextInt();
        //         System.out.println("Integer: " + value);
        //     } else if (sc.hasNext("[a-zA-Z][a-zA-Z0-9_]*")) {
        //         String identifier = sc.next();
        //         System.out.println("Identifier: " + identifier);
        //     } else {
        //         String symbol = sc.next();
        //         System.out.println("Symbol: " + symbol);
        //     }
        // }

        sc.close();
        
    }
}
