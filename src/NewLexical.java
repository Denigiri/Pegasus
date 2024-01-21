import java.util.Scanner;

public class NewLexical {
    public static void main(String[] args) {
        System.out.println("LEXEME" + "\t\tTOKEN\n" );
        String code = "int num = 2&&3.123,2 < 4 + \"hey\", \"hey world\" \"4+4\"";
        String code2 = "The A An Score could only be < 100\n"
                        + "A Score could only be \"Very Good\", \"Good\", \"Pass\", \"4 + 4\" ";
        String code3 = "this WAS iT else if then + - < > <= * && || ! +++ <>!\nsds";
        Scanner sc = new Scanner(code3);

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
            if (token.matches("\\d+")) {
                System.out.println(token + "\t\tInteger" );
            
            //PRONOUNS (POINTERS)
            } else  if ("this".equalsIgnoreCase(token)||
                        "each".equalsIgnoreCase(token)||
                        "was".equalsIgnoreCase(token)||
                        "it".equalsIgnoreCase(token)) {
                System.out.println(token + "\t\tPRONOUNS");
           
            //CONJUNCTIONS (IF-ELSE)
            } else if ("if".equalsIgnoreCase(token)) {
                System.out.println(token + "\t\tCONJUNCTION_IF");
            } else if ("then".equalsIgnoreCase(token)) {
                System.out.println(token + "\t\tCONJUNCTION_THEN");  
            }else  if ("else".equalsIgnoreCase(token)) {
                System.out.println(token + "\t\tCONJUNCTION_ELSE");
            
            //OPERATORS
                //Arithmetic
                }else  if ("+".equalsIgnoreCase(token)||
                        "-".equalsIgnoreCase(token)||
                        "/".equalsIgnoreCase(token)||
                        "*".equalsIgnoreCase(token)||
                        "%".equalsIgnoreCase(token)) {
                System.out.println(token + "\t\tARITHMETIC_OPERATORS");

                //Relational
                }else  if (">".equalsIgnoreCase(token)||
                        "<".equalsIgnoreCase(token)||
                        "==".equalsIgnoreCase(token)||
                        "!=".equalsIgnoreCase(token)||
                        "<=".equalsIgnoreCase(token)||
                        ">=".equalsIgnoreCase(token)) {
                System.out.println(token + "\t\tRELATIONAL_OPERATORS");

                //CONDITIONAL
                } else if ("&&".equalsIgnoreCase(token)) {
                    System.out.println(token + "\t\tAND_OPERATOR");
                } else if ("||".equalsIgnoreCase(token)) {
                    System.out.println(token + "\t\tOR_OPERATOR");  
                }else  if ("!".equalsIgnoreCase(token)) {
                    System.out.println(token + "\t\tNOT_OPERATOR");

            //SAMPLE-ONLY
            } else if (token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
                System.out.println(token +  "\t\tIDENTIFIER");
            } else if (token.matches("[+-/*%<>=&|]")) {
                System.out.println(token +  "\t\tOPERATOR");
            } else if (token.matches("\".*\"")) {
                System.out.println(token +  "\t\tSTRING_LITERAL");

            //DELIMETERS
            } else if (token.equals(",")) {
                System.out.println(token +  "\t\tITEM_DELIMETER");
            }else if (token.equals("\\n")) {
                System.out.println(token +  "\t\tSTATEMENT_DELIMETER");

            //ERROR_HANDLING
            } else {
                System.out.println(token +  "\t\tINVALID_TOKEN");
            }
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
