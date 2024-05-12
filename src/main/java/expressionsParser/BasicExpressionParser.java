package expressionsParser;

import logicalExpressions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public abstract class BasicExpressionParser implements IExpressionParser{


    public static ArrayList<String> OperatorsSigns = new ArrayList<>(Arrays.asList(And.Sign, Or.Sign, Implication.Sign, Equivalent.Sign));

    public abstract void loadInput();

    protected ArrayList<String> tempExpressions = new ArrayList<String>();

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

    private void MergeOperatorAndVariables(Stack<INode> createdNodes, Stack<String> unprocessedSymbols){
        Operator operatorNode = getOperatorNode(unprocessedSymbols.pop());

        INode node2 = createdNodes.pop();
        INode node1 = createdNodes.pop();

        operatorNode.LeftSon = node1;
        operatorNode.RightSon = node2;

        createdNodes.push(operatorNode);
    }

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

    private void ParseVariableInput(String input, Stack<INode> createdNodes){
        if(input.startsWith("¬")){
            input = input.substring(1);
            if(!createdNodes.isEmpty() && createdNodes.peek() instanceof Not){
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
    public ArrayList<Expression> getExpressions() {
        ArrayList<Expression> parsedExpressions = new ArrayList<Expression>();

        for (String expression : tempExpressions){
            parsedExpressions.add(new Expression(ParseExpressionFromString(expression)));
        }

        return parsedExpressions;
    }
}
