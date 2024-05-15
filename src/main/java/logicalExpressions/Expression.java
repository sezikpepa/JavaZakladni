package logicalExpressions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Represents logical expression. It is represented as a tree structure, where every variable and operator is a node in this tree
 */
public class Expression implements Iterable<INode> {

    /**
     * Main node of the expression tree
     */
    public INode _root;

    /**
     * Creates new expression via provided values
     * @param root Main node of the expression
     */
    public Expression(INode root){
        _root = root;
    }

    /**
     * Parse the expression from the form into text form
     * @param root Node representing the section which should be transformed into text
     * @return Text representation of the expression section
     */
    public String getStringRepresentation(INode root) {
        StringBuilder stringRepresentation = new StringBuilder();
        if (root != null) {
            if(root instanceof Not){
                stringRepresentation.append("¬");
                stringRepresentation.append(getStringRepresentation(root.getLeftSon()));
            }
            else{
                if(root.getLeftSon() != null){
                    stringRepresentation.append("( ");
                }
                stringRepresentation.append(getStringRepresentation(root.getLeftSon()));
                stringRepresentation.append(" ");
                stringRepresentation.append(root);
                stringRepresentation.append(" ");
                stringRepresentation.append(getStringRepresentation(root.getRightSon()));
                if(root.getRightSon() != null){
                    stringRepresentation.append(" )");
                }
            }

        }
        return stringRepresentation.toString().trim();
    }

    /**
     * Creates exact copy of the expression
     * @param root Main node of the expression
     * @return Copy of the expression
     */
    public INode getCopy(INode root){
        INode newRoot = root.getCopy();

        newRoot.setLeftSon(getCopyPart(root.getLeftSon()));
        newRoot.setRightSon(getCopyPart(root.getRightSon()));

        return getCopyPart(root);
    }

    /**
     * Recursion function to get copy of the expression part
     * @param root Root of the expression section
     * @return Copy of the expression section
     */
    private INode getCopyPart(INode root){

        if(root == null){
            return null;
        }

        root.setLeftSon(getCopyPart(root.getLeftSon()));
        root.setRightSon(getCopyPart(root.getRightSon()));

        return root;
    }

    /**
     * Negates the expression and propagate the NOT to the lowest level
     */
    public void Negate(){
        _root = new Not(this._root);
        _root = SimplifyNots(_root);
    }

    /**
     * Propagates NOT into the lowest level
     * @param root Main node of the part which should propagate NOTs
     * @return Negated expression
     */
    public INode SimplifyNots(INode root){

        if(root instanceof Not){
            if(!(root.getLeftSon() instanceof Variable)){
                root = root.getLeftSon().NegateNode();
            }
            else{
                return root;
            }
        }

        if(root.getLeftSon() != null && root.getLeftSon() instanceof Operator){
            root.setLeftSon(SimplifyNots(root.getLeftSon()));
        }
        if(root.getRightSon() != null && root.getRightSon() instanceof Operator){
            root.setRightSon(SimplifyNots(root.getRightSon()));
        }

        return root;

    }

    /**
     * Search for all variable names which has occurred in the expression
     * @return Variable names from the expression
     */
    public ArrayList<String> getAllVariables(){
        return getAllVariablesPart(_root);
    }

    /**
     * Recursive function to get all variable names from the expression
     * @param root Root of the part of the expression from which the variable names should be extracted
     * @return Variable names from the expression section
     */
    private ArrayList<String> getAllVariablesPart(INode root){
        ArrayList<String> variables = new ArrayList<>();
        if(root == null){
            return variables;
        }

        if(root instanceof Variable){
            variables.add(((Variable) root).VariableName);
        }

        if(root.getLeftSon() != null){
            variables.addAll(getAllVariablesPart(root.getLeftSon()));
        }
        if(root.getRightSon() != null){
            variables.addAll(getAllVariablesPart(root.getRightSon()));
        }

        return variables;
    }

    /**
     * Gets logical value of the expression
     * @return Logical value of the expression
     */
    public Boolean getLogicalValue(){
        return _root.getLogicalValue();
    }

    /**
     * Based on values in the variables, decides if the expression is true or false
     */
    public void evaluateExpression(){
        evaluateExpressionPart(_root);
    }

    /**
     * Based on values in the variables, decides if the expression is true or false
     * @param root Root of the expression part which should be evaluated
     */
    private void evaluateExpressionPart(INode root){
        if(root.getLeftSon().getLogicalValue() == null){
            if(root.getLeftSon() instanceof Variable){
                throw new ArithmeticException("Variable " + root.getLeftSon().toString() + " is not set");
            }
            evaluateExpressionPart(root.getLeftSon());
        }
        if(root.getRightSon().getLogicalValue() == null){
            if(root.getRightSon() instanceof Variable){
                throw new ArithmeticException("Variable " + root.getRightSon().toString() + " is not set");
            }
            evaluateExpressionPart(root.getRightSon());
        }

        ((Operator)root).evaluate();
    }

    /**
     * Removes all calculated logical values in all parts of the expression, sets them to null
     */
    public void setAllLogicalValuesToNull(){
        setAllLogicalValuesToNullPart(_root);
    }

    /**
     * Removes all calculated logical values in all parts of the expression, sets them to null
     * @param root Main root of the expression part, where logical values should be removed
     */
    private void setAllLogicalValuesToNullPart(INode root){
        if(root != null){
            root.setLogicalValue(null);
        }
        else{
            return;
        }

        if(root.getLeftSon() != null){
            setAllLogicalValuesToNullPart(root.getLeftSon());
        }
        if(root.getRightSon() != null){
            setAllLogicalValuesToNullPart(root.getRightSon());
        }
    }

    /**
     * Sets logical value to every variable in the expression
     * @param values Logical values, keys are variable names, values are logical values
     */
    public void setLogicalValuesToVariables(HashMap<String, Boolean> values){
        setLogicalValuesToVariablesPart(values, _root);
    }

    private void setLogicalValuesToVariablesPart(HashMap<String, Boolean> values, INode root){
        if(root instanceof Variable){
            root.setLogicalValue(values.get(((Variable) root).VariableName));
        }

        if(root.getLeftSon() != null){
            setLogicalValuesToVariablesPart(values, root.getLeftSon());
        }
        if(root.getRightSon() != null){
            setLogicalValuesToVariablesPart(values, root.getRightSon());
        }
    }

    @Override
    public String toString(){
        return getStringRepresentation(_root);
    }


    @Override
    public Iterator<INode> iterator() {
        return new Iterator<>() {
            private Stack<INode> stack = null;

            @Override
            public boolean hasNext() {
                if (stack == null) {
                    stack = new Stack<>();
                    if (_root == null) {
                        stack = new Stack<>();
                    } else {
                        fillStack(_root);
                    }
                }

                return !stack.empty();
            }

            @Override
            public INode next() {
                return stack.pop();
            }

            private void fillStack(INode node) {
                if (node.getRightSon() != null) {
                    fillStack(node.getRightSon());
                }
                stack.add(node);

                if (node.getLeftSon() != null) {
                    fillStack(node.getLeftSon());
                }
            }
        };

    }
}
