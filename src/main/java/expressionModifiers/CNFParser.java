package expressionModifiers;

import logicalExpressions.*;

/**
 * Transform logical expression into CNF form
 */
public class CNFParser {

    /**
     * Expression which will be manipulated
     */
    private Expression _storedExpression;

    /**
     * Creates new CNF parser
     */
    public CNFParser(){

    }

    /**
     * Sets expression which will be transformed into CNF form
     * @param expression Expression to set
     */
    public void setExpression(Expression expression){
        _storedExpression = expression;
    }

    /**
     * Transforms stored expression into CNF form
     * @return CNF form of stored expression
     */
    public Expression createCNFForm(){

        _storedExpression.setAllLogicalValuesToNull();
        _storedExpression._root = _storedExpression.SimplifyNots(_storedExpression._root);
        removeEquivalence();
        removeImplication();
        _storedExpression.SimplifyNots(_storedExpression._root);
        removeOr();
        return  _storedExpression;
    }

    private boolean _changed = false;

    /**
     * Removes OR from low levels
     */
    private void removeOr(){
        _changed = true;
        while (_changed){
            _changed = false;
            _storedExpression._root = removeOrPart(_storedExpression._root);
        }
    }

    /**
     * Removes OR from low levels
     * @param root Root of the part where it should be removed
     * @return Root of the part which was removed from OR operators
     */
    private INode removeOrPart(INode root){

        if(root.getLeftSon() != null){
            root.setLeftSon(removeOrPart(root.getLeftSon()));
        }
        if(root.getRightSon() != null){
            root.setRightSon(removeOrPart(root.getRightSon()));
        }


        if(root instanceof Or){
            if(!(root.getLeftSon() instanceof Variable || (root.getLeftSon() instanceof Not && root.getLeftSon().getLeftSon() instanceof Variable))){
                INode newNode = new And();

                var firstHalf = new Or();
                firstHalf.setLeftSon(root.getLeftSon().getLeftSon());
                firstHalf.setRightSon(root.getRightSon());

                var secondHalf = new Or();
                secondHalf.setLeftSon(root.getLeftSon().getRightSon());
                secondHalf.setRightSon(root.getRightSon());

                newNode.setLeftSon(firstHalf);
                newNode.setRightSon(secondHalf);

                _changed = true;
                return newNode;
            }

            if(!(root.getRightSon() instanceof Variable || (root.getRightSon() instanceof Not && root.getRightSon().getLeftSon() instanceof Variable))){
                INode newNode = new And();

                var firstHalf = new Or();
                firstHalf.setLeftSon(root.getLeftSon());
                firstHalf.setRightSon(root.getRightSon().getLeftSon());

                var secondHalf = new Or();
                secondHalf.setLeftSon(root.getLeftSon());
                secondHalf.setRightSon(root.getRightSon().getRightSon());

                newNode.setLeftSon(firstHalf);
                newNode.setRightSon(secondHalf);

                _changed = true;
                return newNode;
            }
        }

        return root;
    }

    /**
     * Removes implication, as it is not valid in CNF form
     */
    private void removeImplication(){
        _storedExpression._root = removeImplicationPart(_storedExpression._root);
    }

    /**
     * Removes implication, as it is not valid in CNF form
     * @param root Root of the expression section
     * @return Root of the expression section
     */
    private INode removeImplicationPart(INode root){

        if(root instanceof Implication){
            INode newNode = new Or();

            newNode.setLeftSon(new Not(root.getLeftSon()));
            newNode.setRightSon(root.getRightSon());

            root = newNode;
        }

        if(root.getLeftSon() != null){
            root.setLeftSon(removeImplicationPart(root.getLeftSon()));
        }
        if(root.getRightSon() != null){
            root.setRightSon(removeImplicationPart(root.getRightSon()));
        }

        return root;
    }

    /**
     * Removes equivalence, as it is not valid in CNF form
     */
    private void removeEquivalence(){
        _storedExpression._root = removeEquivalencePart(_storedExpression._root);
    }

    /**
     * Removes equivalence, as it is not valid in CNF form
     * @param root Root of the expression section
     * @return Root of the expression section
     */
    private INode removeEquivalencePart(INode root){

        if(root instanceof Equivalent){
            INode newNode = new And();

            INode firstHalf = new Implication();
            INode secondHalf = new Implication();

            firstHalf.setLeftSon(root.getLeftSon());
            firstHalf.setRightSon(root.getRightSon());

            secondHalf.setLeftSon(root.getRightSon());
            secondHalf.setRightSon(root.getLeftSon());

            newNode.setLeftSon(firstHalf);
            newNode.setRightSon(secondHalf);

            root = newNode;
        }

        if(root.getLeftSon() != null){
            root.setLeftSon(removeEquivalencePart(root.getLeftSon()));
        }
        if(root.getRightSon() != null){
            root.setRightSon(removeEquivalencePart(root.getRightSon()));
        }

        return root;
    }

}
