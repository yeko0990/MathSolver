package latexpression;
import java.util.*;

public class ArithmaticOpContext {
	public boolean isFirstElement;
	public int nestingLevel;
	public Operator operatorExtracted;
	public List<LatexElementList> parameters;
	
	public ArithmaticOpContext() {
		nestingLevel = 0;
		parameters = new LinkedList<LatexElementList>();
		parameters.add(new LatexElementList());
	}
	
	public void createNextParameter() {
		parameters.add(new LatexElementList());
	}
	
	public void addToCurrentParameter(LatexElement toAdd) {
		parameters.get(parameters.size()-1).addElement(toAdd);
	}

}
