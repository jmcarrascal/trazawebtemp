package ar.com.cipres.util;

public class KeyValuePair<K, V> {
	
	K key;
	V value;
	
	public KeyValuePair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	
	
}
