import java.util.Scanner;

public class NewLexical {
    public static void main(String[] args) {
        String code = "int num<=num 2 && false 3.2+2,2<4 + \"hey\", \"hey world\"";
        String code2 = "The A An Score could only be < 100\n"
                        + "A Score could only be \"Very Good\", \"Good\", \"Pass\"";

        Scanner sc = new Scanner(code);

            String numeric_literal = "\\d+(\\.\\d+)?";

            //split code by...
            sc.useDelimiter(//whitespace between keywords
                            "\\s"
                            + "|(?<!\")(?<!\\p{Alpha})\\s"
                            //BETWEEN EXPRESSIONS
                                //if before is word + whitespace, and after is operator
                                + "|(?<=(\\w+)\\s*)(?=[=+-/*%<>&|])" 
                                //if before is operator + whitespace, and after is word
                                + "|(?<=[=+-/*%<>=&|]\\s)(?=(\\w+))"
                                //if before is word, and after is operator
                                + "|(?<=(\\w+))(?=[=+-/*%<>=&|])"
                                //if before is operator, and after is word
                                + "|(?<=[=+-/*%<>=&|])(?=(\\w+))"

                            //separate string literals with double quotation marks
                            + "|\\s(?=\")(?=\")"
                            //separate literals with commas
                            + "|(?=,\\s*)|(?<=,)");
            
            sc.useDelimiter("\s|\t|\n|(?=,\\s*)|(?<=,)");

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
