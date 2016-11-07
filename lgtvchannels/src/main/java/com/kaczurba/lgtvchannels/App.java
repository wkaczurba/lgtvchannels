package com.kaczurba.lgtvchannels;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.kaczurba.lgtvchannels.gui.FrameHandler;
import com.kaczurba.lgtvchannels.gui.MainFrame;
import com.kaczurba.lgtvchannels.xmls.Item;
import com.kaczurba.lgtvchannels.xmls.LGTVXml;

public class App {
	final List<Item> leftList = new ArrayList<>();
	final List<Item> rightList = new ArrayList<>();
//	final FrameOne frame = new FrameOne();
	final MainFrame frame = new MainFrame();
	
	private final LGTVXml lgtvXml;
	//LGTVXml lgtvXml; // = new LGTVXml(doc);
	
	FrameHandler frameHandler = new FrameHandler() {
		

		@Override
		public void moveRight(ActionEvent e, List<Item> selectedItems) {
			// TODO Auto-generated method stub
			System.out.println("moveRight received; selected: " + selectedItems);
			leftList.removeAll(selectedItems);
			for (Item item : selectedItems) {
				rightList.add(item);
			}
			frame.refresh();
		}

		@Override
		public void moveLeft(ActionEvent e, List<Item> what) {
			// TODO: Make it remember original positions of stuff.
			System.out.println("moveLeft received; selected: " + what);
			
			rightList.removeAll(what);
			for (Item item : what) {
				leftList.add(item);
			}
			frame.refresh();
		}

		@Override
		public void moveUp(ActionEvent e, List<Item> what) {
			System.out.println("moveUp received; selected: " + what);
			if (what.isEmpty())
				return;
			
			List<Integer> listOfIndexes = what.stream().map(x -> rightList.indexOf(x)).sorted().collect(Collectors.toList());
			boolean canBeMoved = listOfIndexes.get(0) > 0;
			if (canBeMoved) {
				listOfIndexes.stream().forEach(idx -> Collections.swap(rightList, idx, idx-1));
			}
			// TODO: Add code that updates values in the list.
			
			frame.refresh();
		}
		
		@Override
		public void moveDown(ActionEvent e, List<Item> what) {
			System.out.println("moveDown received; selected: " + what);
			if (what.isEmpty())
				return;
			
			List<Integer> reversedListOfIndexes = what.stream()
					.map(x -> rightList.indexOf(x))
					.sorted(Comparator.reverseOrder())
					.collect(Collectors.toList());
			
			boolean canBeMoved = reversedListOfIndexes.get(0)+1 < rightList.size();
			
			if (canBeMoved) {
				reversedListOfIndexes.stream().forEach(idx -> Collections.swap(rightList, idx, idx+1));
			} else {
				System.out.format("Cannot be moved: reversedListOfIndexes.get(0)+1 == %d < rightList.size() == %d;%n", reversedListOfIndexes.get(0)+1, rightList.size());
			}
			
			frame.refresh();
		}

		@Override
		public void undo(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("undo received");
		}

		@Override
		public void saveWIP(ActionEvent e) {
			// TODO: Saving to a file.
			lgtvXml.removeAllItemTags();
			lgtvXml.newAddChannels2(leftList);
			lgtvXml.writeXml(Paths.get("tmpSaveLeft.xml"));
			System.out.println("Saved: leftList().size=" + leftList.size());
			
			//leftList.stream().forEach(System.out::println);
			lgtvXml.removeAllItemTags();
			lgtvXml.newAddChannels2(rightList);
			lgtvXml.writeXml(Paths.get("tmpSaveRight.xml"));
			System.out.println("Saved: rightList().size=" + rightList.size());
			
			//rightList.stream().forEach(System.out::println);
			System.out.println("Saved (shoulld be...");
		}

		@Override
		public void load(ActionEvent e) {
			final Path pathLeft = Paths.get("tmpSaveLeft.xml");
			final Path pathRight = Paths.get("tmpSaveRight.xml");
			// TODO Auto-generated method stub
			System.out.println("load received");
			Path path = Paths.get("output.xml");
			
			leftList.clear();
			rightList.clear();
			
			try {
//				LGTVXml.fromFile(pathLeft).readItemTagsAsImmutableItem().forEach(x -> leftList.add(x));
				LGTVXml.fromFile(pathLeft).readItemTagsAsMutableItem().forEach(x -> leftList.add(x));
//				LGTVXml.fromFile(pathRight).readItemTagsAsImmutableItem().forEach(x -> rightList.add(x));
				LGTVXml.fromFile(pathRight).readItemTagsAsMutableItem().forEach(x -> rightList.add(x));
				//rightList = LGTVXml.fromFile(pathRight).readItemTags2();
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			frame.refresh();
		}

		@Override
		public void redo(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("redo received");
		}

		@Override
		public void newFromFile(ActionEvent e) {
			final Path path = Paths.get("GlobalClone00001.xml"); 
			// TODO Auto-generated method stub
			
			leftList.clear();
			rightList.clear();
			
			System.out.println("PATH TO THE FILE HAS TO BE ADDED HERE. " + path);
			try {
//				LGTVXml.fromFile(path).readItemTagsAsImmutableItem().forEach(x -> leftList.add(x));
				LGTVXml.fromFile(path).readItemTagsAsMutableItem().forEach(x -> leftList.add(x));
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			frame.refresh();
		}
	};
	
/*	public static void mockMove(FrameHandler frameHandler, ActionEvent e, List<Item> leftList) {
		// Mock move
		frameHandler.moveRight(e, Arrays.asList( leftList.get(10), leftList.get(11), leftList.get(12) )); 
	}*/
	
	public App() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("GlobalClone00001.xml"));
		lgtvXml = new LGTVXml(doc); 
		
//		List<ImmutableItemTag> deserializedList = lgtvXml.readItemTags();
//		List<Item> deserializedList = lgtvXml.readItemTagsAsImmutableItem();
		List<Item> deserializedList = lgtvXml.readItemTagsAsMutableItem();
		leftList.addAll(deserializedList);
		
		startGUI();
	}

