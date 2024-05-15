package logicalExpressions;


/**
 * Serves as a representation of every part of the logical expression, can be negation symbol, variable, and, or, implies...
 * Mentioned parts will have different classes which implement this interface
 */
public interface INode {

    /**
     * Every variable all part of the expression is true/false, this method returns this value
     * @return logical value of specific section of the expression
     */
    public Boolean getLogicalValue();

    /**
     * Every variable all part of the expression is true/false, this method sets this value
     * @param value Logical value of the variable which will be set to this variable
     */
    public void setLogicalValue(Boolean value);

    /**
     * Gets right son of the node
     * @return Right son of the node
     */
    public INode getRightSon();

    /**
     * Gets left son of the node
     * @return Left son of the node
     */
    public INode getLeftSon();

    /**
     * Sets the left son of the node
     * @param node Value to be inserted
     */
    public void setLeftSon(INode node);

    /**
     * Sets the right son of the node
     * @param node Value to be inserted
     */
    public void setRightSon(INode node);

    /**
     * Negates the node
     * @return Negated node
     */
    public INode NegateNode();

    /**
     * Creates exact copy of the node
     * @return copy of the node
     */
    public INode getCopy();

}
