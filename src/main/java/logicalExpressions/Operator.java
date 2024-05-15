package logicalExpressions;

/**
 * Base class for all operators which connects variables.
 */
public abstract class Operator implements INode{

    /**
     * Creates new Operator
     */
    public Operator(){

    }

    @Override
    public Boolean getLogicalValue(){
        return _logicalValue;
    }

    @Override
    public void setLogicalValue(Boolean value){
        _logicalValue = value;
    }

    /**
     * Based of the logical values of the child nodes, the logical value of whole expression is stored in this variable
     */
    private Boolean _logicalValue;

    /**
     * Variable on the left side
     */
    public INode LeftSon;

    /**
     * Variable on the right side. If the operator is not binary, this property is null
     */
    public INode RightSon;

    @Override
    public INode getLeftSon(){
        return LeftSon;
    }

    @Override
    public INode getRightSon(){
        return RightSon;
    }

    @Override
    public void setLeftSon(INode node){
        LeftSon = node;
    }

    @Override
    public void setRightSon(INode node){
        RightSon = node;
    }

    /**
     * Determins the logical value of the expression based on logical values of child components
     */
    public abstract void evaluate();

}
