package evaluators;

import logicalExpressions.*;

import java.util.HashMap;

/**
 * Performs tablo on provided expression, based on the tablo decides if there is a solution for the expression
 */
@SuppressWarnings("unchecked")
public class TabloEvaluator {

    /**
     * Expression which will be used for tablo operations
     */
    private Expression _expression;

    /**
     * Creates new tablo evaluator with provided expression
     * @param expression Expression which will be used to generate tablo
     */
    public TabloEvaluator(Expression expression){
        _expression = expression;
    }

    /**
     * Decides if the provided expression has solution of not
     * @return True - solution has a solution, otherwise false
     */
    public boolean isSolvable(){

        _expression.setAllLogicalValuesToNull();
        _expression.Negate();
        _expression.SimplifyNots(_expression._root);
        _expression._root.setLogicalValue(false);
        var result = buildTablo();
        _foundValid = false;
        isTabloValid(result, new HashMap<>());
        return _foundValid;
    }

    private boolean _foundValid = false;

    /**
     * Iterate through the built tablo and checks if there is a branch without contradiction. If branch without contradition exists,
     * the expression has a solution.
     * @param tablo Tablo tree of the expression
     * @param knownValues If the variable in expression value was read in the branch before, it is stored in this variable. If there is
     *                    a variable in expression with opposite value, the branch is not correct.
     */
    private void isTabloValid(TabloBracket tablo, HashMap<String, Boolean> knownValues){

        if(tablo == null){
            return;
        }

        if(tablo.Value._root instanceof Variable){
            if(knownValues.containsKey(((Variable) tablo.Value._root).VariableName)){
                if(tablo.Validity != knownValues.get(((Variable) tablo.Value._root).VariableName)){
                    return;
                }
            }
            else {
                knownValues.put(((Variable) tablo.Value._root).VariableName, tablo.Validity);
            }
        }

        if(tablo.LeftSon == null && tablo.RightSon == null){
            _foundValid = true;
        }

        isTabloValid(tablo.LeftSon, (HashMap<String, Boolean>) knownValues.clone());
        isTabloValid(tablo.RightSon, (HashMap<String, Boolean>) knownValues.clone());
    }

    /**
     * Breaks the expression into the smallest parts based on atomic tablos.
     * @return Root of the tablo.
     */
    private TabloBracket buildTablo(){
        var tablo = new TabloBracket(_expression._root);
        tablo.Validity = false;

        while (true){
            var unfinishedPart = findUnifinishedPart(tablo);
            if(unfinishedPart == null){
                break;
            }
            unfinishedPart.Extracted = true;
            var copy = unfinishedPart.getCopy();
            copy.Extracted = true;
            pasteToEveryLowerBranch(tablo, copy);
            buildTabloPart(copy);
        }
        return tablo;
    }

    /**
     * If the part of the expression was breaked down using atomic tablo, this method finds another part which is not breaked down and
     * returns it.
     * @param root Root of the tablo where the search should be held.
     * @return Unprocessed part
     */
    private TabloBracket findUnifinishedPart(TabloBracket root){

        if(root == null){
            return null;
        }

        if(root.Value._root instanceof Variable && root.LeftSon == null){
            return null;
        }

        if(!root.Extracted && !(root.Value._root instanceof Variable)){
            return root;
        }

        var result = findUnifinishedPart(root.LeftSon);
        if(result != null){
            return result;
        }

        result = findUnifinishedPart(root.RightSon);
        return result;
    }

    /**
     * Unprocessed part needs to be placed into every existing branch. This method does exactly that.
     * @param start Root of the section in the table where the search for existing branches should be held.
     * @param whatToInsert Unprocessed part which will be placed into every existing branch
     */
    private void pasteToEveryLowerBranch(TabloBracket start, TabloBracket whatToInsert){

        if(start.RightSon == null && start.LeftSon == null){
            start.LeftSon = whatToInsert;
            return;
        }

        if(start.RightSon != null){
            pasteToEveryLowerBranch(start.RightSon, whatToInsert);
        }
        if(start.LeftSon != null){
            System.out.println("right son: " + start.RightSon);
            System.out.println("left son: " + start.LeftSon);
            System.out.println("what to insert: " + whatToInsert);
            System.out.println();
            pasteToEveryLowerBranch(start.LeftSon, whatToInsert);
        }
    }

