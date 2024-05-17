package expressionPrinters;

import logicalExpressions.Expression;

import java.io.IOException;

/**
 * Displays expressions in the console
 */
public class ConsolePrinter implements ExpressionPrinter{

    /**
     * Creates new printer to console
     */
    public ConsolePrinter(){

    }

    @Override
    public void Print(Iterable<Expression> expressions) throws IOException {
        for (Expression expression : expressions){
            System.out.println(expression.toString());
        }
    }
}
