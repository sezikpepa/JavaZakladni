package org.example;

import evaluators.ExpressionCorrectness;
import evaluators.TabloEvaluator;
import expressionModifiers.CNFParser;
import expressionPrinters.PossibleSolutionsExpression;
import expressionsParser.CommandLineParser;
import expressionsParser.FileParser;
import logicalExpressions.Expression;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class of the library, here it is possible to use provided classes and functions
 */
public class Main {

    /**
     * Creates new Main class
     */
    public Main(){

    }

    /**
     * Method which is run at the start of the program
     * @param args Command line arguments
     */
    public static void main(String[] args) throws Exception {
        //WARNING insert file path
        var parser = new FileParser("C:\\matfyz\\vyrokynajavu.txt");

        //LOAD OF EXPRESSIONS FROM FILE
        parser.loadInput();
        var parsed = parser.getExpressions();
//        for (Expression expression : parsed) {
//            expression.Negate();
//        }
//
        //SETS LOGICAL VALUES TO VARIABLES IN EXPRESSIONS - ONLY FOR EXPRESSION WITH VARIABLES b,c,d
//        var possibleSolution = new HashMap<String, Boolean>();
//        possibleSolution.put("b", true);
//        possibleSolution.put("c", false);
//        possibleSolution.put("d", true);
//
        //ALL SOLUTIONS OF THE EXPRESSION
//        ExpressionCorrectness evaluator = new ExpressionCorrectness();
//        ArrayList<HashMap<String, Boolean>> allSolutions = new ArrayList<>();
//        for (Expression expression : parsed){
//            allSolutions = evaluator.getAllSolutions(expression);
//        }
//        var printer = new PossibleSolutionsExpression();
//        printer.printAllSolutions(allSolutions);
//
        //PRINTS TO CONSOLE ALL POSSIBLE SOLUTIONS OF THE EXPRESSION
//        for (Expression expression : parsed){
//            var formParser = new CNFParser();
//            formParser.setExpression(expression);
//            System.out.println(expression);
//            var result = formParser.createCNFForm();
//            System.out.println(result);
//        }
//
        //TABLO EVALUATION OF THE EXPRESSION
        for (Expression expression : parsed) {
            System.out.println(expression);
            TabloEvaluator evaluator = new TabloEvaluator(expression);
            System.out.println(evaluator.isSolvable());
//
//        }


        }
    }
}