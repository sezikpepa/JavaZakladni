package evaluators;

import logicalExpressions.Expression;
import logicalExpressions.INode;


/**
 * Tablo is built using different sections, every section contains  True/False validity of the expression and the expression. Moreover
 * it has to remember its children.
 */
public class TabloBracket {

    /**
     * Expression in the bracket
     */
    public Expression Value;

    /**
     * First part of the split expression
     */
    public TabloBracket LeftSon;

    /**
     * Second part of the split expression
     */
    public TabloBracket RightSon;

    /**
     * Expression in this bracket has been split into smaller pieces and copied into child branches
     */
    public boolean Extracted = false;

    /**
     * Expression in the bracket should be considered as true/false
     */
    public boolean Validity;

    /**
     * Creates new empty bracket
     */
    public TabloBracket(){

    }

    /**
     * Creates copy of the bracket and its children
     * @return Copy of the bracket
     */
    public TabloBracket getCopy(){
        return getCopy(this);
    }

    /**
     * Creates copy of the bracket and its children, used as a recursion function
     * @param start Section of the bracket which should be copied
     * @return Copy of the expression section
     */
    private TabloBracket getCopy(TabloBracket start){

        if(start == null){
            return null;
        }

        var newValue = new TabloBracket();

        newValue.LeftSon = getCopy(newValue.LeftSon);
        newValue.RightSon = getCopy(newValue.RightSon);
        newValue.Extracted = Extracted;
        newValue.Validity = Validity;
        newValue.Value = new Expression(Value.getCopy(Value._root));

        return newValue;
    }

    /**
     * Creates new bracket with the expression
     * @param value Root of the expression
     */
    public TabloBracket(INode value){
        Value = new Expression(value);
    }

    @Override
    public String toString(){
        String validityString;
        if(Validity){
            validityString = "T";
        }
        else {
            validityString = "F";
        }
        return validityString + Value;
    }
}
