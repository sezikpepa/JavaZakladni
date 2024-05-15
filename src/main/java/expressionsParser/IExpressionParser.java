package expressionsParser;

import logicalExpressions.Expression;

import java.util.ArrayList;

/**
 * Transforms text representations of expressions into a tree of INode. If the expression is in tree format it is possible to
 * work with it a lot better
 */
public interface IExpressionParser {

    /**
     * Transforms stored expressions into tree format and returns them
     * @return transformed expressions in tree format
     */
    public ArrayList<Expression> getExpressions() throws Exception;

    /**
     * Expressions can be stored in different places or be read from console for example. This method retreives expressions and load them
     * into the parser
     */
    public void loadInput();

}
