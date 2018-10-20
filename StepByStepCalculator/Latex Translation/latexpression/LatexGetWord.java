package latexpression;

import java.util.LinkedList;

public class LatexGetWord extends LatexOperation {

	@Override
	public boolean isOperation(LinkedList<LatexElement> elements) {
		if (elements.size() < 1) return false;
		return elements.get(0).isWord();
	}

	@Override
	public LatexExpression toExpression(LinkedList<LatexElement> elements) {
		GetWordContext context = new GetWordContext();
		elements.get(0).wordExtraction(context);
		return context.wordExpression;
	}

}
