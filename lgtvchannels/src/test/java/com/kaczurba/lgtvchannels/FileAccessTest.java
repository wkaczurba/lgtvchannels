package com.kaczurba.lgtvchannels;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
//import static org.junit.Assert.*;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@RunWith
public class FileAccessTest {

	@Test
	public void testOne() throws URISyntaxException {
		URL url = this.getClass().getClassLoader().getResource("com/kaczurba/lgtvchannels/GlobalClone00001.xml");
		Path path = Paths.get(url.toURI());
		
		Assert.assertEquals(true, Files.exists(path));
		// passing test
	}
	
	// How to do a test runner?
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(FileAccessTest.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.getTrace());
		}
		
		System.out.println(result.wasSuccessful());
	}
	
}
