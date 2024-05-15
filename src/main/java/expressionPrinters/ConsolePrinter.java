package expressionPrinters;

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
    public void Print(Iterable<String> expressions) throws IOException {
        for (String expression : expressions){
            System.out.println(expression);
        }
    }
}
