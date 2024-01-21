import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;


public class NewLexical {
    public static void print(String lexeme, String token) {
        System.out.printf("%-12s | %-12s\n", lexeme, token);
    }

    public static boolean matches(String lexeme, String token_pattern) {
        return lexeme.matches(token_pattern) || lexeme.equals(token_pattern);
    }

    public static boolean isToken(String lexeme, String token){
        return   (lexeme.toLowerCase().equals(token) && 
                 Character.toLowerCase(lexeme.charAt(0)) == Character.toLowerCase(token.charAt(0)) && 
                 (lexeme.substring(1).equals(lexeme.substring(1).toLowerCase())) );
    }
    
    public static void main(String[] args) {
        // Create a file chooser
        JFileChooser inputfileChooser = new JFileChooser();

        // //Set the current directory for the input file chooser
        // inputfileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        // // Show the file chooser dialog and get the selected file
        // int result = inputfileChooser.showOpenDialog(null);
        
        // // Check if the user selected a file
        // if (result == JFileChooser.APPROVE_OPTION) {

        //     // Get the selected file
        //     File selectedFile = inputfileChooser.getSelectedFile();

        //     // Create file paths (For input and Output)
        //     String filePath = selectedFile.getAbsolutePath();
        //     String outputFilePath = System.getProperty("user.dir") + File.separator + "symboltable.txt";


        //     //Check if the file has the appropriate extnesion
        //     if (!filePath.endsWith(".pgs")) {
        //         System.out.println("\nInvalid file extension. Please provide a file with .pgs extension.");
        //         return;
        //     }

        // try {
            // // Create a FileInputStream for the specified file
            // FileInputStream fileInputStream = new FileInputStream(new File(filePath));

            // // Use Scanner to read from the file
            // Scanner sc = new Scanner(fileInputStream);

            // // Create a PrintStream to copy the output of printfunctions
            // PrintStream originalOut = System.out;
            // PrintStream fileOut = new PrintStream(new File(outputFilePath));
            // System.setOut(fileOut);

            String code = "Each This 3+3 a was == + If < <= 1aaa \"hey hoy\"";
            Scanner sc = new Scanner(code);

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
            
            

        
        

            while (sc.hasNext()) {


            while (sc.hasNext()) {
                String lexeme = sc.next();
                if (matches(lexeme, "\\d+")) {
                    print(lexeme, "INTEGER");
                
                //PRONOUNS (POINTERS)
                //} else if (matches(lexeme, "\\b([Tt]his|[Ee]ach|[Ww]as)\\b")) {
                //} else if (isToken(lexeme, "this") ||
                //           isToken(lexeme, "each") ||
                //           isToken(lexeme, "was") ||
                //           isToken(lexeme, "it")){
                } else if (isToken(lexeme, "was")) {
                    print(lexeme, "POINTERS");
 
            
                //CONJUNCTIONS (IF-ELSE)
                //} else if (matches(lexeme,"[Ii]f")) {
                } else if (isToken(lexeme, "if")) { 
                    print(lexeme, "CONJUNCTION_IF");

                } else if (isToken(lexeme, "if")) {
                    print(lexeme, "CONJUNCTION_THEN");  

                } else if (isToken(lexeme,"else")) {
                    print(lexeme, "CONJUNCTION_ELSE");
                
                //UNION
                } else if (isToken(lexeme, "union")) {
                    print(lexeme, "UNION");


                //OPERATORS
                    //Arithmetic
                    } else  if ("+-*/%".contains(lexeme)) {
                        print(lexeme, "ARITHMETIC_OPERATORS");

                    //Relational
                    } else  if (matches(lexeme, ".*[<>]=?|!=|==.*")) {
                        print(lexeme, "RELATIONAL_OPERATORS");

                    //CONDITIONAL
                    } else if (lexeme.equals("&&")) {
                        print(lexeme, "AND_OPERATOR");
                    } else if (matches(lexeme,"||")) {
                        print(lexeme, "OR_OPERATOR");  
                    } else  if (matches(lexeme,"!")) {
                        print(lexeme, "NOT_OPERATOR");

                //DELIMETERS
                } else if (matches(lexeme,",")) {
                    print(lexeme, "ITEM_DELIMETER");
                }else if (matches(lexeme,"\n")) {
                } else if (matches(lexeme,"\\n")) {
                    print(lexeme, "STATEMENT_DELIMETER");

                //SAMPLE-ONLY
                } else if (matches(lexeme,"[a-zA-Z][a-zA-Z0-9_]*")) {
                    print(lexeme, "IDENTIFIER");
                } else if (matches(lexeme,"\"[a-zA-Z][a-zA-Z0-9_]*\s\"")) {
                    print(lexeme, "STRING_DELIMITER"); 
                } else if (matches(lexeme,"[a-z]*")) {
                    print(lexeme, "LOWERCASE_LETTERS");
                } else if (matches(lexeme,"[A-Z]*")) {
                    print(lexeme, "UPPERCASE_LETTERS");
                } else if (matches(lexeme,"[a-z]*")) {
                    print(lexeme, "UPPERCASE_LETTERS");
                } else if (matches(lexeme,"[0-9][a-zA-Z0-9_]*")) {
                    print(lexeme, "INVALID_IDENTIFIER");
                } else if (lexeme.charAt(0)=='\"') {
                    StringBuilder stringLiteral = new StringBuilder();
                    stringLiteral.append(lexeme);

                    while (sc.hasNext()) {
                        String next = sc.next();
                        
                        if (next.contains(String.valueOf("\""))) {
                            // Found closing quote
                            stringLiteral.append("\s" + next);
                            print(stringLiteral.toString(), "STRING_LITERAL");
                            break;
                        } else {
                            stringLiteral.append(next);
                        }
                    }
                } else if (lexeme.contains("\\\\")) {
                    StringBuilder singleComment = new StringBuilder();
                    singleComment.append(lexeme);
                    
                    sc.useDelimiter("");
                    while (sc.hasNext()) {
                        String next = sc.next();
                        if (next.contains(String.valueOf("\n"))) {
                            // Found closing quote
                            singleComment.append(next);
                            break;
                        } else {
                            sc.useDelimiter("\n");
                            singleComment.append(next);
                        }
                    }
                    print(singleComment.toString(), "COMMENTS");
                //ERROR_HANDLING
                } else {
                    print(lexeme, "UNRECOGNIZED CHARACTERS");
                }
            }
        
            //Close Scanner and InputStream
            sc.close();
            // fileInputStream.close();

            // // Reset the standard output
            // System.setOut(originalOut);
            // System.out.println("\nOutput saved to: " + outputFilePath);

            // }//ERROR HANDLING FOR FILE
            // catch (FileNotFoundException e) {
            //     System.out.println("File not found: " + filePath);
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
        }
    }
// }

}