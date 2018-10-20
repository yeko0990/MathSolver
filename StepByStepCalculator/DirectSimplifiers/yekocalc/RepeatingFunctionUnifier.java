package yekocalc;
import java.util.*;

///TODO write documentation
///REPEATING FUNCTION is a function that means REPEATING the same function over and over.
///Multiplication is repetitions of additions, Power is repetitions of multiplications.
///this simplifier unifies repeating functions.
///
///UNIFIABLE SYMBOL is
public abstract class RepeatingFunctionUnifier implements DirectSimplifier {
	
	protected abstract Unifiable getEffectiveUnifiable(Symbol sym, int index); //Should only return unifiables that may
																				//be unified.
	
	protected abstract UnifiableGroup createGroup(Symbol unifiable, int index); 
	
	protected abstract List<Symbol> getParameters(); ///Overriden to return the simplified symbol's parameters.
	protected abstract void setParameters(List<Symbol> newParams); ///Overriden to set the simplified symbol's parameters.
	
	//protected abstract boolean isEffectiveUnifiable(Symbol unifiable);
	
	/*private List<Symbol> getEffectiveUnifiableSymbols(Symbol sym) {
		List<Unifiable> unifiables = getAllUnifiableSymbols(sym);
		List<Unifiable> effectiveUnifiables = new LinkedList<Symbol>();
		for (Unifiable next : unifiables) {
			if (isEffectiveUnifiable(next)) effectiveUnifiables.add(next);
		}
		return effectiveUnifiables;
	}*/
	
	private List<Unifiable> getEffectiveUnifiablesInParams(List<Symbol> params) {
		List<Unifiable> allUni = new LinkedList<Unifiable>();
		Unifiable nextUni;
		Symbol nextParam;
		for (int i = 0; i < params.size(); i++) {
			nextParam = params.get(i);
			nextUni = getEffectiveUnifiable(nextParam, i);
			if (nextUni != null)allUni.add(nextUni);
		}
		return allUni;
	}
	
	private UnifiableGroup nextSortedUnifiablesList(List<Unifiable> unifiables) {
		if (unifiables.size() == 0) return null;
		Symbol toSearch = unifiables.get(0).getUnifiable();
		int toSearchIndex = unifiables.get(0).getIndex();
		UnifiableGroup buff = createGroup(toSearch, toSearchIndex);
		Unifiable next;
		Symbol nextUnifiable;
		Iterator<Unifiable> iterate = unifiables.iterator();
		while (iterate.hasNext()) {
			next = iterate.next();
			nextUnifiable = next.getUnifiable();
			if (nextUnifiable.equals(toSearch)) {
				buff.add(next.getRepetitionArgument());
				iterate.remove();
			}
		}
		return buff;
	}
	
	///TODO OPTIMIZATION- save the indexes of this unifiables, instead of searching each one to remove it.
	private void removeUnifiables(List<Symbol> params, List<Unifiable> unifiables) {
		for (Unifiable nextUni : unifiables) {
			params.remove(nextUni.getFunction());
		}
	}
	
	///Returns the lowest index of the indices
	private int searchIndicesAndGetMinIndex(List<Symbol> params, List<Argument> toSearch, List<Integer> indices) {
		///TODO OPTIMIZATION- Try to use normal HashMap because the keys would not change during the
		///run of this function. (or would they?)
		MutableKeyHashMap<Symbol, Integer> timesFound = new MutableKeyHashMap<Symbol, Integer>();
		int minIndex = -1;
		int nextIndex;
		int timesSearched; //The number of times the same symbol has been searched;
		for (Argument nextSearch: toSearch) {
			timesSearched = timesFound.containsKey(nextSearch.originalFunc) ? timesFound.get(nextSearch.originalFunc) : 0;
			nextIndex = ListUtils.indexOf(params, nextSearch.originalFunc, timesSearched);
			if (nextIndex != -1) {
				indices.add(nextIndex);
				timesFound.put(nextSearch.originalFunc, timesSearched+1);
				if (minIndex == -1 || nextIndex < minIndex) minIndex = nextIndex;
			}
		}
		return minIndex;
	}
	
	private List<Symbol> withoutIndices(List<Symbol> params, List<Integer> indices) {
		List<Symbol> newParams = new LinkedList<Symbol>();
		ListUtils.copyWithoutIndices(ListUtils.toArray(indices), params, newParams);
		return newParams;
	}
	
	///Returns the lowest index from the removed unifiables' indices.
	/*private int removeUnifiableGroup(List<Symbol> params) {
		List<Argument> args = group.group();
		for (Argument next : args) {
			params.remove(next.originalFunc);
		}
	}*/
	
	///Breaks down all of the parameters to Unifiables, than sorts them into lists and return the lists.
	private List<UnifiableGroup> getSortedUnifiables(List<Symbol> params) {
		List<Unifiable> allUnifiables = getEffectiveUnifiablesInParams(params);
		//List<Unifiable> toRemove = new LinkedList<Unifiable>(allUnifiables); ///allUnifiables is being emptied before
																			 ///removal of the unifiables, thus we save
																			 ///it for the remove function.
		/*List<Unifiable> nextUnifiables;
		for (Symbol nextParam : params) {
			nextUnifiables = getEffectiveUnifiableSymbols(nextParam);
			for (Symbol unifiable : nextUnifiables) {
				if (allUnifiables.contains(unifiable)) return unifiable;
				else allUnifiables.add(unifiable);
			}
		}*/
		List<UnifiableGroup> sortedUnifiables = new LinkedList<UnifiableGroup>();
		while (allUnifiables.size() > 0) sortedUnifiables.add(nextSortedUnifiablesList(allUnifiables));
		
		//removeUnifiables(params, toRemove);
		return sortedUnifiables;
	}
	
	private boolean willUnify(List<UnifiableGroup> toUnify) {
		for (UnifiableGroup nextGroup : toUnify) {
			if (nextGroup.group().size() > 1) return true;
		}
		return false;
	}
	
	@Override
	public final boolean simplifyNext() {
		List<Symbol> newParams = new LinkedList<Symbol>(this.getParameters());
		int originalSize = newParams.size(); ///Used to convert original indices of unifiables to indexes
											 ///AFTER removal of unifiables.
		List<UnifiableGroup> toUnify = getSortedUnifiables(newParams);
		if (!willUnify(toUnify)) return false;
		else {
			int updatedIndex;
			List<Integer> toRemoveIndices = new LinkedList<Integer>();
			Symbol nextUnify;
			for (UnifiableGroup nextList : toUnify) {
				updatedIndex = searchIndicesAndGetMinIndex(newParams, nextList.group(), toRemoveIndices);
				newParams = withoutIndices(newParams, toRemoveIndices);
				nextUnify = nextList.unify();
				newParams.add(updatedIndex, nextUnify);
				toRemoveIndices.clear();
			}
			///TODO maybe standarize? Check if needed. Probably not.
			this.setParameters(newParams);
			return true;
		}
	}
}
