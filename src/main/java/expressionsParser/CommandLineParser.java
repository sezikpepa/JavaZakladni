package expressionsParser;

import logicalExpressions.INode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CommandLineParser extends BasicExpressionParser{

    public CommandLineParser(){

    }

    public void loadInput(){
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
