package expressionPrinters;


import logicalExpressions.Expression;

import java.io.IOException;

/**
 * Prints expression in string representation to other mediums
 */
public interface ExpressionPrinter {

    /**
     * Prints expression in string representation to other mediums
     * @param expressions Expression in string representation
     * @throws IOException If the printer access to other resources, which are not available, this exception is thrown
     */
    public void Print(Iterable<Expression> expressions) throws IOException;


}
