package logicalExpressions;

/**
 * Operator AND in logical expressions
 */
public class And extends Operator{

    /**
     * Creates new AND operator
     */
    public And(){

    }

    /**
     * String representation of the And operator
     */
    public static String Sign = "∧";

    @Override
    public String toString(){
        return Sign;
    }

    @Override
    public INode NegateNode() {
        INode newNode = new Or();

        newNode.setLeftSon(new Not(LeftSon));
        newNode.setRightSon(new Not(RightSon));

        return newNode;
    }

    @Override
    public INode getCopy() {
        INode copy = new And();
        copy.setLeftSon(LeftSon.getCopy());
        copy.setRightSon(RightSon.getCopy());
        copy.setLogicalValue(getLogicalValue());
        return copy;
    }

    @Override
    public void evaluate() {
        boolean left = LeftSon.getLogicalValue();
        boolean right = RightSon.getLogicalValue();

        setLogicalValue(left && right);
    }
}
