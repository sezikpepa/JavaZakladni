package logicalExpressions;


/**
 * Logical expressions are build using operators and variables. This class represents variable part of expressions
 */
public class Variable implements INode {

    /**
     * Logical value of the variable
     */
    private Boolean _logicalValue;

    @Override
    public INode getCopy() {
        INode copy = new Variable(VariableName);
        copy.setLogicalValue(getLogicalValue());
        return copy;
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
     * String representation of the variable
     */
    public String VariableName;

    /**
     * Creates new variable a sets its name
     * @param variableName Name of the variable
     */
    public Variable(String variableName){
        VariableName = variableName;
    }


    @Override
    public INode getRightSon() {
        return null;
    }

    @Override
    public INode getLeftSon() {
        return null;
    }

    @Override
    public void setLeftSon(INode node) {

    }

    @Override
    public void setRightSon(INode node) {

    }

    @Override
    public INode NegateNode() {
        return new Not(this);
    }

    @Override
    public String toString(){
        return VariableName;
    }
}
