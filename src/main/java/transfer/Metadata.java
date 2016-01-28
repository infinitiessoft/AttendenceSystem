package transfer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Metadata implements Map<String, Object> {

	private Map<String, Object> inner;

	public Metadata() {
		inner = new HashMap<String, Object>();
	}

	@Override
	public int size() {
		return inner.size();
	}

	@Override
	public boolean isEmpty() {
		return inner.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return inner.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return inner.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return inner.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return inner.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return inner.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		inner.putAll(m);
	}

	@Override
	public void clear() {
		inner.clear();
	}

	@Override
	public Set<String> keySet() {
		return inner.keySet();
	}

	@Override
	public Collection<Object> values() {
		return inner.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return inner.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return inner.equals(o);
	}

	@Override
	public int hashCode() {
		return inner.hashCode();
	}

}
