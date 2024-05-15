package expressionPrinters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * All solutions of the expression should be displayed in a better looking way. This class prints the variables in the first line and on
 * other lines are all possible solutions. To each variable is assigned a logical value.
 */
public class PossibleSolutionsExpression {

    /**
     * Creates new possible solution printer
     */
    public PossibleSolutionsExpression(){

    }

    /**
     * Returns all solutions of the expression in a more pleasant way
     * @param solutions Solutions of the expression, where keys are variable names and values are logical values of variables
     * @return All solutions of the expression in a text form
     */
    public String printAllSolutions(ArrayList<HashMap<String, Boolean>> solutions){
        StringBuilder result = new StringBuilder();
        int longestVariable = solutions.stream().flatMap(map -> map.keySet().stream()).mapToInt(x -> x.length()).max().orElse(10);

        int spaceSize = longestVariable + 5;

        List<String> variables = solutions.get(0).keySet().stream().sorted().toList();

        for(var key : variables){
            result.append(key);
            result.append(" ".repeat(spaceSize - key.length()));
        }
        result.append(System.lineSeparator());
        result.append("-".repeat(spaceSize * variables.size()));
        for(var solution : solutions){
            for(var variable : variables){
                if(solution.get(variable)){
                    result.append('1');
                }
                else{
                    result.append('0');
                }
                result.append(" ".repeat(spaceSize - 1));
            }
            result.append(System.lineSeparator());
        }

        return result.toString();
    }



}
