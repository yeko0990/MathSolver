package yekocalc;
import java.util.*;

public class ListUtils {
	static private <T> void fillList(List<T> toFill, T[] objs) {
		for (T t : objs) toFill.add(t);
	}
	
	public static <T> LinkedList<T> toLinkedList(T...objs) {
		LinkedList<T> list = new LinkedList<T>();
		fillList(list ,objs);
		return list;
	}
	
	private static boolean isSkippedIndex(int index, int[] indices) {
		for (int i = 0; i < indices.length; i++) {
			if (index == indices[i]) return true;
		}
		return false;
	}
	
	///Copies list while skipping specified indices in the original list
	public static <T> void copyWithoutIndices(int[] indices, List<T> copyFrom, List<T> copyTo) {
		for (int i = 0; i < copyFrom.size(); i++) {
			if (!isSkippedIndex(i, indices)) copyTo.add(copyFrom.get(i));
		}
	}
	
	public static <T> void copyWithoutIndex(int index, List<T> copyFrom, List<T> copyTo) {
		copyWithoutIndices(new int[] {index}, copyFrom, copyTo);
	}
	
	///Generates hash code of the list WITH importance of the order of elements
	public static <T> int orderedHashCode(List<T> list) {
		if (list == null) return 0;
		int result = 1;
		for (int i = 0; i < list.size(); i++) {
			result += (list.get(i).hashCode() * i); 
		}
		return result;
	}
	
	///Generates hash code of the list WITHOUT importance of the order of elements
	public static <T> int unorderedHashCode(List<T> list) {
		if (list == null) return 0;
		int result = 1;
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i).hashCode(); 
		}
		return result;
	}
	
	public static int[] toArray(List<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	public static <T> int indexOf(List<T> list, T toSearch, int timesToSkip) throws IllegalArgumentException {
		if (timesToSkip < 0) throw new IllegalArgumentException();
		T nextElement;
		for (int i = 0; i < list.size(); i++) {
			nextElement = list.get(i);
			if (!toSearch.equals(nextElement)) continue;
			if (timesToSkip > 0) timesToSkip--;
			else return i;
		}
		return -1;
	}

}
