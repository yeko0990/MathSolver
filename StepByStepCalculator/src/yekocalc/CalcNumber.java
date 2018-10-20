package yekocalc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.math.*;

public class CalcNumber {
	private static final CalcNumber DEFAULT_EPSILON = new CalcNumber(0.000000001);
	public static final CalcNumber ZERO = new CalcNumber(0);
	
	public static CalcNumber parse(String str) {
		return new CalcNumber(Double.parseDouble(str));
	}
	
	///Field is final so a CalcNumber instance would represent a specific value, and could be used in many places.
		private final double val;
		public CalcNumber(int num) {
			val = (double)num;
		}
		public CalcNumber(double num) {
			val = num;
		}
		
		@Override
		public String toString() {
			DecimalFormat formatter = new DecimalFormat("#.#######");
			return formatter.format(val);
			
		}

		public boolean isInteger() {
			return val - Math.floor(val) == 0;
		}
		
		public boolean isNegative() {
			return val < 0;
		}

		public double getDouble() {
			return val;
		}
		//@Override
		//public void set(int num) {
		//	val = (double)num;
		//}
		//@Override
		//public void set(double num) {
		//	val = num;
		//}
		
		public CalcNumber add(CalcNumber v) {
			return new CalcNumber(val + v.getDouble());
		}
		public CalcNumber sub(CalcNumber v) {
			return new CalcNumber(val - v.getDouble());
		}
		public CalcNumber mul(CalcNumber v) {
			return new CalcNumber(val * v.getDouble());
		}
		public CalcNumber div(CalcNumber v) {
			return new CalcNumber(val / v.getDouble());
		}
		
		public CalcNumber pow(CalcNumber v) {
			///TODO For now, lets just implement a power by non-negative int
			///(NEED TO BE CHANGED EVENTUALLY)		
			double originalVal = 1;
			if (!v.isInteger()) throw new IllegalArgumentException();
			
			int intVal = (int) v.getDouble();
			for (int i = 0; i < intVal; i++) originalVal*=val;
			return new CalcNumber(originalVal);
		}
		
		private CalcNumber average(CalcNumber n1, CalcNumber n2) {
			return n1.add(n2).div(new CalcNumber(2));
		}
		
		///A root of this number IF IT IS A POSITIVE NUMBER!
		private CalcNumber positiveNumberRoot(CalcNumber rootBy) {
																				///TODO maybe create a PositiveCalcNumber
																				///class.
			if (this.isNegative() || !rootBy.isInteger() || rootBy.isNegative()) throw new IllegalArgumentException();
			CalcNumber TWO = new CalcNumber(2);
			CalcNumber high = this.pow(rootBy);
			CalcNumber low = CalcNumber.ZERO;
			CalcNumber root = high;
			CalcNumber ONE = new CalcNumber(1);
			CalcNumber rootPower = root.pow(rootBy);
			while (!rootPower.epsilonEquals(this)) {
				int compare = rootPower.compareTo(this);
				if (compare == 1) {
					high = average(high, low);
				}
				///Else compare == -1, beacause rootPower != this
				else if (compare == -1) {
					low = average(high, low);
				}
				
				root = average(high, low);
				rootPower = root.pow(rootBy);
			}
			return root;
		}
		
		public CalcNumber root(CalcNumber rootBy) {
			if (rootBy.isNegative() || !rootBy.isInteger()) throw new RuntimeException(); ///TODO make exception for this
																						  ///and/or implement this
			if (this.isNegative()) throw new NegativeRootException(); ///TODO implement a root of a negative number
			
			CalcNumber positiveRoot = this.positiveNumberRoot(rootBy);
			//ArrayList<CalcNumber> ret = new ArrayList<CalcNumber>();
			//if (rootBy.div(new CalcNumber(2)).isInteger()) ret.add(positiveRoot.minus());
			//ret.add(positiveRoot);
			return positiveRoot;
			
		}
		public CalcNumber minus() {
			return mul(new CalcNumber(-1));
		}
		
		public CalcNumber abs() {
			return this.val >= 0 ? this : this.minus();
		}
		
		public CalcNumber copy() {
			return new CalcNumber(val);
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(val);
		}
		
		@Override
		public boolean equals(Object otherNum) {
			if (otherNum instanceof CalcNumber) {
				CalcNumber otherDoub = (CalcNumber) otherNum;
				return otherDoub.compareTo(this) == 0;
			}
			else return false;
		}
		
		///Returns true if this - otherNum < epsilon.
		public boolean epsilonEquals(CalcNumber otherNum, CalcNumber EPSILON) {
			return this.sub(otherNum).abs().compareTo(EPSILON) == -1;
		}
		
		///Returns true if this - otherNum <= epsilon.
		public boolean epsilonEquals(CalcNumber otherNum) {
			return epsilonEquals(otherNum, CalcNumber.DEFAULT_EPSILON);
		}
		
		public int compareTo(CalcNumber c) {
			double ret = val - c.getDouble();
			if (ret < 0) return -1;
			if (ret > 0) return 1;
			else return 0;
		}

		
}
