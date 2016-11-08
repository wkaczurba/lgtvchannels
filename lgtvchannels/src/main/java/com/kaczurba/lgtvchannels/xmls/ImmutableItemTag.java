package com.kaczurba.lgtvchannels.xmls;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Deprecated
public final class ImmutableItemTag implements Item, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2262386998721654578L;
	final private Map<String, String> map;
	
	public ImmutableItemTag(Map<String, String> map) {

		//this.map = Collections.unmodifiableMap( new HashMap<>(map) );
		this.map = new LinkedHashMap<>(map);
		//throw new IllegalStateException("ImmutableItemTags should not be used.");
	}
	
	public ImmutableItemTag(Item item) {
		this(item.getAsMap());
	}

	@Override
	public Set<String> getKeys() {
		return Collections.unmodifiableSet( map.keySet() );
	}

	@Override
	public String get(String key) {
		return map.get(key);
	}
	
	@Override
	public Map<String, String> getAsMap() {
		return Collections.unmodifiableMap(map);
	}
	
	@Override
	public String toString() {
		//return getKeys().stream().map(k -> k + ":" + get(k)).collect(Collectors.joining(System.lineSeparator()));
		
		List<String> preferredKeys = Arrays.asList("vchName", "prNum");
		
		return preferredKeys.stream().map(k -> k + ":" + get(k)).collect(Collectors.joining(System.lineSeparator()));
		//return preferredKeys.stream().map(k -> k + ":" + get(k)).collect(Collectors.joining(" "));
	}
	
/*	public ImmutableItemTag set(String key, String value) {
		Builder builder = new Builder(this);
		builder.set(key, value);
		return builder.build();
	}*/
	
	public void set(String key, String value) {
		throw new IllegalStateException("Cannot set value in ImmutableItemTag.");
		//map.put(key, value);
	}
	
	public static class Builder {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		public Builder(Item item) {
			this.map = new LinkedHashMap<>(item.getAsMap());
		}
		
		/*public Builder(ItemTag itemTag) {
			 //itemTag.getKeys().stream().forEach(k -> map.put(k, itemTag.get(k)));
			this.map = new LinkedHashMap<>(itemTag.getAsMap());
		}*/
		
		public ImmutableItemTag build() {
			return new ImmutableItemTag(map);
		}

		public String get(String key) {
			return map.get(key);
		}
		
		public void set(String key, String value) {
			map.put(key, value);
		}
	}
}
