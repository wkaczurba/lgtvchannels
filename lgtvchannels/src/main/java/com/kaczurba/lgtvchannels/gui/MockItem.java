package com.kaczurba.lgtvchannels.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kaczurba.lgtvchannels.xmls.Item;

public class MockItem implements Item{

	@Override
	public Set<String> getKeys() {
		return new HashSet<>(Arrays.asList("prNum", "vchName"));
	}

	@Override
	public String get(String key) {
		switch(key) {
			case "prNum": return "23";
			case "vchName": return "BBC";
			default: throw new IllegalArgumentException("No key: " + key); 
		}
	} 
	
	public String toString() {
		return getKeys().stream().map(k -> k + "=" + get(k)).collect(Collectors.joining(" "));
	}

	@Override
	public Map<String, String> getAsMap() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Need to implement this function so it returns Map<String, String>");
		//return null;
	}

	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Need to implement this function so a value can be assigned");
		
	}
}
