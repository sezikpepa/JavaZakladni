package expressionsParser;

import logicalExpressions.INode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Loads expression from command line and parse them into the tree structure
 */
public class CommandLineParser extends BasicExpressionParser{

    /**
     * Creates new instance of the parser
     */
    public CommandLineParser(){

    }

    /**
     * It reads the input by lines. Every line is different expression
     */
    public void loadInput(){
        System.out.println("Insert your expressions. After inserting a blank line, input will be ended: ");
        Scanner consoleReader = new Scanner(System.in);

        while (consoleReader.hasNextLine()){
            String expression = consoleReader.nextLine();

            if(expression.isEmpty()){
                break;
            }
            else{
                tempExpressions.add(expression);
            }
        }
    }

}
