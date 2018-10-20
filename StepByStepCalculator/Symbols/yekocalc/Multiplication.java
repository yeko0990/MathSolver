package yekocalc;
import java.util.*;

import latexpression.LatexExpression;
import latexpression.LatexMultiply;
import latexpression.VariableIDTable;

public class Multiplication extends Symbol {
	@Override
	public void accept(SymbolVisitor visitor) {
		visitor.visit(this);
	}
	
	public Multiplication(List<Symbol> paramsP) {
		super(paramsP);
	}
	
	public Multiplication(Symbol...syms) {
		super(syms);
	}
	
	@Override
	protected Symbol copySymbolType() {
		return new Multiplication();
	}

	
	@Override
	protected SymbolID createIdType() {
		return new NoId();
	}

	@Override
	protected SymbolStandarizer createStd() {
		List<SymbolStandarizer> stands = ListUtils.toLinkedList(new AssociativeStandarizer(),
																new SingleParameterExtractionStandarizer());
		return new StandarizeSet (stands);
	}

	@Override
	protected FullSimplifier createSimp() {
		DirectSimplifierSet simp = new DirectSimplifierSet(new MultiplyNumberSimplifier(this, SimplificationConfig.numberDetector),
												  new PowerUnifier(this),
												  new RemoveParameterWithoutClearingParams(this, NumberSym.doubleSymbol(1)));
		return new FullSimplifier(this, simp);
	}

	@Override
	protected SymbolPatternIdentifier createId() {
		return new SymbolPatternIdentifier(
				new NotRawNumber(),
				new NotImplicatedNumber(),
				new NoTranslatedNumberIdentifier(this), ///TODO WTF? Add a translatedNumIdentifier
				new MultiPolynomIdentifier(this)
				);
	}

	@Override
	protected Parameters createParameters() {
		return new UnorderedListParameters();
	}
	@Override
	protected OpenForm createOpenForm() {
		return new MultiplicationOpenForm();
	}
	
	@Override
	public SymType getType() {
		return SymType.Multiplication;
	}
	
	@Override
	public LatexExpression latex(VariableIDTable idList) {
		return new LatexMultiply(this.getParams(), idList);
	}
	
	/*@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		List<Symbol> params = parameters.getParameters();
		if (params.size() < 1) return "";
		else putParanthessesSymbol(sb, params.get(0));
		for (int i = 1; i < params.size(); i++) {
			sb.append("*");
			putParanthessesSymbol(sb, params.get(i));
		}
		return sb.toString();
	}*/
	
	private void addDissolvedParam(DissolvedSymbol dissolved, Symbol param) {
		param.toDissolvedSymbol();
	}
	
	@Override
	public DissolvedSymbol toDissolvedSymbol() {
		DissolvedSymbol total = new DissolvedSymbol();
		List<Symbol> params = this.getParams();
		for (Symbol nextParam: params) {
			total.mixDissolvedSymbol(nextParam.toDissolvedSymbol());
		}
		return total;
	}
	
	///TODO: *EXTREMELY IMPORTANT*: implement behavior when sym is a power!
	//@Override
	//public Symbol getCoefficient(Symbol extractedSym) {
		//MultiplicationSymbolCoefficient coefficient = new MultiplicationSymbolCoefficient(this, extractedSym);
		//return coefficient.getCoefficient();
	//}
	
	/*@Override
	public Multiplication toMultiply() {
		return this;
	}*/

}
