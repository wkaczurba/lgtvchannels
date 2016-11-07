package com.kaczurba.lgtvchannels.gui.inputfilters;

import org.junit.*;
import org.junit.Assert.*;
import java.util.LinkedHashSet;

public class DataModelTest {
	
	DataModel<Object> dataModel = new DataModel<>();
	
	@Test
	public void addTest() {
		// TODO: Add observer;
		
		LinkedHashSet<Object> values = new LinkedHashSet<Object>();
		dataModel.add("abc", values);
		
		Assert.assertEquals(values, dataModel.get("abc"));
	}
	
	// TODO: More tests...
}

