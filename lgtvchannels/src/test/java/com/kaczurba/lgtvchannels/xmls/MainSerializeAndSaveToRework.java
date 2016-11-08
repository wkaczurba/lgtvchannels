package com.kaczurba.lgtvchannels.xmls;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.kaczurba.lgtvchannels.xmls.ImmutableItemTag;
import com.kaczurba.lgtvchannels.xmls.XMLMappedItemTag;

@SuppressWarnings("deprecation")
public class MainSerializeAndSaveToRework {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//Main main = new Main();
		List<XMLMappedItemTag> itemTags = MainToRework.readItemTags("GlobalClone00001.xml");
		
		List<ImmutableItemTag> immutableItemTags = 
				itemTags.stream().limit(400)
					.map(x -> (new ImmutableItemTag.Builder(x)).build())
					.collect(Collectors.toList());
		
		MainToRework.serializeItemsToFile(Paths.get("itemTags.obj"), immutableItemTags);
		System.out.println("Saved.");
		
		List<ImmutableItemTag> readBack = MainToRework.deserializeItemsTagsFromFile(Paths.get("itemTags.obj"));
		readBack.forEach(x -> System.out.println(x + System.lineSeparator()));
	}
}
