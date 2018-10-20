package yekocalc;

import java.util.List;

import latexpression.LatexExpression;
import latexpression.LatexRoot;

public class Root extends Symbol {
	@Override
	public void accept(SymbolVisitor visitor) {
		visitor.visit(this);
	}
	
	public Root(Symbol base, Symbol rootBy) {
		super(base, rootBy);
	}
	
	public Root() {
		this(null, null);
	}
	
	public Symbol base() {
		return this.getParams().get(0);
	}
	
	public Symbol rootBy() {
		return this.getParams().get(1);
	}
	
	public void setBase(Symbol newBase) {
		this.getParams().set(0, newBase);
	}
	
	public void setRootBy(Symbol newRootBy) {
		this.getParams().set(1, newRootBy);
	}

	@Override
	protected Symbol copySymbolType() {
		return new Root();
	}

	@Override
	public LatexExpression latex(VariableIDTable idList) {
		return new LatexRoot(this.base().latex(idList), this.rootBy().latex(idList));
	}

	@Override
	protected SymbolID createIdType() {
		return new NoId();
	}

	@Override
	protected SymbolStandarizer createStd() {
		return new RootNumberStandarizer();
	}

	@Override
	protected FullSimplifier createSimp() {
		return new FullSimplifier(this, new DirectSimplifierSet(new RootNumberSimplifier(this),
																new RootBaseSimplifier(this)));
	}

	@Override
	protected SymbolPatternIdentifier createId() {
		return new SymbolPatternIdentifier(new NotRawNumber(),
										   new RootImplicatedNumber(this),
										   new RootTranslatedNumber(this),
										   new RootPolynomIdentifier(this));
	}

	@Override
	protected Parameters createParameters() {
		return new FixedSizeParameters(new ParameterOrderedEquator(), 2);
	}

	@Override
	public SymType getType() {
		return SymType.Root;
	}

}
