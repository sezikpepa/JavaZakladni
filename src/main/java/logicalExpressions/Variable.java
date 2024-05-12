package logicalExpressions;



public class Variable implements INode {



    public String VariableName;

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
    public String toString(){
        return VariableName;
    }
}
