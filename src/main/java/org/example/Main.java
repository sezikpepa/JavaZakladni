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
        //NAPŘÍKLAD C:\\cesta\\k\\souboru.txt
        var fileParser = new FileParser("");
        var commandLineParser = new CommandLineParser();

        fileParser.loadInput();
        //ODKOMENTOVAT PRO POUŽÍVÁNÍ PŘÍKAZOVÉ ŘÁDKY, PRO JEDNODUCHOST LZE VŠE DÁVAT DO SOUBORU
        //VÝROKY LZE VKLÁDAT PO ŘÁDCÍCH, VSTUP BUDE UKONČEN PO ZADÁNÍ PRÁZDNÉ ŘÁDKY
        //commandLineParser.loadInput();

        expressions.addAll(fileParser.getExpressions());
        expressions.addAll(commandLineParser.getExpressions());

        System.out.println("These expressions were loaded:");
        for (Expression expression : expressions){
            System.out.println(expression);
        }

        System.out.println("--------------------------------------------------");

        System.out.println("Expressions' variables: ");
        for (Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" --> ");
            System.out.print(expression.getAllVariables());
            System.out.println();
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("Expression is satisfied by every model - proof by tablo method");
        for(Expression expression : expressions){
            System.out.print(expression + " -------> ");
            TabloEvaluator evaluator = new TabloEvaluator(expression);
            System.out.print(evaluator.validForEveryModel());
            System.out.println();
        }

        System.out.println("--------------------------------------------------");
        System.out.println("All solutions of expressions");

        for(Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" has solutions: ");
            System.out.println();
            var validator = new ExpressionCorrectness();
            var printer = new PossibleSolutionsExpression();
            ArrayList<HashMap<String, Boolean>> allSolutions = validator.getAllSolutions(expression);
            System.out.println(printer.printAllSolutions(allSolutions));
            System.out.println("-----------------------------------------------------------------------------------");
        }

        System.out.println("We assign logical values to variables of these negated expressions. Note that we need to assign all variables");
        System.out.println("p -> true, q => false, r => false");
        var possibleSolution = new HashMap<String, Boolean>();
        possibleSolution.put("p", true);
        possibleSolution.put("q", false);
        possibleSolution.put("r", false);

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
        // NAPŘÍKLAD C:\\cesta\\k\\souboru.txt
        var filePrinter = new FilePrinter("");
        filePrinter.Print(expressions);
        System.out.println("--------------------------------------------------");

        System.out.println("Negated expressions were saved into the file");

        System.out.println("--------------------------------------------------");

        System.out.println("Negated expressions in their not optimalized CNF form");

        CNFParser cnfParser = new CNFParser();
        for(Expression expression : expressions){
            System.out.print(expression);
            System.out.print(" ----> ");
            cnfParser.setExpression(expression);
            System.out.println(cnfParser.createCNFForm());
        }
    }
}