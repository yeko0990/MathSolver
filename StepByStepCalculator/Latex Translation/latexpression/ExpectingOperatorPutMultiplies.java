package latexpression;

public class ExpectingOperatorPutMultiplies implements PutMultipliesBehavior {
	@Override
	public boolean putMultiplies(PutMultipliesContext context) {
		boolean ret = context.previousElementExpectingOperator; ///If previous element expecting operator, we should
																///add a multiply between us and the previous element.
		
		context.previousElementExpectingOperator = true;
		return ret;
	}

}
