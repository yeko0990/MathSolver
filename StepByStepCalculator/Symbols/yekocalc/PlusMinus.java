package yekocalc;

import java.util.List;

import latexpression.LatexExpression;
import latexpression.LatexPlusMinus;
import latexpression.VariableIDTable;

public class PlusMinus extends Symbol {

	public PlusMinus(Symbol param) {
		super();
		this.getParams().set(0, param);
	}

	public PlusMinus() {
		this(null);
	}
	
	public Symbol parameter() {
		return this.getParams().get(0);
	}

	public void setParameter(Symbol newParam) {
		this.getParams().set(0, newParam);
	}
	
	@Override
	protected Symbol copySymbolType() {
		return new PlusMinus();
	}

	@Override
	public void accept(SymbolVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public LatexExpression latex(VariableIDTable idList) {
		return new LatexPlusMinus(this.parameter().latex(idList));
	}

	@Override
	protected SymbolID createIdType() {
		return new NoId();
	}

	@Override
	protected SymbolStandarizer createStd() {
		return new StandarizeSet(new PlusMinusExtraction(this),
								 new PlusMinusZeroExtractor(this));
	}

	@Override
	protected FullSimplifier createSimp() {
		return new FullSimplifier(this, new DirectSimplifierSet(new NoSimplify()));
	}

	@Override
	protected SymbolPatternIdentifier createId() {
		return new SymbolPatternIdentifier(new NotRawNumber(),
										   new NotImplicatedNumber(),
										   new PlusMinusTranslatedNumber(this),
										   new PlusMinusPolynomIdentifier(this)
										   );
	}

	@Override
	protected Parameters createParameters() {
		return new FixedSizeParameters(new ParameterOrderedEquator(), 1);
	}

	@Override
	public SymType getType() {
		// TODO Auto-generated method stub
		return SymType.PlusMinus;
	}

}
