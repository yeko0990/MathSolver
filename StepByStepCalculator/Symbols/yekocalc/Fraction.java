package yekocalc;

import java.util.*;

import latexpression.LatexDivision;
import latexpression.LatexExpression;

///TODO
///OVERRIDE toDissolvedSymbol() (DONT FORGET TO EDIT POLYNOM COEFFICIENT CLASS- TO ACCOMODATE COEFFICIENTS OF FRACTIONS 
///								 example: polynom coefficient of 1/x extracted from 1/(x^2) is 1/x, not 0.)
///								*And perhaps DissolvedSymbol needs editing too
public class Fraction extends Symbol {
	@Override
	public void accept(SymbolVisitor visitor) {
		visitor.visit(this);
	}
	
	public Fraction(Symbol numerator, Symbol denominator) {
		super();
		parameters.getParameters().set(0, numerator);
		parameters.getParameters().set(1, denominator);
	}

	@Override
	protected Symbol copySymbolType() {
		return new Fraction(null, null); ///TODO very unsafe... Maybe check this thing
	}
	
	public Symbol numerator() {
		return this.getParams().get(0);
	}
	
	public Symbol denominator() {
		return this.getParams().get(1);
	}
	
	public void setNumerator(Symbol newNumerator) {
		this.parameters.getParameters().set(0, newNumerator);
	}
	
	public void setDenominator(Symbol newDenominator) {
		this.parameters.getParameters().set(1, newDenominator);
	}

	@Override
	public LatexExpression latex(VariableIDTable idList) {
		return new LatexDivision(this.numerator().latex(idList), this.denominator().latex(idList));
	}
	
	/*@Override
	public String print() {
		StringBuilder build = new StringBuilder();
		super.putParanthessesSymbol(build, this.numerator());
		build.append("/");
		super.putParanthessesSymbol(build, this.denominator());
		return build.toString();
	}*/

	@Override
	protected SymbolID createIdType() {
		return new NoId();
	}

	@Override
	protected SymbolStandarizer createStd() {
		return new FractionNumberStandarizer(new RawNumberFilter());
	}

	@Override
	protected FullSimplifier createSimp() {
		DirectSimplifierSet simps = new DirectSimplifierSet(new FractionNumberSimplifier(this, new RawNumberFilter()),
															new FractionZeroNumerator(this),
															new FractionCancelation(this));
		return new FullSimplifier(this, simps);
	}

	
	///TODO Check patterns identification is working fine
	@Override
	protected SymbolPatternIdentifier createId() {
		return new SymbolPatternIdentifier(new NotRawNumber(),
											new FractionImplicatedNumber(this),
											new FractionTranslatedNumberIdentifier(this),
											new FractionPolynomIdentifier(this));
	}

	@Override
	protected Parameters createParameters() {
		return new FixedSizeParameters(new ParameterOrderedEquator(), 2);
	}

	@Override
	public SymType getType() {
		return SymType.Fraction;
	}
	
	@Override
	public DissolvedSymbol toDissolvedSymbol() {
		DissolvedSymbol dissolved = new DissolvedSymbol();
		DissolvedSymbol disNumerator = this.numerator().toDissolvedSymbol();
		DissolvedSymbol disDenominator = this.denominator().toDissolvedSymbol();
		dissolved.mixDissolvedSymbol(disNumerator);
		
		for (Symbol nextBase : disDenominator.dissolvedSymbols())
			disDenominator.multiplyDissolvedSymbolExpo(nextBase, NumberSym.intSymbol(-1));
		
		dissolved.mixDissolvedSymbol(disDenominator);
		return dissolved;
	}

}
