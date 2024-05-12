package logicalExpressions;

public class Not implements INode{

    public Not(INode expression){
        LeftSon = expression;
    }

    public Not(){

    }

    public void setNegatablePart(INode negatablePart){
        LeftSon = negatablePart;
    }

    public INode LeftSon;

    @Override
    public INode getRightSon() {
        return null;
    }

    @Override
    public INode getLeftSon() {
        return LeftSon;
    }

    @Override
    public String toString(){
        return "¬";
    }
}
