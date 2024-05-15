package logicalExpressions;

/**
 * Operator EQUIVALENCE in logical expressions
 */
public class Equivalent extends Operator{

    /**
     * Creates new EQUIVALENCE operator
     */
    public Equivalent(){

    }


    /**
     * String representation of EQUIVALENCE
     */
    public static String Sign = "<=>";

    @Override
    public String toString(){
        return Sign;
    }

    @Override
    public INode getCopy() {
        INode copy = new Equivalent();
        copy.setLeftSon(LeftSon);
        copy.setRightSon(RightSon);
        copy.setLogicalValue(getLogicalValue());
        return copy;
    }

    @Override
    public INode NegateNode() {
        INode newNode = new Or();

        INode firstHalf = new Implication();
        INode secondHalf = new Implication();

        firstHalf.setLeftSon(LeftSon);
        firstHalf.setRightSon(RightSon);

        secondHalf.setLeftSon(RightSon);
        secondHalf.setRightSon(LeftSon);

        firstHalf = new Not(firstHalf);
        secondHalf = new Not(secondHalf);

        newNode.setLeftSon(firstHalf);
        newNode.setRightSon(secondHalf);

        return newNode;
    }

    @Override
    public void evaluate() {
        boolean left = LeftSon.getLogicalValue();
        boolean right = RightSon.getLogicalValue();

        setLogicalValue(left && right || !left && !right);
    }
}
