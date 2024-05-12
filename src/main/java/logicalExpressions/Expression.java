package logicalExpressions;

import java.util.Iterator;
import java.util.Stack;

public class Expression implements Iterable<INode> {

    public INode _root;

    public Expression(INode root){
        _root = root;
    }

    public String getStringRepresentation(INode root) {
        StringBuilder stringRepresentation = new StringBuilder();
        if (root != null) {
            if(root instanceof Not){
                stringRepresentation.append("¬");
                stringRepresentation.append(getStringRepresentation(root.getLeftSon()));
            }
            else{
                if(root.getLeftSon() != null){
                    stringRepresentation.append("(");
                }
                stringRepresentation.append(getStringRepresentation(root.getLeftSon()));
                stringRepresentation.append(" ");
                stringRepresentation.append(root.toString());
                stringRepresentation.append(" ");
                stringRepresentation.append(getStringRepresentation(root.getRightSon()));
                if(root.getRightSon() != null){
                    stringRepresentation.append(")");
                }
            }

        }
        return stringRepresentation.toString().trim();
    }

    @Override
    public String toString(){
        return getStringRepresentation(_root);
    }


    @Override
    public Iterator<INode> iterator() {
        return new Iterator<INode>() {
            private Stack<INode> stack = null;
            @Override
            public boolean hasNext() {
                if(stack == null){
                    stack = new Stack<INode>();
                    if(_root == null){
                        stack = new Stack<INode>();
                    }
                    else{
                        fillStack(_root);
                    }
                }

                return !stack.empty();
            }

            @Override
            public INode next() {
                return stack.pop();
            }

            private void fillStack(INode node){
                if(node.getRightSon() != null){
                    fillStack(node.getRightSon());
                }
                stack.add(node);

                if(node.getLeftSon() != null){
                    fillStack(node.getLeftSon());
                }
            }
        };

    }
}
