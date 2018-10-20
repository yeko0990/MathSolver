package latexpression;
import java.util.*;

import yekocalc.CalcNumber;

public class LatexNumberElement extends LatexElement {
	CalcNumber num; ///TODO replace with calc number
	
	public LatexNumberElement(CalcNumber num) {
		super(new ExpectingOperatorPutMultiplies());
		this.num = num;
	}
	
	public LatexNumberElement(int num) {
		this(new CalcNumber(num));
	}
	
	public LatexNumberElement(String num) {
		this(CalcNumber.parse(num)); ///TODO Change to CalcNumber
	}
	
	@Override
	public void getNumber(NumberElementContext context) {
		context.num = this.num;
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}

}
