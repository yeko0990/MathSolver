package yekocalc;

///Does not perform any simplification of the symbol
public class NoSimplify implements DirectSimplifier {

	@Override
	public boolean isLightSimplification() {
		return true;
	}
	
	@Override
	public boolean simplifyNext() {
		return false;
	}

}
