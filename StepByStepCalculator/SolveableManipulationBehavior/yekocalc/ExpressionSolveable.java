package yekocalc;

///TODO Finish this class
public class ExpressionSolveable implements Solveable {
	private SolveableManipulateBehavior manipulate;
	private Expression exp;	
	
	public ExpressionSolveable(Expression exp) {
		this.exp = exp;
	}

	@Override
	public SolveableManipulateBehavior manipulateBehavior() {
		return manipulate;
	}

	@Override
	public String printSolveable(VariableIDTable table) {
		return exp.getStoredSym().latexString(table);
	}

	@Override
	public Solution reachedSolution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solveable stepSolve() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Solveable fullSolve() {
		// TODO Change this
		return this;
	}

}
