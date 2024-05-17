package logicalExpressions;
/**
 * Operator OR in logical expressions
 */
public class Or extends Operator{

    /**
     * Creates new OR operator
     */
    public Or(){

    }

    /**
     * String representation of Or operator
     */
    public static String Sign = "v";

    @Override
    public String toString(){
        return Sign;
    }

    @Override
    public INode getCopy() {
        INode copy = new Or();
        copy.setLeftSon(LeftSon.getCopy());
        copy.setRightSon(RightSon.getCopy());
        copy.setLogicalValue(getLogicalValue());
        return copy;
    }

    @Override
    public INode NegateNode() {
        INode newNode = new And();

        newNode.setLeftSon(new Not(LeftSon));
        newNode.setRightSon(new Not(RightSon));

        return newNode;
    }

    @Override
    public void evaluate() {
        boolean left = LeftSon.getLogicalValue();
        boolean right = RightSon.getLogicalValue();

        setLogicalValue(left || right);
    }

}
