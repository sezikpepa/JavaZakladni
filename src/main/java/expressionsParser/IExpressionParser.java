package expressionsParser;

import logicalExpressions.Expression;
import logicalExpressions.INode;

import java.util.ArrayList;

public interface IExpressionParser {


    public ArrayList<Expression> getExpressions();

    public void loadInput();

}
