package yekocalc;
import java.util.*;

import latexpression.LatexAddition;
import latexpression.LatexExpression;
import latexpression.VariableIDTable;

public class Addition extends Symbol {
	@Override
	public void accept(SymbolVisitor visitor) {
		visitor.visit(this);
	}
	
	public Addition(List<Symbol> paramsP) {
		super(paramsP);
		// TODO Auto-generated constructor stub
	}
	
	public Addition(Symbol...syms) {
		super(syms);
	}
	
	public Addition() {
		this(new LinkedList<Symbol>());
	}
	
	@Override
	protected Symbol copySymbolType() {
		return new Addition();
	}
	
	@Override
	public LatexExpression latex(VariableIDTable idList) {
		return new LatexAddition(this.getParams(),idList);
	}
	
	/*@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		List<Symbol> params = parameters.getParameters();
		if (params.size() < 1) return "";
		else putParanthessesSymbol(sb, params.get(0));
		for (int i = 1; i < params.size(); i++) {
			sb.append("+");
			putParanthessesSymbol(sb, params.get(i));
		}
		return sb.toString();
	}*/
	
	@Override
	protected SymbolID createIdType() {
		return new NoId();
	}

	@Override
	protected SymbolStandarizer createStd() {
		// TODO Auto-generated method stub
		List<SymbolStandarizer> stands = ListUtils.toLinkedList(new AssociativeStandarizer(),
																new SingleParameterExtractionStandarizer());
		return new StandarizeSet (stands);
	}

	@Override
	protected FullSimplifier createSimp() {
		// TODO Auto-generated method stub
		DirectSimplifierSet directSimp = new DirectSimplifierSet(
														new SumNumberSimplifier(this, SimplificationConfig.numberDetector),
														new MultiplicationUnifier(this),
														new RemoveParameterWithoutClearingParams(this, NumberSym.doubleSymbol(0)));
		return new FullSimplifier(this, directSimp);
	}

	@Override
	protected SymbolPatternIdentifier createId() {
		// TODO Auto-generated method stub
		return new SymbolPatternIdentifier(
				new NotRawNumber(),
				new NotImplicatedNumber(),
				new ParametersAdditionIdentifier(this),
				new AdditionPolynomIdentifier(this)
				);
	}

	@Override
	protected Parameters createParameters() {
		return new UnorderedListParameters();
	}
	@Override
	protected OpenForm createOpenForm() {
		return new AdditionOpenForm();
	}
	
	@Override
	public SymType getType() {
		// TODO Auto-generated method stub
		return SymType.Addition;
	}
	
	/*@Override
	public Symbol getCoefficient(Symbol extractedSym) {
		List<Symbol> coefficients = new LinkedList<Symbol>();
		Symbol nextCoef;
		Symbol zeroCoefficient = NumberSym.doubleSymbol(0);
		boolean extractedSymExists = false;
		for (Symbol param : parameters) {
			nextCoef = param.getCoefficient(extractedSym);
			if (!nextCoef.equals(zeroCoefficient)) {
				coefficients.add(nextCoef);
				extractedSymExists = true; 
			}
		}
		
		if (!extractedSymExists) return zeroCoefficient;
		else return new Addition(coefficients);
	}*/
	
	@Override
	public Symbol getTotalPolynomCoefficients(Symbol extractedSym) {
		Addition addedCoefs = new Addition();
		List<Symbol> params = this.getParams();
		List<Symbol> addedCoefsParams = addedCoefs.getParams();
		Symbol nextCoef;
		for (Symbol nextP : params) {
			nextCoef = nextP.getPolynomCoefficient(extractedSym);
			if (nextCoef != PolynomCoefficient.onNegativePower) addedCoefsParams.add(nextCoef);
		}
		if (addedCoefsParams.size() == 0) return NumberSym.zero;
		
		Expression finalAddedCoefs = new Expression(addedCoefs);
		finalAddedCoefs.simplify();
		return finalAddedCoefs.getStoredSym();
	}

}
