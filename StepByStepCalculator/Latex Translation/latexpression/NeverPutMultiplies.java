package latexpression;

///Typically assigned to operators, as they never expect an operator after them or before them.
public class NeverPutMultiplies implements PutMultipliesBehavior {

	@Override
	public boolean putMultiplies(PutMultipliesContext context) {
		context.previousElementExpectingOperator = false;
		return false;
	}

}
