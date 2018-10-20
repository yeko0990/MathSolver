package yekocalc;

///TODO: BROKEN! implement some method to check whether an expression is fully simplified WITHOUD ACTUALLY SIMPLIFYING IT.

///IMPORTANT: Calls simplifyNext() to check whether the expression is fully simplified.
public class IsFullySimplified extends Condition {
	Expression ex;
	public IsFullySimplified(Expression exp) {
		ex = exp;
	}
	
	@Override
	public boolean resolve() {
		return ex.simplifyNext();
	}

}