    /**
     * if the expression is not break down into atomic parts, this method performs atomic tablo and splits the expression into smaller parts
     * based on the operator
     * @param root Root of the expression which should be break down
     */
    private void buildTabloPart(TabloBracket root){

        if(root.Value == null){
            return;
        }

        else if(root.Value._root instanceof Variable){
            return;
        }

        else if(root.Value._root instanceof Not){
            var newNode = new TabloBracket(root.Value._root.getLeftSon().getCopy());
            newNode.Validity = !root.Validity;

            root.LeftSon = newNode;
        }

        else if(root.Value._root instanceof And){
            var newBracket = new TabloBracket(root.Value._root.getLeftSon().getCopy());
            var newBracket2 = new TabloBracket(root.Value._root.getRightSon().getCopy());
            if(root.Validity){

                newBracket.Validity = true;
                newBracket2.Validity = true;

                newBracket.LeftSon = newBracket2;

                root.LeftSon = newBracket;
            }
            else{

                newBracket.Validity = false;
                newBracket2.Validity = false;

                root.LeftSon = newBracket;
                root.RightSon = newBracket2;

            }
        }

        else if(root.Value._root instanceof Or){
            var newBracket = new TabloBracket(root.Value._root.getLeftSon().getCopy());
            var newBracket2 = new TabloBracket(root.Value._root.getRightSon().getCopy());
            if(root.Validity){

                newBracket.Validity = true;
                newBracket2.Validity = true;

                root.LeftSon = newBracket;
                root.RightSon = newBracket2;
            }
            else{

                newBracket.Validity = false;
                newBracket2.Validity = false;

                newBracket.LeftSon = newBracket2;
                root.LeftSon = newBracket;
            }
        }

        else if(root.Value._root instanceof Implication){
            var newBracket = new TabloBracket(root.Value._root.getLeftSon().getCopy());
            var newBracket2 = new TabloBracket(root.Value._root.getRightSon().getCopy());
            if(root.Validity){

                newBracket.Validity = false;
                newBracket2.Validity = true;

                root.LeftSon = newBracket;
                root.RightSon = newBracket2;
            }
            else{

                newBracket.Validity = true;
                newBracket2.Validity = false;

                newBracket.LeftSon = newBracket2;
                root.LeftSon = newBracket;
            }
        }
        else if(root.Value._root instanceof Equivalent){
            var newBracket = new TabloBracket(root.Value._root.getLeftSon().getCopy());
            var newBracket2 = new TabloBracket(root.Value._root.getRightSon().getCopy());
            if(root.Validity){
                //first half

                newBracket.Validity = true;
                newBracket2.Validity = true;

                newBracket.LeftSon = newBracket2;

                root.LeftSon = newBracket;
                ////////////////////////////////////////////////////////////////////////////////

                var newBracket3 = new TabloBracket(root.Value._root.getLeftSon().getCopy());
                var newBracket4 = new TabloBracket(root.Value._root.getRightSon().getCopy());

                newBracket3.Validity = true;
                newBracket4.Validity = true;

                newBracket3.LeftSon = newBracket4;

                root.RightSon = newBracket3;
            }
            else{
                //first half

                newBracket.Validity = false;
                newBracket2.Validity = true;

                newBracket.LeftSon = newBracket2;

                root.LeftSon = newBracket;
                ////////////////////////////////////////////////////////////////////////////////

                var newBracket3 = new TabloBracket(root.Value._root.getLeftSon().getCopy());
                var newBracket4 = new TabloBracket(root.Value._root.getRightSon().getCopy());

                newBracket3.Validity = true;
                newBracket4.Validity = false;

                newBracket3.LeftSon = newBracket4;

                root.RightSon = newBracket3;
            }
        }

    }

}
