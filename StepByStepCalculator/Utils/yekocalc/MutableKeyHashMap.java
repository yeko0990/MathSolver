package yekocalc;
import java.util.*;


///TODO NOT SAFE!
///		We are able to get the keySet. Therefore, we may add keys through the set and break this class mechanism of
///		adding keys.
///		To solve it we should return an immutable set OR delete this method and face the (light) consequences.
///		BE CAREFUL if you decide to return a copy of the associated keySet because bugs will occur yet no compiler
///		error will.

///A hash map that uses MUTABLE keys- when using a key for the first time, 
///a deep copy is created to be used as the key.
public class MutableKeyHashMap<K extends DeepCopiable<K>, V> {
	private HashMap<K, V> hashTable;
	public MutableKeyHashMap() {
		hashTable = new HashMap<K, V>();
	}
	
	public V put(K key, V val) {
		if (hashTable.containsKey(key)) return hashTable.put(key, val);
		else return hashTable.put(key.deepCopy(), val);
	}
	
	public V get(Object key) {
		return hashTable.get(key);
	}
	
	public boolean containsKey(K key) {
		return hashTable.containsKey(key);
	}
	
	public Set<K> keySet() {
		return hashTable.keySet();
	}
	
}
