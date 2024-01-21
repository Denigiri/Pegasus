import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NewLexical {
    public static void print(String lexeme, String token) {
        System.out.printf("%-12s | %-12s\n", lexeme, token);
    }

    public static boolean matches(String lexeme, String token_pattern) {
        return lexeme.matches(token_pattern) || lexeme.equals(token_pattern);
    }
    public static void main(String[] args) {

        // Get the current directory
        String currentDirectory = System.getProperty("user.dir");

        // Create file paths (For input and Output)
        String filePath = currentDirectory + File.separator + "test.pgs";//CHANGE THE FILE NAME TO THE FILE YOU WANT TO SCAN
        String outputFilePath = currentDirectory + File.separator + "symboltable.txt";

        //Check if the file has the appropriate extnesion
         if (!filePath.endsWith(".pgs")) {
            System.out.println("Invalid file extension. Please provide a file with .pgs extension.");
            return;
        }

        try {
            // Create a FileInputStream for the specified file
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));

            // Use Scanner to read from the file
            Scanner sc = new Scanner(fileInputStream);

            // Create a PrintStream to copy the output of printfunctions
            PrintStream originalOut = System.out;
            PrintStream fileOut = new PrintStream(new File(outputFilePath));
            System.setOut(fileOut);

            // Print the table header
            print("LEXEME", "TOKEN");
            System.out.println("------------------------------");

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
            
        String code = "Each This 3 a was == + If < <=";
        // sc = new Scanner(code);

        
        

            while (sc.hasNext(code)) {
                String lexeme = sc.next();
                if (matches(lexeme, "\\d+")) {
                    print(lexeme, "INTEGER");
                
                //PRONOUNS (POINTERS)
                } else if (matches(lexeme, "\\b([Tt]his|[Ee]ach|[Ww]as)\\b")) {
                    print(lexeme, "POINTERS");
 
            
                //CONJUNCTIONS (IF-ELSE)
                } else if (matches(lexeme,"[Ii]f")) {
                    print(lexeme, "CONJUNCTION_IF");
                } else if (matches(lexeme,"[Tt]hen")) {
                    print(lexeme, "CONJUNCTION_THEN");  
                }else  if (matches(lexeme,"[Ee]lse")) {
                    print(lexeme, "CONJUNCTION_ELSE");
                
                //OPERATORS
                    //Arithmetic
                    }else  if (lexeme.matches("[\\+\\-*/%]")) {
                        print(lexeme, "ARITHMETIC_OPERATORS");

                    //Relational
                    }else  if (matches(lexeme, ".*[<>]=?|!=|==.*")) {
                        print(lexeme, "RELATIONAL_OPERATORS");

                    //CONDITIONAL
                    } else if (matches(lexeme,"&&")) {
                        print(lexeme, "AND_OPERATOR");
                    } else if (matches(lexeme,"||")) {
                        print(lexeme, "OR_OPERATOR");  
                    }else  if (matches(lexeme,"!")) {
                        print(lexeme, "NOT_OPERATOR");

                //SAMPLE-ONLY
                } else if (lexeme.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
                    print(lexeme, "IDENTIFIER");
                } else if (lexeme.matches("[+-/*%<>=&|]")) {
                    print(lexeme, "OPERATOR");
                } else if (lexeme.matches("\".*\"")) {
                    print(lexeme, "STRING_LITERAL");

                //DELIMETERS
                } else if (matches(lexeme,",")) {
                    print(lexeme, "ITEM_DELIMETER");
                }else if (matches(lexeme,"\\n")) {
                    print(lexeme, "STATEMENT_DELIMETER");

                //ERROR_HANDLING
                } else {
                    print(lexeme, "INVALID_TOKEN");
                }
            }
        
        //Close Scanner nd InputStream
        sc.close();
        fileInputStream.close();

        // Reset the standard output
        System.setOut(originalOut);
        System.out.println("\nOutput saved to: " + outputFilePath);

        }//ERROR HANDLING FOR FILE
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
