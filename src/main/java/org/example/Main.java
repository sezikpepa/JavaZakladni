package org.example;

import evaluators.ExpressionCorrectness;
import evaluators.TabloEvaluator;
import expressionModifiers.CNFParser;
import expressionPrinters.FilePrinter;
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


        ArrayList<Expression> expressions = new ArrayList<>();

        //WARNING ----- JE NUTNÉ VYPLNIT CESTU K SOUBORU DO UVOZOVEK
        //NAPŘÍKLAD C:\\cesta\\k\\souboru.text
        var fileParser = new FileParser("C:\\matfyz\\vyrokynajavu.txt");
        var commandLineParser = new CommandLineParser();

        fileParser.loadInput();
        commandLineParser.loadInput();

        expressions.addAll(fileParser.getExpressions());
        expressions.addAll(commandLineParser.getExpressions());

        System.out.println("These expressions were loaded:");
        for (Expression expression : expressions){
            System.out.println(expression);
        }

        System.out.println("--------------------------------------------------");

        System.out.println("Expressions variables: ");
        for (Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" --> ");
            System.out.print(expression.getAllVariables());
            System.out.println();
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Expressions have these negations:");
        for (Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" -------> ");
            expression.Negate();
            System.out.print(expression);
            System.out.println();
        }

        // ULOŽÍME SI NEGOVANÉ VÝROKY DO SOUBORU
        // WARNING NUTNÉ OPĚT DOPLNIT CESTU K SOUBORU
        var filePrinter = new FilePrinter("C:\\matfyz\\vyrokynajavuee.txt");
        filePrinter.Print(expressions);
        System.out.println("Negates expressions were saved into the file");

        System.out.println("--------------------------------------------------");

        System.out.println("We assign logical values to variables of these negated expressions. Note that we need to assign all variables");
        System.out.println("p -> true, q => false");
        var possibleSolution = new HashMap<String, Boolean>();
        possibleSolution.put("p", true);
        possibleSolution.put("q", false);

        for (Expression expression : expressions){
            //Firstly we delete all previously set values
            expression.setAllLogicalValuesToNull();
            expression.setLogicalValuesToVariables(possibleSolution);
        }

        for (Expression expression : expressions){
            expression.evaluateExpression();
            boolean isLogicalyValid = expression.getLogicalValue();
            System.out.println(expression + " is " + isLogicalyValid);
        }
        System.out.println();

        System.out.println("--------------------------------------------------");
        System.out.println("Expressions in their not optimalized CNF form");

        CNFParser cnfParser = new CNFParser();
        for(Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" ----> ");
            cnfParser.setExpression(expression);
            System.out.println(cnfParser.createCNFForm());
        }

        System.out.println("--------------------------------------------------");
        System.out.println("All solutions of the expressions");

        for(Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" has solutions: ");
            System.out.println();
            var validator = new ExpressionCorrectness();
            var printer = new PossibleSolutionsExpression();
            ArrayList<HashMap<String, Boolean>> allSolutions = validator.getAllSolutions(expression);
            System.out.println(printer.printAllSolutions(allSolutions));
            System.out.println("---------------------------------------------------------------------");
        }

        System.out.println("Solutions with information if they have a possible solution");
        for(Expression expression : expressions){
            System.out.print(expression + " is solvable: ");
            TabloEvaluator evaluator = new TabloEvaluator(expression);
            System.out.print(evaluator.isSolvable());
            System.out.println();
        }
    }
}