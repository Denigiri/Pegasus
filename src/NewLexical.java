import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class NewLexical {
    public static void print(String lexeme, String token) {
        System.out.printf("%-12s | %-12s\n", lexeme, token);
    }

    public static boolean matches(String lexeme, String token_pattern) {
        return lexeme.matches(token_pattern) || lexeme.equals(token_pattern);
    }

    public static boolean matchRegex(String lexeme, String regex) {
        int len = regex.length();
        int lexemeIndex = 0;  // Track position in lexeme
        List<Character> charactersToCheck = new ArrayList<>();  // Store characters within brackets
        StringBuilder nonBracketedText = new StringBuilder();  // Store text outside brackets
        boolean insideBrackets = false;
    
        for (int i = 0; i < len; i++) {
            char ch = regex.charAt(i);
    
            if (ch == '[') {
                insideBrackets = true;
                // Check for matching characters within brackets
                if (!nonBracketedText.isEmpty()) {
                    if (!lexeme.startsWith(nonBracketedText.toString(), lexemeIndex)) {
                        return false;  // Mismatch outside brackets
                    }
                    lexemeIndex += nonBracketedText.length();  // Advance lexeme position
                }
                nonBracketedText.setLength(0);  // Reset for next bracketed section
            } else if (ch == ']') {
                insideBrackets = false;
            } else if (insideBrackets) {
                charactersToCheck.add(ch);
            } else {
                nonBracketedText.append(ch);
            }
        }

        // Check for matching characters within brackets
        if (!charactersToCheck.isEmpty()) {
            for (char c : lexeme.toCharArray()) {
                if (charactersToCheck.contains(c)) {
                    return true;  // Match found
                }
            }
            charactersToCheck.clear();  // Reset for next bracketed section
        }
    
        // Check for final substring outside brackets
        if (!nonBracketedText.isEmpty()) {
            if (!lexeme.startsWith(nonBracketedText.toString(), lexemeIndex)) {
                return false;  // Mismatch outside brackets
            }
        }
        
        return true;  // All matches found
    }

    public static boolean matchString() {
        return true;
    }

    
    public static void main(String[] args) {
        // // Create a file chooser
        // JFileChooser inputfileChooser = new JFileChooser();

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
                String lexeme = sc.next();
                if (matches(lexeme, "\\d+")) {
                    print(lexeme, "INTEGER");
                
                //PRONOUNS (POINTERS)
                } else if (matches(lexeme, "\\b([Tt]his|[Ee]ach|[Ww]as)\\b")) {
                    print(lexeme, "POINTERS");
 
            
                //CONJUNCTIONS (IF-ELSE)
                } else if (matchRegex(lexeme,"[Ii]f")) {
                    print(lexeme, "CONJUNCTION_IF");
                } else if (matchRegex(lexeme,"[Tt]hen")) {
                    print(lexeme, "CONJUNCTION_THEN");  
                }else  if (matchRegex(lexeme,"[Ee]lse")) {
                    print(lexeme, "CONJUNCTION_ELSE");
                
                //OPERATORS
                    //Arithmetic
                    }else  if (matchRegex(lexeme,"[\\+\\-*/%]")) {
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

                //DELIMETERS
                } else if (matches(lexeme,",")) {
                    print(lexeme, "ITEM_DELIMETER");
                }else if (matches(lexeme,"\n")) {
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
                } else if (matches(lexeme,"\".*\"")) {
                    print(lexeme, "STRING_LITERAL");

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
