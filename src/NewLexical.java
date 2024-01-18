import java.util.Scanner;

public class NewLexical {
    public static void main(String[] args) {
        String code = "int num = 2&&3.123,2 < 4 + \"hey\", \"hey world\" \"4+4\"";
        String code2 = "The A An Score could only be < 100\n"
                        + "A Score could only be \"Very Good\", \"Good\", \"Pass\", \"4 + 4\" ";

        Scanner sc = new Scanner(code);

            //split code by...
            sc.useDelimiter(//whitespace between keywords
                            "\\s" 
                            //if before is integer + whitespace, and after is operator
                            + "|(?<=\\d\\s*)(?=[+-/*%<>=&|])" 
                            //if before is operator + whitespace, and after is integer
                            + "|(?<=[+-/*%<>=&|]\\s)(?=\\d)"
                            //if before is integer, and after is operator
                            + "|(?<=\\d)(?=[+-/*%<>=&|])"
                            //if before is operator, and after is integer
                            + "|(?<=[+-/*%<>=&|])(?=\\d)"
                            //separate string literals with double quotation marks
                            + "|\\s(?=\")(?=.)(?=\")"
                            //separate literals with commas
                            + "|(?=,\\s*)");
        
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