	public void startGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setFrameHandler(frameHandler); // FIXME: frameHandler must be set before leftList or rightList (a bit of a mess...)
					frame.setLeftList(leftList);
					frame.setRightList(rightList);					
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	/*public static void saveTestOnlyDemo() throws SAXException, IOException, ParserConfigurationException {
		System.out.println("Recognized 'saveTestOnly' argument. Not running GUI.");
		
		LGTVXml lgtvXml = LGTVXml.fromFile(Paths.get("GlobalClone00001.xml"));
		
		//List<ImmutableItemTag> channelsAsImmutableItemTag = lgtvXml.readItemTags();
		List<Item> channelsAsImmutableItemTag = lgtvXml.readItemTags2();
		lgtvXml.removeAllItemTags();
		
		Item ch0 = channelsAsImmutableItemTag.get(3);
		ImmutableItemTag it = new ImmutableItemTag(ch0.getAsMap());
		channelsAsImmutableItemTag.set( 3, it.set("vchName", "ABC TV") );
		
		lgtvXml.newAddChannels2(channelsAsImmutableItemTag);
		
		//Document doc = Saving.readAndRemoveItemTags(doc);
		
		lgtvXml.writeXml(Paths.get( "NoChannels3.xml" ));
		
		System.out.println("save received - saving to a file...");
	}*/
	
	public static void histDataOnlyDemo() throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Recognized 'histData' argument. Not running GUI.");
		
		LGTVXml lgtvXml = LGTVXml.fromFile(Paths.get("GlobalClone00001.xml"));
		List<Item> channels = lgtvXml.readItemTagsAsImmutableItem();
				
		//listOfMaps.stream().flatMap(x -> x.entrySet().stream()).peek( x -> System.out.println(x.getKey())).forEach( x -> {} );
		Map<String, Map<String, Long>> frequencies = channels.stream()
				.map(x -> x.getAsMap())
				.flatMap(x -> x.entrySet().stream())
				.collect(Collectors.groupingBy( Entry::getKey,
				Collectors.groupingBy( Entry::getValue, Collectors.counting()) ));

		frequencies.forEach((k,v) -> {
			//if ((v.size() >= 10) && (v.size() < 20)) {
			if ((v.size() < 10)) {
				System.out.println(k + ":" + v);
			} else { 
				System.out.format("  " + k + ": %s ... %n", 
						v.entrySet().stream().limit(5).collect(Collectors.toMap(Entry::getKey,  Entry::getValue))); }
			});

// TODO: HERE: COME UP WITH FILTERS.
		
		// Here -> need to apply some filters.
		
		//System.out.println(o);
		  
/*()		//Desired:
		Map<String, Map<String, Integer>> result = new HashMap<>();
		  Map<String, Integer> colors = new HashMap<>();
		    colors.put("red", 2);
		    colors.put("blue", 1);
		  result.put("color", colors) ; // red:2, blue:1
		  
		  Map<String, Integer> sizes = new HashMap<>();
		    sizes.put("small", 3);
		  result.put("size", sizes);
		    
		  Map<String, Integer> volumes = new HashMap<>();
		    volumes.put("low", 1);
		    volumes.put("medium", 1);
		    volumes.put("high", 1);
		  result.put("volume", volumes);
		  
		  System.out.println("result" + result);*/
}
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		// TODO: Remove the messing conditional:
		if (args.length==1) {
			switch (args[0]) {
//				case "saveTestOnly": saveTestOnlyDemo(); break;
				case "histData":     histDataOnlyDemo(); break;
				default: System.err.println("Invalid args.");
			}
		} else {
			new App();
		}
	}
	
}
