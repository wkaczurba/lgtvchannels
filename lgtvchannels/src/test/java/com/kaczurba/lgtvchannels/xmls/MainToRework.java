package com.kaczurba.lgtvchannels.xmls; 

//import java.nio.file.Path;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kaczurba.lgtvchannels.xmls.ImmutableItemTag;
import com.kaczurba.lgtvchannels.xmls.XMLElementsView;
import com.kaczurba.lgtvchannels.xmls.XMLMappedItemTag;
import com.kaczurba.lgtvchannels.xmls.XMLNode;

@SuppressWarnings("deprecation")
public class MainToRework {
	
	
	public MainToRework() throws ParserConfigurationException, SAXException, IOException {
		
	}
	
	public void writeXml(Document document, Path fileOutputPath) {
		document.setXmlStandalone(true); //before creating the DOMSource
		 // Use a Transformer for output
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer;
		try {
			OutputStream fos = Files.newOutputStream(fileOutputPath);
			
			transformer = tFactory.newTransformer(); 
		    DOMSource source = new DOMSource(document);
		    //StreamResult result = new StreamResult(System.out);
		    StreamResult result = new StreamResult(fos);
			transformer.transform(source, result);
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public List<String> getTagsFromChannels ( List<XMLMappedItemTag> channels ) {
		System.out.println("Printing tags in a channel:");
		Set<String> tags = channels.stream().map(i -> i.getKeys().stream()).flatMap(i -> i).collect(Collectors.toSet());
		List<String> expectedTagsList = Arrays.asList("prNum","minorNum","original_network_id","transport_id","network_id","service_id","physicalNum","sourceIndex","serviceType","special_data","frequency","nitVersion","mapType","mapAttr","isInvisable","isBlocked","isSkipped","isNumUnSel","isDeleted","chNameByte","isDisabled","hexVchName","notConvertedLengthOfVchName","vchName","lengthOfVchName","hSettingIDHandle","usSatelliteHandle","isUserSelCHNo","logoIndex","videoStreamType");
	
		Set<String> knownTags = new HashSet<>(expectedTagsList);
		if (!knownTags.equals(tags)) {
			throw new IllegalStateException("Tags are different than expected.");
		}
		
		return expectedTagsList;
	}
	
	public void saveFullListToCSV(List<XMLMappedItemTag> channels, OutputStream os) {
		PrintStream ps = new PrintStream(os);
		
		//FIXME:
		List<String> tags = getTagsFromChannels(channels);
		tags.stream().forEach(t -> ps.print(t + ","));
		ps.println();
		channels.stream().forEach(ch -> {
			tags.forEach( t -> ps.print(ch.get(t) + ",") );
			ps.println();
		});		
	}
	
	public static List<XMLMappedItemTag> readItemTags(String fileName) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(fileName));

		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
		//System.out.println(nodes);
		
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		n = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");

		// TODO: Next step -> get Channel only from it...
		//System.out.println(n);
		
		nodes = XMLElementsView.getChildElementNodes(n);
		//System.out.println( nodes );
		n = nodes.get(0);
		//System.out.println( n );
		XMLElementsView.getChildElementNodes(n);
		//System.out.println( XMLElementsView.getChildElementNodes(n) );
		
		//System.out.println("*** CHANNEL VIEWS ***:");
		
		List<XMLMappedItemTag> channels = nodes.stream().map(x -> new XMLMappedItemTag(x)).collect(Collectors.toList());
		return channels;
	}
	
	public static void serializeItemsToFile(Path path, List<ImmutableItemTag> immutableItemTags) throws IOException {
		//ImmutableItemTag[] itemTagsArr = itemTags.toArray(new ItemTag[0]);
		
		try (OutputStream os = Files.newOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(os)) {
//			oos.writeObject( itemTagsArr );
			oos.writeObject( immutableItemTags );
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public static List<ImmutableItemTag> deserializeItemsTagsFromFile(Path path) {
		try (InputStream is = Files.newInputStream(path);
			 ObjectInputStream ois = new ObjectInputStream(is)) {
			
				Object o = ois.readObject();
				//ImmutableItemTag[] arrayOfItemTags = (ImmutableItemTag[]) o; 
//				return new ArrayList<ImmutableItemTag>(Arrays.asList(arrayOfItemTags));
				return (List<ImmutableItemTag>) o;
		} catch (ClassNotFoundException | ClassCastException | IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public void readListOfItemTags() {
		
	}

	public void readXml(String fileName) throws ParserConfigurationException, SAXException, IOException {
		//path.toString();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(fileName));
		
		
		System.out.println( doc.getDocumentElement() );
		System.out.println( doc.getNamespaceURI() );
		System.out.println( doc.getParentNode() );
		System.out.println( doc.getBaseURI() );
		
		System.out.println( doc.getNodeName() );
		System.out.println( doc.getNodeValue() );
		System.out.println( doc.getLocalName() );
		System.out.println( doc.getChildNodes() );
		
		// Need an algorithm for traversing the XML.
		
		System.out.println("********** Node Info: *************");
		NodeList nodeList = doc.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++ ){
			//Node node = nodeList.item(i);
			
			System.out.println( XMLNode.getNodeInfo(nodeList.item(i)) );
		}
		
		System.out.println("*********** Traversing *************");
		
		//Predicate<Node> elementsOnly = node -> (NodeType.fromInteger(node.getNodeType()) == NodeType.ELEMENT_NODE && (node.getNodeValue() == null) ); 
		//Predicate<Node> elementsOnly = node -> (!(node instanceof Text)) ;
		//Predicate<Node> e = node -> true;
		
		
		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
		System.out.println(nodes);
		
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		n = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");

		// TODO: Next step -> get Channel only from it...
		System.out.println(n);
		
		nodes = XMLElementsView.getChildElementNodes(n);
		System.out.println( nodes );
		n = nodes.get(0);
		System.out.println( n );
		XMLElementsView.getChildElementNodes(n);
		System.out.println( XMLElementsView.getChildElementNodes(n) );
		
		System.out.println("*** CHANNEL VIEWS ***:");
		
		List<XMLMappedItemTag> channels = nodes.stream().map(x -> new XMLMappedItemTag(x)).collect(Collectors.toList());
		Comparator<XMLMappedItemTag> compareByPrNum = (a, b) -> Integer.parseInt(a.get("prNum")) - Integer.parseInt(b.get("prNum"));
		
		channels.stream()
			.sorted( compareByPrNum )
			.forEach(x -> System.out.println(x.get("prNum") + "," + x.get("minorNum") + "," + x.get("vchName") + ","));
		
		// Print to a file:
		PrintStream csv = new PrintStream("channels.csv");
	
		
		channels.stream()
		.sorted( (a, b) -> Integer.parseInt(a.get("prNum")) - Integer.parseInt(b.get("prNum")))
		.forEach(x -> csv.println(x.get("prNum") + "," + x.get("minorNum") + "," + x.get("vchName") + ","));
		
		csv.close();


		try (FileOutputStream fos = new FileOutputStream("allChannels.csv")) {
			saveFullListToCSV(channels, fos);
		}
		// Creating list of channels:
		System.out.println("Writing XML:");
		writeXml(doc, Paths.get("New.xml"));
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		MainToRework main = new MainToRework();
// TODO: Move it to outside.
		
		main.readXml("GlobalClone00001.xml");

		// Serialize + change.
	}

}
/* 
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder(); 
Document doc = db.parse(new File(filename));
*/