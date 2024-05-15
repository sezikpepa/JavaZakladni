package expressionsParser;

import logicalExpressions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Provides basic methods which are then used in specific parsers
 */
public abstract class BasicExpressionParser implements IExpressionParser{

    /**
     * Creates new parser
     */
    public BasicExpressionParser(){

    }

    /**
     * List of all string representations of operators which are supported by parsers
     */
    public static ArrayList<String> OperatorsSigns = new ArrayList<>(Arrays.asList(And.Sign, Or.Sign, Implication.Sign, Equivalent.Sign));

    public abstract void loadInput();

    /**
     * loadInput method loads expressions from different places. It stores them into this variable, so the expression can be
     * used later - for example parsing into tree structure.
     */
    protected ArrayList<String> tempExpressions = new ArrayList<String>();

    /**
     * If the symbol in the expression is operator symbol, Operator node is created. This method decides which node should
     * be created
     * @param symbol Text representation of the operator
     * @return Operator node based on the input string
     */
    private Operator getOperatorNode(String symbol){
        if(OperatorsSigns.contains(symbol)){
            if(symbol.equals(And.Sign)){
                return new And();
            }
            else if(symbol.equals(Or.Sign)){
                return new Or();
            }
            else if(symbol.equals(Implication.Sign)){
                return new Implication();
            }
            else if(symbol.equals(Equivalent.Sign)){
                return new Equivalent();
            }
        }

        return null;
    }

    /**
     * If there is enough created nodes for operator and an available operator, nodes should be placed as the children of the operator
     * @param createdNodes Variables or operators which are already transformed into tree structure
     * @param unprocessedSymbols Operators which do not have variables, the most recent one will be given children
     */
    private void MergeOperatorAndVariables(Stack<INode> createdNodes, Stack<String> unprocessedSymbols){
        Operator operatorNode = getOperatorNode(unprocessedSymbols.pop());

        INode node2 = createdNodes.pop();
        INode node1 = createdNodes.pop();

        operatorNode.LeftSon = node1;
        operatorNode.RightSon = node2;

        createdNodes.push(operatorNode);
    }

    /**
     * Every input of the string representation of the expression need to be parsed into tree representation of the expression.
     * This method based on the meaning of each character in the string transforms the string into tree.
     * @param expressionString String representation of the expression
     * @return Expression in tree format
     */
    public INode ParseExpressionFromString(String expressionString){
        String[] splittedExpression = expressionString.split(" ");

        Stack<INode> createdNodes = new Stack<INode>();
        Stack<String> unprocessedSymbols = new Stack<String>();

        for (String part : splittedExpression){
            if(part.equals("(")){ //ok
                unprocessedSymbols.push(part);
            }
            else if(part.equals("¬")){
                unprocessedSymbols.push(part);
            }
            else if(part.equals(")")){
                while(!unprocessedSymbols.peek().equals("(")){
                    MergeOperatorAndVariables(createdNodes, unprocessedSymbols);
                }

                unprocessedSymbols.pop();

                while (!unprocessedSymbols.isEmpty() && unprocessedSymbols.peek().equals("¬")){
                    unprocessedSymbols.pop();
                    var temp = createdNodes.pop();
                    var notNode = new Not(temp);
                    createdNodes.push(notNode);
                }
            }
            else if(OperatorsSigns.contains(part)){
               while (!unprocessedSymbols.empty() && !unprocessedSymbols.peek().equals("(")){
                   MergeOperatorAndVariables(createdNodes, unprocessedSymbols);
               }
               unprocessedSymbols.push(part);
            }
            else{ //ok
                ParseVariableInput(part, createdNodes);
            }
        }

        while (createdNodes.size() > 1){
            MergeOperatorAndVariables(createdNodes, unprocessedSymbols);
        }
        return createdNodes.pop();
    }

    /**
     * If the character is variable it needs specific handling. It could be assigned to an operator or to a Not node, as the variable
     * can have negation sign in from of it.
     * @param input Variable name
     * @param createdNodes Already created tree nodes
     */
    private void ParseVariableInput(String input, Stack<INode> createdNodes){
        if(input.startsWith("¬")){
            input = input.substring(1);
            if(!createdNodes.isEmpty() && createdNodes.peek() instanceof Not && ((Not)createdNodes.peek()).LeftSon == null){
                Not fromStack = (Not)createdNodes.pop();
                fromStack.setNegatablePart(new Not());
            }
            else{
                createdNodes.push(new Not());
            }
            ParseVariableInput(input, createdNodes);
        }
        else{
            if(!createdNodes.isEmpty() && createdNodes.peek() instanceof Not && ((Not)createdNodes.peek()).LeftSon == null){
                Not fromStack = (Not)createdNodes.pop();
                fromStack.setNegatablePart(new Variable(input));
                createdNodes.push(fromStack);
            }
            else{
                createdNodes.push(new Variable(input));
            }
        }
    }

    @Override
    public ArrayList<Expression> getExpressions() throws Exception {
        ArrayList<Expression> parsedExpressions = new ArrayList<Expression>();

        String currentExpression = "";
        try{
            for (String expression : tempExpressions){
                currentExpression = expression;
                parsedExpressions.add(new Expression(ParseExpressionFromString(expression)));
            }
        }
        catch (Exception e) {
            throw new Exception("Provided expression is not valid: " + currentExpression);
        }


        return parsedExpressions;
    }
}
