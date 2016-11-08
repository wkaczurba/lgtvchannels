package com.kaczurba.lgtvchannels.xmls;

import java.io.Serializable;
import java.util.*;

public final class MutableItemTag implements Item, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5329780161538671921L;
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
		//StringBuilder sb = new StringBuilder();
		String vchName = get("vchName");
		String prNum = get("prNum");
		if (vchName.trim().isEmpty()) {
			vchName="<Noname>";
		}

		return String.format( "%s (%s)", vchName, prNum);
	}
	
	public void set(String key, String value) {
		//throw new IllegalStateException("Cannot set value in ImmutableItemTag.");
		map.put(key, value);
	}	
}
