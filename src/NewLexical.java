import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFileChooser;


public class NewLexical {
    public static void print(String lexeme, String token) {
        System.out.printf("%-20s | %-20s\n", lexeme, token);
    }

    public static void error(String lexeme, String token) {
        System.out.printf("%-20s | %-20s\n", lexeme, "INVALID: \"" + token + "\" is mispelled");
    }

    public static boolean matches(String lexeme, String token_pattern) {
        return lexeme.matches(token_pattern) || lexeme.equals(token_pattern);
       
    }

    public static boolean isMispelled(String lexeme, String token) {
        int levenshtein_distance = calculate(lexeme, token);

        if (levenshtein_distance ==1) {
            return true;
        } 
        return false;
    }

    static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] 
                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), 
                    dp[i - 1][j] + 1, 
                    dp[i][j - 1] + 1);
                }
            }
        }
        
        return dp[x.length()][y.length()];
    }
    public static int costOfSubstitution(char a, char b) {
        // String keyboard = "qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
        // int distance;
        // if (a ==b) {return 0;}
        // else {
        //     // System.out.println(a + " " + b);
        //     distance = (keyboard.indexOf(b) - keyboard.indexOf(a)) % 10;
        // }
        // return distance;
        return a == b ? 0 : 1;
    }
    public static int min(int... numbers) {
        return Arrays.stream(numbers)
          .min().orElse(Integer.MAX_VALUE);
    }


    public static boolean isToken(String lexeme, String token){
        return   (lexeme.toLowerCase().equals(token) && 
                 Character.toLowerCase(lexeme.charAt(0)) == Character.toLowerCase(token.charAt(0)) && 
                 (lexeme.substring(1).equals(lexeme.substring(1).toLowerCase())) );
    }

    public static String scanMultiLineComment(String lexeme, Scanner sc) {
        StringBuilder wholeLexeme = new StringBuilder();
        wholeLexeme.append(lexeme);
    
        while (sc.hasNext()) {
            String next = sc.next();
            wholeLexeme.append(next);
    
            // Check if the current lexeme ends with the specified end delimiter
            if (wholeLexeme.toString().endsWith("*/")) {
                break;
            }
        }
        return wholeLexeme.toString();
    }

    public static String matchNext(String lexeme, String separators, String end_delimiter, Scanner sc) {
        StringBuilder wholeLexeme = new StringBuilder();
        wholeLexeme.append(lexeme);
        
        sc.useDelimiter("");
        while (sc.hasNext()) {
            String next = sc.next();
            if (next.contains(String.valueOf(end_delimiter))) {
                // Found closing quote
                wholeLexeme.append(next);
                break;
            } else {
                sc.useDelimiter(separators);
                wholeLexeme.append(next);
            }
        }
        return wholeLexeme.toString();
    }
    
    static int commas = 0;
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
            print("LEXEME", "TOKEN");
            System.out.println("------------------------------");

            //split code by...
            sc.useDelimiter(//whitespace between keywords
                            "(?<=.)\s+"
                            //if before is integer + whitespace, and after is operator
                            + "|(?<=\\d\\s+)(?=[()+-/*%<>=&|])" 
                            // //if before is operator + whitespace, and after is integer
                            + "|(?<=[()+-/*%<>=&|]\\s+)(?=\\d)"
                            // //if before is integer, and after is operator but not period
                            + "|(?<=\\d)(?=[()+-/*%<>=&|])(?![.])"
                            // //if before is operator but not a period, and after is integer
                            + "|(?<=[()+-/*%<>=&|])(?<![.])(?=\\d)"
                            //separate literals with commas with or without spaces
                            + "|(?=,)|(?<=,)"
                            //exclude single-line & multi-line comments 
                            + "|\\s*(//.*|/\\*(.|\\R)*?\\*/)\\s*"
                            //separates newline from other words
                            + "|\\s(?=[^\"]*\"[^\"]*\")");
                            //separate multi-line comments
                            // + "|(?==/*.*/)"
            
            

            while (sc.hasNext()) {
                String lexeme = sc.next();

                
                //FLEXIBLE DATA TYPES
                if (isToken(lexeme, "could")) { 
                    print(lexeme, "DATATYPE_LIMITER");
                } else if (isMispelled(lexeme, "could")) {
                    error(lexeme, "could");
                } else if(isToken(lexeme, "only")) { 
                    print(lexeme, "DATATYPE_LIMITER");
                } else if(isMispelled(lexeme, "only")) { 
                    error(lexeme, "only");
                
                } else if (isToken(lexeme, "of")) { 
                    print(lexeme, "RELATIONALS_OF");
                } else if (isToken(lexeme, "is")) {
                    print(lexeme, "RELATIONALS_IS");
                //NOISE WORDS
                }else if(isToken(lexeme, "a") ||
                            isToken(lexeme, "an")) {
                            print(lexeme, "NOISE_WORDS");

                //ASSIGNMENT
                } else if(matches(lexeme, "Let")) { 
                    print(lexeme, "VARIABLE_INITIALIZER");
                
                } else if(matches(lexeme, "be")) { 
                    print(lexeme, "ASSIGNMENT_KEYWORD");
                } else if(isMispelled(lexeme, "be")) { 
                    error(lexeme, "be");
                
                } else if(matches(lexeme, "is")) { 
                    print(lexeme, "ASSIGNMENT_KEYWORD");
                

                
                //PRONOUNS (POINTERS)
                } else if(isToken(lexeme, "this") ||
                          isToken(lexeme, "each") ||
                          isToken(lexeme, "it") ||
                          isToken(lexeme, "was")) {
                    print(lexeme, "POINTERS");
                } else if(isMispelled(lexeme, "this") ||
                          isMispelled(lexeme, "each") ||
                          isMispelled(lexeme, "it") ||
                          isMispelled(lexeme, "was")) { 
                    error(lexeme, lexeme);
                
                //TYPEDEF
                } else if(matches(lexeme, "Remember")) { 
                    print(lexeme, "NAME_CONVERTER");
                } else if(isMispelled(lexeme, "Remember")) { 
                    error(lexeme, "Remember");            
                } else if(matches(lexeme, "Shorten")) { 
                    print(lexeme, "NAME_CONVERTER");
                } else if(isMispelled(lexeme, "Shorten")) { 
                    error(lexeme, "Shorten");
                    
                } else if(matches(lexeme, "Represent")) { 
                    print(lexeme, "NAME_CONVERTER");
                } else if(isMispelled(lexeme, "Represent")) { 
                    error(lexeme, "Represent");
                    

                } else if(matches(lexeme, "mean")) { 
                    print(lexeme, "KEYWORD_CONVERTER");
                } else if(isMispelled(lexeme, "mean")) { 
                    error(lexeme, "mean");

                } else if(isToken(lexeme, "hide") ||
                          isToken(lexeme, "insert")) { 
                    error(lexeme, lexeme);
                //PREPOSITIONS
                } else if(matches(lexeme, "to")) { 
                    print(lexeme, "PREPOSITION_TO");

                } else if(matches(lexeme, "as")) { 
                    print(lexeme, "PREPOSITION_AS");

                } else if(matches(lexeme, "in")) { 
                    print(lexeme, "PREPOSITION_IN");
                
                } else if(matches(lexeme, "one")) {
                    print(lexeme, "QUANTIFIER");
                } else if(isMispelled(lexeme, "time")) {
                    error(lexeme, "time");
                
                //ADJECTIVES (Data Type & Variable Modifiers)
                } else if (isToken(lexeme, "always")) {
                    print(lexeme, "CONSTANT");
                } else if(isMispelled(lexeme, "always")) {
                    error(lexeme, "always");
                
                //NUMERICAL
                } else if (isToken(lexeme, "discrete")) {
                    print(lexeme, "DISCRETE");
                } else if(isMispelled(lexeme, "discrete")) {
                    error(lexeme, "discrete");
                
                } else if (isToken(lexeme, "continuous")) {
                    print(lexeme, "CONTINUOUS");
                } else if(isMispelled(lexeme, "continuous")) { 
                    error(lexeme, "continuous");

                //CATEGORICAL
                } else if (isToken(lexeme, "order")) {
                    print(lexeme, "ORDINAL");
                } else if(isMispelled(lexeme, "order")) { 
                    error(lexeme, "order");

                } else if (isToken(lexeme, "boolean")) {
                    print(lexeme, "BINARY");

                //TASKS (Functions)
                } else if (isToken(lexeme, "show")) {
                    print(lexeme, "PRINT");
                } else if(isMispelled(lexeme, "show")) { 
                    error(lexeme, "show");
                } else if (isToken(lexeme, "ask")) {
                    print(lexeme, "SCAN");
                } else if(isMispelled(lexeme, "ask")) { 
                    error(lexeme, "ask");
                } else if (isToken(lexeme, "read")) {
                    print(lexeme, "READ");
                } else if(isMispelled(lexeme, "read")) { 
                    error(lexeme, "read");
                } else if (isToken(lexeme, "write")) {
                    print(lexeme, "WRITE");
                } else if(isMispelled(lexeme, "write")) { 
                    error(lexeme, "write");
                } else if (isToken(lexeme, "open")) {
                    print(lexeme, "OPEN");
                } else if(isMispelled(lexeme, "open")) { 
                    error(lexeme, "open");
                } else if (isToken(lexeme, "close")) {
                    print(lexeme, "CLOSE");
                } else if(isMispelled(lexeme, "close")) { 
                    error(lexeme, "close");
                } else if (isToken(lexeme, "change")) {
                    print(lexeme, "CHANGE");
                } else if(isMispelled(lexeme, "change")) { 
                    error(lexeme, "change");
                } else if (isToken(lexeme, "spell")) {
                    print(lexeme, "SPELL");
                } else if(isMispelled(lexeme, "spell")) { 
                    error(lexeme, "spell");
                } else if (isToken(lexeme, "count")) {
                    print(lexeme, "COUNT");
                } else if(isMispelled(lexeme, "count")) { 
                    error(lexeme, "count");
                

                //CONJUNCTIONS (IF-ELSE)
                } else if (isToken(lexeme, "if")) { 
                    print(lexeme, "CONJUNCTION_IF");
                } else if(isMispelled(lexeme, "if")) { 
                    error(lexeme, "if");
                } else if (isToken(lexeme, "then")) {
                    print(lexeme, "CONJUNCTION_THEN");  
                } else if(isMispelled(lexeme, "then")) { 
                    error(lexeme, "then");
                } else if (isToken(lexeme,"else")) {
                    print(lexeme, "CONJUNCTION_ELSE");
                } else if(isMispelled(lexeme, "else")) { 
                    error(lexeme, "else");
                
                //UNION
                } else if (isToken(lexeme, "union")) {
                    print(lexeme, "UNION");


                //OPERATORS
                    //Arithmetic
                    } else  if ("+-*/%".contains(lexeme) && (lexeme.length() == 1)) {
                        print(lexeme, "ARITHMETIC_OPERATORS");

                    //Relational
                    } else  if (matches(lexeme, ".*[<>]=?|!=|==.*")) {
                        print(lexeme, "RELATIONAL_OPERATORS");

                    //CONDITIONAL
                    } else if (lexeme.equals("&&")) {
                        print(lexeme, "AND_OPERATOR");
                    } else if (lexeme.equals("||")) {
                        print(lexeme, "OR_OPERATOR");  
                    } else  if (matches(lexeme, "!")) {
                        print(lexeme, "NOT_OPERATOR");
                    } else if (lexeme.contains("(") && !lexeme.contains(")")) {
                        print(lexeme, "INVALID: UNMATCHED PARENTHESES");
                    } else if (lexeme.contains(")") && !lexeme.contains("(")) {
                        print(lexeme, "INVALID: UNMATCHED PARENTHESES");

                    

                //DELIMETERS
                } else if (matches(lexeme,",")) {
                    print(lexeme, "DELIMITER_COMMA");
                } else if (matches(lexeme,"\t")) {
                    print(lexeme, "DELIMITER_TAB");
                } else if (matches(lexeme,"\n")) {
                    print(lexeme, "DELIMITER_NEWLINE");
                } else if (matches(lexeme,"\s")) {
                    print(lexeme, "WARNING: EXCESS WHITESPACES");
                
                //LITERALS
                } else if (matches(lexeme, "[0-9]+")) {
                    print(lexeme, "INTEGER_LITERAL");
                } else if (matches(lexeme, "\\d+(?:\\.\\d+)")) {
                    print(lexeme, "FLOAT_LITERAL");
                } else if (matches(lexeme, "\\d+(?:\\.\\d+).")) {
                    print(lexeme, "INVALID: EXCESS FLOATING POINT DECIMALS");
                } else if (matches(lexeme, "true|false")) {
                    print(lexeme, "BOOLEAN_LITERAL");
                } else if (lexeme.length() == 1) {
                    print(lexeme, "CHARACTER_LITERAL");
                }  else if (isToken(lexeme, "null")) {
                    print(lexeme, "NULL_LITERAL");
                } else if (matches(lexeme, "\"\s*\\w\s*\"")) {
                    print(lexeme, "STRING");

                //COMMENTS
                // } else if (lexeme.contains("//")) {
                //     print(lexeme, "COMMENTS");
                // } else if (lexeme.contains("/**/")) {
                //     // String literal = scanMultiLineComment(lexeme, sc);
                //     print(lexeme, "MULTI-LINE COMMENT");

                //IDENTIFIER
                } else if (matches(lexeme,"[a-zA-Z_\\p{Sc}][a-zA-Z0-9._]*")) {
                    print(lexeme, "IDENTIFIER");
                } else if (matches(lexeme,"[0-9][a-zA-Z0-9._]*")) {
                    print(lexeme, "INVALID: identifier cannot start with number");
                } else {
                    print(lexeme, "INVALID: unrecognized characters");
                }
            }
        
            //Close Scanner and InputStream
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
