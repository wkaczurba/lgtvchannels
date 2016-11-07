package com.kaczurba.lgtvchannels.xmls;

import java.util.Map;
import java.util.Set;

public interface Item {
	public Set<String> getKeys();
	public String get(String key);
	public Map<String, String> getAsMap();
	//public void extraOne();
	public void set(String key, String value);

}
