import java.util.Scanner;

public class NewLexical {
    public static void main(String[] args) {
        String code = "int num = 2%3 * 4 + \"hey\", \"hey world\" \"4+4\"";
        String code2 = "The A An Score could only be < 100\n"
                        + "A Score could only be \"Very Good\", \"Good\", \"Pass\", \"Fail\" ";

        Scanner sc = new Scanner(code);

        // ATTEMPT #3
            //separate keywords by whitespaces
            sc.useDelimiter("\s");

            //separate literals from operators in expressions

            sc.useDelimiter("(?<=\\d\\s*)(?=[+-/*%])"
                            + "|(?<=[+-/*%]\\s)(?=\\d)"
                            + "|(?<=\\d)(?=[+-/*%])"
                            + "|(?<=[+-/*%])(?=\\d)");

            //separate string literals only by double quotations
            //"hello world"


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
