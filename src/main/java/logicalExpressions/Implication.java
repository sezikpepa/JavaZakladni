package logicalExpressions;

/**
 * Operator IMPLICATION in logical expressions
 */
public class Implication extends Operator {


    /**
     * Creates new IMPLICATION operator
     */
    public Implication(){

    }

    /**
     * String representation of IMPLICATION
     */
    public static String Sign = "=>";

    @Override
    public INode getCopy() {
        INode copy = new Implication();
        copy.setLeftSon(LeftSon);
        copy.setRightSon(RightSon);
        copy.setLogicalValue(getLogicalValue());
        return copy;
    }

    @Override
    public String toString(){
        return Sign;
    }

    @Override
    public INode NegateNode() {
        INode newNode = new And();

        newNode.setLeftSon(LeftSon);
        newNode.setRightSon(new Not(RightSon));

        return newNode;
    }

    @Override
    public void evaluate() {
        boolean left = LeftSon.getLogicalValue();
        boolean right = RightSon.getLogicalValue();

        setLogicalValue(!left || right);
    }
}
