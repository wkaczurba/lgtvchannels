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
	final MainFrame frame = new MainFrame();
	
	private final LGTVXml lgtvXml;
	
	FrameHandler frameHandler = new FrameHandler() {
		
		@Override
		public void moveRight(ActionEvent e, List<Item> selectedItems) {
			System.out.println("moveRight received; selected: " + selectedItems);
			leftList.removeAll(selectedItems);
			for (Item item : selectedItems) {
				rightList.add(item);
			}
			frame.refresh();
		}

		@Override
		public void moveLeft(ActionEvent e, List<Item> what) {
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
			// TODO Handle UNDO.
			System.out.println("undo received");
		}

		@Override
		public void saveWIP(ActionEvent e) {
			// TODO: Saving to a file.
			lgtvXml.removeAllItemTagsAsNodes();
			lgtvXml.newAddChannels2(leftList);
			lgtvXml.writeXml(Paths.get("tmpSaveLeft.xml"));
			System.out.println("Saved: leftList().size=" + leftList.size());
			
			lgtvXml.removeAllItemTagsAsNodes();
			lgtvXml.newAddChannels2(rightList);
			lgtvXml.writeXml(Paths.get("tmpSaveRight.xml"));
			System.out.println("Saved: rightList().size=" + rightList.size());
			
			System.out.println("Saved (shoulld be...");
		}

		@Override
		public void load(ActionEvent e) {
			final Path pathLeft = Paths.get("tmpSaveLeft.xml");
			final Path pathRight = Paths.get("tmpSaveRight.xml");

			System.out.println("load received");
//			Path path = Paths.get("output.xml");
			
			leftList.clear();
			rightList.clear();
			
			try {
				LGTVXml.fromFile(pathLeft).readItemTagsAsMutableItem().forEach(x -> leftList.add(x));
				LGTVXml.fromFile(pathRight).readItemTagsAsMutableItem().forEach(x -> rightList.add(x));
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				e1.printStackTrace();
			}
			
			frame.refresh();
		}

		@Override
		public void redo(ActionEvent e) {
			System.out.println("redo received");
		}

		@Override
		public void newFromFile(ActionEvent e) {
			final Path path = Paths.get("GlobalClone00001.xml"); 
			
			leftList.clear();
			rightList.clear();
			
			System.out.println("PATH TO THE FILE HAS TO BE ADDED HERE. " + path);
			try {
				LGTVXml.fromFile(path).readItemTagsAsMutableItem().forEach(x -> leftList.add(x));
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			frame.refresh();
		}
	};
	
	public App() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("GlobalClone00001.xml"));
		lgtvXml = new LGTVXml(doc); 
		
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
		//List<Item> channels = lgtvXml.readItemTagsAsImmutableItem();
		List<Item> channels = lgtvXml.readItemTagsAsMutableItem();
				
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
