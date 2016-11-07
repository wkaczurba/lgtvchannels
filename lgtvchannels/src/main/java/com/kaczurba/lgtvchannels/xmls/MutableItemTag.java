package com.kaczurba.lgtvchannels.xmls;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public final class MutableItemTag implements Item, Serializable {
	final private Map<String, String> map;
	
	public MutableItemTag(Item item) {
		this.map = new LinkedHashMap<>(item.getAsMap());
		// this.map = new LinkedHashMap<>(item.getAsMap());
	}
	
	public MutableItemTag(Map<String, String> map) {
		//this.map = Collections.unmodifiableMap( new HashMap<>(map) );
		this.map = new LinkedHashMap<>(map);
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
		
//		List<String> preferredKeys = Arrays.asList("vchName", "prNum");
//		return preferredKeys.stream().map(k -> k + ":" + get(k)).collect(Collectors.joining(System.lineSeparator()));
		
		StringBuilder sb = new StringBuilder();
		String vchName = get("vchName");
		String prNum = get("prNum");
		if (vchName.trim().isEmpty()) {
			vchName="<Noname>";
		}

		return String.format( "%s (%s)", vchName, prNum);
	}
	
/*	public ImmutableItemTag set(String key, String value) {
		Builder builder = new Builder(this);
		builder.set(key, value);
		return builder.build();
	}*/
	
	public void set(String key, String value) {
		//throw new IllegalStateException("Cannot set value in ImmutableItemTag.");
		map.put(key, value);
	}
	
	/*public static class Builder {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		public Builder(Item item) {
			this.map = new LinkedHashMap<>(item.getAsMap());
		}
		
		//public Builder(ItemTag itemTag) {
		//	 itemTag.getKeys().stream().forEach(k -> map.put(k, itemTag.get(k)));
		//	this.map = new LinkedHashMap<>(itemTag.getAsMap());
		//}
		
		public MutableItemTag build() {
			return new MutableItemTag(map);
		}

		public String get(String key) {
			return map.get(key);
		}
		
		public void set(String key, String value) {
			map.put(key, value);
		}
	}*/
}
