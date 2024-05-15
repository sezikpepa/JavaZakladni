package evaluators;

import logicalExpressions.Expression;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Checks different forms of logical validity of expressions
 */
public class ExpressionCorrectness {

    /**
     * Creates new object, which checks different forms of logical validity of expressions
     */
    public ExpressionCorrectness(){

    }

    /**
     * Decides if the provided expression is true or false
     * @param expression Expression tree
     * @param possibleSolution Possible solution, where variables are the key and in the value section are logical values of these variables.
     * @return Returns true if the possible solution satisfy the expression, otherwise false
     */
    public boolean isExpressionTrue(Expression expression, HashMap<String, Boolean> possibleSolution){
        expression.setAllLogicalValuesToNull();
        expression.setLogicalValuesToVariables(possibleSolution);
        expression.evaluateExpression();

        return expression.getLogicalValue();
    }

    /**
     * Checks for all solutions of the expression
     * @param expression Expression which is checked for solutions
     * @return all solutions which satisfy the expression
     */
    public ArrayList<HashMap<String, Boolean>> getAllSolutions(Expression expression){

        ArrayList<HashMap<String, Boolean>> solutions = new ArrayList<>();

        expression.setAllLogicalValuesToNull();
        ArrayList<String> allVariables = expression.getAllVariables();


        HashMap<String, Boolean> variablesWithValues = new HashMap<>();
        for (String variable : allVariables){
            variablesWithValues.put(variable, false);
        }


        for(int option = 0; option < Math.pow(2, allVariables.size()); option++){
            for (int j = 0; j < allVariables.size(); j++){
                boolean logicalValue = ((option >> j) & 1) == 1;
                String variableName = allVariables.get(j);
                variablesWithValues.put(variableName, logicalValue);
            }

            if(isExpressionTrue(expression, variablesWithValues)){
                solutions.add((HashMap<String, Boolean>)variablesWithValues.clone());
            }
        }

        return solutions;
    }
}
