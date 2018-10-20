package latexpression;
import java.util.*;

public class LatexElementList implements LatexEvaluatable {
	private boolean evaluated; ///True if value was evaluated (thus the object is now immutable)
	LatexExpression value;
	LinkedList<LatexElement> elements;	
	private LinkedList<LatexOperation> extractionOperations; ///IMPORTANT: Index of an operation in this list is relative	
															 ///to its STRENGTH. Bigger-index operation is STRONGER.
	private void initOperations() {
		extractionOperations = new LinkedList<LatexOperation>();
		extractionOperations.add(new LatexExtractAddition());
		extractionOperations.add(new LatexExtractMultiply());
		extractionOperations.add(new LatexExtractDivision());
		extractionOperations.add(new LatexExtractPower());
		extractionOperations.add(new LatexGetVariable());
		extractionOperations.add(new LatexGetNumber());
		extractionOperations.add(new LatexGetWord());
	}
	
	public LatexElementList() {
		elements = new LinkedList<LatexElement>();
		initOperations();
	}
	
	public void addElement(LatexElement element) {
		if (evaluated) throw new RuntimeException(); ///TODO Create exception for this
		this.elements.add(element);
	}
	
	private void putMultiplyBeforeIndex(int index) {
		elements.add(index, LatexStaticElements.MULTIPLY);
	}
	
	private void putMultiplies() {
		PutMultipliesContext ctx = new PutMultipliesContext();
		LatexElement next;
		int nested = 0;
		for (int i = 0; i < elements.size(); i++) {
			next = elements.get(i);
			if (nested == 0) {
				if (next.putMultiplies(ctx)) {
					putMultiplyBeforeIndex(i);
					i++; ///because size has grown by 1
				}
			}
			if (next.isNesting()) nested++;
			if (next.isDoneNesting()) nested--;
		}
	}
	
	private void addMinusMultiply() {
		elements.add(LatexStaticElements.OPEN);
		elements.add(LatexStaticElements.MINUS);
		elements.add(new LatexNumberElement(1));
		elements.add(LatexStaticElements.CLOSE);
		elements.add(LatexStaticElements.MULTIPLY);
	}
	
	private void firstMinusToMultiply() {
		if (elements.size() < 2) return;
		else if (elements.get(0).shouldRemoveMinus(elements.get(1))) {
			elements.removeFirst();
			addMinusMultiply();
		}
	}
	
	private boolean wholyNested() {
		int nesting = 0;
		LatexElement next;
		if (elements.size() == 0) return false; //SHOULDNT HAPPEN, but safe-checking.
		for (int i = 0; i < elements.size()-1; i++) {
			next = elements.get(i);
			if (next.isNesting()) nesting++;
			if (next.isDoneNesting()) nesting--;
			if (nesting == 0) return false;
		}
		
		if (elements.get(elements.size()-1).isDoneNesting() && nesting == 1) return true;
		else return false;
	}
	
	///Removes nesting if whole latexElementList is nested.
	private void removeNesting() {
		while (wholyNested()) {
			elements.removeFirst();
			elements.removeLast();
		}
	}
	
	private LatexOperation determineOperation() throws IllegalElementListException {
		for (LatexOperation nextOp : this.extractionOperations) {
			if (nextOp.isOperation(elements)) return nextOp;
		}
		throw new IllegalElementListException();
	}
	
	@Override
	public LatexExpression evaluate() throws IllegalElementListException {
		if (evaluated) return value;
		removeNesting();
		putMultiplies();
		firstMinusToMultiply();
		value = determineOperation().toExpression(elements);
		
		evaluated = true;
		return value;
	}

}
