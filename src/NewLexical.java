import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class NewLexical {
    public static void main(String[] args) {
        // Create a file chooser
        JFileChooser inputfileChooser = new JFileChooser();

        //Set the current directory for the input file chooser
        inputfileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        // Show the file chooser dialog and get the selected file
        int result = inputfileChooser.showOpenDialog(null);
        
        // Check if the user selected a file
        if (result == JFileChooser.APPROVE_OPTION) {

            // Get the selected file
            File selectedFile = inputfileChooser.getSelectedFile();

            // Create file paths (For input and Output)
            String filePath = selectedFile.getAbsolutePath();
            String outputFilePath = System.getProperty("user.dir") + File.separator + "symboltable.txt";


            //Check if the file has the appropriate extnesion
            if (!filePath.endsWith(".pgs")) {
                System.out.println("\nInvalid file extension. Please provide a file with .pgs extension.");
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
                System.out.printf("%-12s | %-12s%n", "LEXEME", "TOKEN");
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
                    String token = sc.next();
                    if (token.matches("\\d+")) {
                        System.out.println(token + "\t\tInteger" );
                    
                    //PRONOUNS (POINTERS)
                    } else if (token.equals("This")||token.equals("this")
                            ||token.equals("Was")||token.equals("was")
                            ||token.equals("Each")||token.equals("each")
                            ||token.equals("It")||token.equals("it")) {
                        System.out.printf("%-12s | %-12s%n", token, "PRONOUNS");
    
                
                    //CONJUNCTIONS (IF-ELSE)
                    } else if (token.equals("If")||token.equals("if")) {
                        System.out.printf("%-12s | %-12s%n", token, "CONJUNCTION_IF");
                    } else if (token.equals("Then")||token.equals("then")) {
                        System.out.printf("%-12s | %-12s%n", token, "CONJUNCTION_THEN");  
                    }else  if (token.equals("Else")||token.equals("else")) {
                        System.out.printf("%-12s | %-12s%n", token, "CONJUNCTION_ELSE");
                    
                    //OPERATORS
                        //Arithmetic
                        }else  if (token.equals("+")||
                                token.equals("-")||
                                token.equals("/")||
                                token.equals("*")||
                                token.equals("%")) {
                            System.out.printf("%-12s | %-12s%n", token, "ARITHMETIC_OPERATORS");

                        //Relational
                        }else  if (token.equals("<")||
                                token.equals(">")||
                                token.equals("==")||
                                token.equals("!=")||
                                token.equals("<=")||
                                token.equals(">=")) {
                            System.out.printf("%-12s | %-12s%n", token, "RELATIONAL_OPERATORS");

                        //CONDITIONAL
                        } else if (token.equals("&&")) {
                            System.out.printf("%-12s | %-12s%n", token, "AND_OPERATOR");
                        } else if (token.equals("||")) {
                            System.out.printf("%-12s | %-12s%n", token, "OR_OPERATOR");  
                        }else  if (token.equals("!")) {
                            System.out.printf("%-12s | %-12s%n", token, "NOT_OPERATOR");

                    //SAMPLE-ONLY
                    } else if (token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
                        System.out.printf("%-12s | %-12s%n", token, "IDENTIFIER");
                    } else if (token.matches("[+-/*%<>=&|]")) {
                        System.out.printf("%-12s | %-12s%n", token, "OPERATOR");
                    } else if (token.matches("\".*\"")) {
                        System.out.printf("%-12s | %-12s%n", token, "STRING_LITERAL");

                    //DELIMETERS
                    } else if (token.equals(",")) {
                        System.out.printf("%-12s | %-12s%n", token, "ITEM_DELIMETER");
                    }else if (token.equals("\n")) {
                        System.out.printf("%-12s | %-12s%n", token, "STATEMENT_DELIMETER");

                    //ERROR_HANDLING
                    } else {
                        System.out.printf("%-12s | %-12s%n", token, "INVALID_TOKEN");
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
}
