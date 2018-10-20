package latexpression;
import java.util.*;

public class LatexUserString {
	private String str;
	private final HashMap<Character, Boolean> digits;
	private final HashMap<Character, Boolean> vars;
	private final HashMap<Character, LatexElement> operators;
	//private final HashMap<String, WordParser> words;
	private final String SQRT_WORD = "sqrt";
	private final char OPEN_NEST = '(';
	private final char CLOSE_NEST = ')';

	///TODO implement LatexWord
	//private final HashMap<Character, LatexWord> words;
	
	private void putStr(HashMap<Character, Boolean> map, String str) {
		char[] arr = str.toCharArray();
		for (int i = 0; i < arr.length; i++) map.put(arr[i], true);
	}
	
	public LatexUserString(String str) {
		this.str = str;
		digits = new HashMap<Character, Boolean>();
		for (int i = 0; i < 10; i++) digits.put(new Integer(i).toString().toCharArray()[0], true);
		
		vars = new HashMap<Character, Boolean>();
		putStr(vars, "xyab");
		
		operators = new HashMap<Character, LatexElement>();
		operators.put('+', LatexStaticElements.ADDITION);
		operators.put('-', LatexStaticElements.MINUS);
		operators.put('*', LatexStaticElements.MULTIPLY);
		operators.put('/', LatexStaticElements.DIVISION);
		operators.put('^', LatexStaticElements.POWER);
		
		//words = new HashMap<String, WordParser>(); 
		//words.put("\\sqrt", new SqrtParser());
	}
	
	
	private void addNum(LatexElementList list, String number) {
		list.addElement(new LatexNumberElement(number));
	}
	
	private int tryGetNum(LatexElementList list, int index) {
		StringBuilder number = new StringBuilder();
		int startIndex = index;
		char digit = str.charAt(index);
		while (digits.containsKey(digit)) {
			number.append(digit);
			index++;
			if (index >= str.length()) break;
			digit = str.charAt(index);
		}
		if (index != startIndex) addNum(list, number.toString());
		return index;
	}
	
	private void addOp(LatexElementList list, int index, char v) {
		list.addElement(operators.get(v));
	}
	
	private int tryGetOperator(LatexElementList list,  int index) {
		char v = str.charAt(index);
		if (operators.containsKey(v)) {
			addOp(list, index, v);
			return index+1;
		}
		else return index;
	}
	
	private void addVar(LatexElementList list, char v) {
		list.addElement(new LatexVariableElement(v));
	}
	
	private int tryGetVar(LatexElementList list, int index) {
		char v = str.charAt(index);
		if (vars.containsKey(v) && vars.get(v)) {
			addVar(list, v);
			return index+1;
		}
		else return index;
	}
	
	private int tryGetNestings(LatexElementList list, int index) {
		char c = str.charAt(index);
		if (c == this.OPEN_NEST || c == this.CLOSE_NEST) {
			index++;
			list.addElement(c == this.OPEN_NEST ? new LatexOpenElement() : new LatexCloseElement());
		}
		return index;
	}
	
	private LatexElementList defaultRootBy() {
		LatexElementList ret = new LatexElementList();
		ret.addElement(new LatexNumberElement(2));
		return ret;
	}
	
	private int getSqrt(LatexElementList list, int index) {
		index += SQRT_WORD.length();
		if (index >= str.length() - 2) throw new IllegalLatexStringException(); 
		LatexUserString rootBy = null, base = null;
		if (str.charAt(index) == '[') {
			int startInd = index+1;
			while (str.charAt(index) != ']') index++;
			String rootByStr = str.substring(startInd, index);
			rootBy = new LatexUserString(rootByStr);
			index++;
		}
		if (str.charAt(index) != '(') throw new IllegalLatexStringException();
		int startInd = index+1;
		while (str.charAt(index) != ')') index++;
		String baseStr = str.substring(startInd, index);
		base = new LatexUserString(baseStr);
		
		LatexElementList rootByElements = rootBy == null ? defaultRootBy() : rootBy.toElements();
		LatexElementList baseElements = base.toElements();
		list.addElement(new LatexSqrtElement(baseElements, rootByElements)); 
		
		index++; //because index now points to '}'
		return index;
	}
	
	private boolean startsWithWord(String word, int index) {
		if (str.length() - index < word.length()) return false;
		for (int i = 0; i < word.length(); i++) if (str.charAt(i+index) != word.charAt(i)) return false;
		return true;
	}
	
	private int tryGetWord(LatexElementList list, int index) {
		if (startsWithWord(SQRT_WORD, index)) return getSqrt(list, index);
		else return index;
	}
	
	private String removeSpaces() {
		StringBuilder buff = new StringBuilder(str.length());
		char next;
		for (int i = 0; i < str.length(); i++) {
			next = str.charAt(i);
			if (next != ' ') buff.append(next);
		}
		return buff.toString();
	}
	
	public LatexElementList toElements() throws IllegalElementListException {
		str = removeSpaces();
		int index = 0;
		int startIndex;
		LatexElementList list = new LatexElementList();
		while (index < str.length()) {
			startIndex = index;
			index = tryGetWord(list, index);
			if (index != startIndex) continue;
			
			index = tryGetNestings(list, index);
			if (index != startIndex) continue;
			
			index = tryGetVar(list, index);
			if (index != startIndex) continue;
			
			index = tryGetOperator(list, index);
			if (index != startIndex) continue;
			
			index = tryGetNum(list, index);
			if (index != startIndex) continue;
			
			throw new IllegalElementListException();
		}
		return list;
	}
	
	public LatexExpression toExpression() throws IllegalElementListException {
		return this.toElements().evaluate();
	}

}
