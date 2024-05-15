package logicalExpressions;

/**
 * Operator NOT in logical expressions
 */
public class Not extends Operator{

    /**
     * Creates new NOT with already provided expression which is negated
     * @param expression Expression which will be marked with negation
     */
    public Not(INode expression){
        LeftSon = expression;
    }

    @Override
    public INode getCopy() {
        INode copy = new Not();
        copy.setLeftSon(LeftSon);
        copy.setRightSon(RightSon);
        copy.setLogicalValue(getLogicalValue());
        return copy;
    }

    /**
     * Creates new NOT operator
     */
    public Not(){

    }

    /**
     * This operator has only one child - the expression which negates. The expression is set via this method
     * @param negatablePart Expression which will be marked with negation sign
     */
    public void setNegatablePart(INode negatablePart){
        LeftSon = negatablePart;
    }

    @Override
    public INode NegateNode() {
        return LeftSon;
    }

    @Override
    public String toString(){
        return "¬";
    }

    @Override
    public void evaluate() {
        setLogicalValue(!LeftSon.getLogicalValue());
    }
}
