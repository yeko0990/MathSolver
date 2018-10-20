package yekocalc;

import latexpression.VariableIDTable;

public interface Solveable {
	public SolveableManipulateBehavior manipulateBehavior(); ///Defines actions to be taken when next step is done- For example, print
															 ///"Simplifying" when an equation is being simplified.
	public String printSolveable(VariableIDTable table); ///Prints the solveable.
	public Solution reachedSolution(); ///Returns 'null' if did not reach solution yet.
	public Solveable stepSolve();
	public Solveable fullSolve();
}
