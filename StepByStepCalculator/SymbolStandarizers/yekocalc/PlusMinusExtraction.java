package yekocalc;

public class PlusMinusExtraction extends SymbolStandarizer {
	private PlusMinus pm;
	
	public PlusMinusExtraction(PlusMinus pm) {
		this.pm = pm;
	}

	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		Symbol extractionResult = new PlusMinusExtractionVisitor().shouldExtractPlusMinus(pm.parameter());
		if (extractionResult == null ) {
			tookAction.set(false);
			return pm;
		}
		else {
			tookAction.set(true);
			return extractionResult;
		}
	}

}
