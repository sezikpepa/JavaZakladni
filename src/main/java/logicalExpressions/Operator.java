package logicalExpressions;

import java.util.List;

public abstract class Operator implements INode{

    public INode LeftSon;

    public INode RightSon;

    public static String Sign;

    @Override
    public INode getLeftSon(){
        return LeftSon;
    }

    @Override
    public INode getRightSon(){
        return RightSon;
    }
}
