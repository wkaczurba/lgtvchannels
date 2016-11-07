package com.kaczurba.lgtvchannels.xmls;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

// TODO: Add path -> so it is easier to get where it is needed to get.
public class LGTVXml {
	final Document doc;
	public LGTVXml(Document doc) {
		this.doc = doc;
	}
	
	public static LGTVXml fromFile(Path path) throws ParserConfigurationException, SAXException, IOException {
		// Read Tags first.
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		//Document doc = db.parse(new File("GlobalClone00001.xml"));
		Document doc = db.parse(path.toFile());
		return new LGTVXml(doc);
	}
	
	public void addChannels(final Node channelsNode, List<ImmutableItemTag> immutableItemTags) {
		//Document doc = channelsNode.getOwnerDocument();
		
		for (ImmutableItemTag immutableTag : immutableItemTags) {
			Map<String, String> map = immutableTag.getAsMap();
			
			Element el = doc.createElement("ITEM");
				channelsNode.appendChild(doc.createTextNode("\n"));
				map.forEach((k,v) -> {
					Element e = doc.createElement(k);
					e.appendChild(doc.createTextNode(v));
					el.appendChild(doc.createTextNode("\n"));						
					el.appendChild(e);
					channelsNode.appendChild(el);
				});
				el.appendChild(doc.createTextNode("\n"));	
		}
		channelsNode.appendChild(doc.createTextNode("\n"));			
	}
	
	public Node getTopNode(Document doc) {
		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
				
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		final Node topNode = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");
		return topNode;
	}
	
	@Deprecated
	public void newAddChannels(List<ImmutableItemTag> immutableItemTags) {
		final Node channelsNode = getTopNode(doc);
				
		for (ImmutableItemTag immutableTag : immutableItemTags) {
			Map<String, String> map = immutableTag.getAsMap();
			
			Element el = doc.createElement("ITEM");
				channelsNode.appendChild(doc.createTextNode("\n"));
				map.forEach((k,v) -> {
					Element e = doc.createElement(k);
					e.appendChild(doc.createTextNode(v));
					el.appendChild(doc.createTextNode("\n"));						
					el.appendChild(e);
					channelsNode.appendChild(el);
				});
				el.appendChild(doc.createTextNode("\n"));	
	
		}
		channelsNode.appendChild(doc.createTextNode("\n"));			
	}

	public void newAddChannels2(List<? extends Item> immutableItemTags) {
		final Node channelsNode = getTopNode(doc);
				
		for (Item immutableTag : immutableItemTags) {
			Map<String, String> map = immutableTag.getAsMap();
			
			Element el = doc.createElement("ITEM");
				channelsNode.appendChild(doc.createTextNode("\n"));
				map.forEach((k,v) -> {
					Element e = doc.createElement(k);
					e.appendChild(doc.createTextNode(v));
					el.appendChild(doc.createTextNode("\n"));						
					el.appendChild(e);
					channelsNode.appendChild(el);
				});
				el.appendChild(doc.createTextNode("\n"));	
	
		}
		channelsNode.appendChild(doc.createTextNode("\n"));			
	}	
	
	public List<Item> readItemTagsAsImmutableItem() {		
		List<Item> immutableItemTags = new ArrayList<Item>();
		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
				
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		n = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");
				
		nodes = XMLElementsView.getChildElementNodes(n);

		nodes.stream()
			.map(node -> new XMLMappedItemTag(node))
			.map(itemTag -> (new ImmutableItemTag.Builder(itemTag).build()))
			.forEach(immutableItemTag -> immutableItemTags.add(immutableItemTag));
				
		return immutableItemTags;					
	}
	
	public List<Item> readItemTagsAsMutableItem() {		
		List<Item> mutableItemTags = new ArrayList<Item>();
		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
				
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		n = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");
				
		nodes = XMLElementsView.getChildElementNodes(n);

		nodes.stream()
			.map(node -> new XMLMappedItemTag(node))
			.map(itemTag -> (new MutableItemTag(itemTag)))
			.forEach(mutableItemTag -> mutableItemTags.add(mutableItemTag));
				
		return mutableItemTags;					
	}	

	public void removeAllItemTags() {		
		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		n = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");
			
		nodes = XMLElementsView.getChildElementNodes(n);
		System.out.println("nodes: " + nodes );
			
		List<ImmutableItemTag> immutableItemTags = new ArrayList<ImmutableItemTag>();
			
		nodes.stream()
			.map(node -> new XMLMappedItemTag(node))
			.map(itemTag -> (new ImmutableItemTag.Builder(itemTag).build()))
			.forEach(immutableItemTag -> immutableItemTags.add(immutableItemTag));
			
		final Node topNode = n;
		final List<Node> removedNodes = new ArrayList<>();
			
		XMLNode.getChildNodes(topNode).stream().forEach( child -> removedNodes.add( topNode.removeChild(child) ) );
			
			//nodeAnalysis(removedNodes.get(0));
	}

	public Document readAndRemoveItemTags() {		
		Predicate<Node> elementsOnly = node -> (NodeType.fromInteger(node.getNodeType()) == NodeType.ELEMENT_NODE && (node.getNodeValue() == null) ); 
		List<Node> nodes = XMLElementsView.getChildElementNodesWithName(doc.getFirstChild(), "CHANNEL");
		//System.out.println(nodes);
			
		Node n = XMLElementsView.getFirstChildElementNodeWithName(doc.getFirstChild(), "CHANNEL");
		n = XMLElementsView.getFirstChildElementNodeWithName(n, "DTV");
			
		System.out.println("Children of " + XMLElementsView.getPathAsString(n) + ":");
		nodes = XMLElementsView.getChildElementNodes(n);
		System.out.println("nodes: " + nodes );
			
		List<ImmutableItemTag> immutableItemTags = new ArrayList<ImmutableItemTag>();
			
		XMLMappedItemTag it = new XMLMappedItemTag(nodes.get(0));
		it.getAsMap().forEach((k,v) -> System.out.println(k+":"+v) );
		System.out.println("\nit2: ");
			
		nodes.stream()
			.map(node -> new XMLMappedItemTag(node))
			.map(itemTag -> (new ImmutableItemTag.Builder(itemTag).build()))
			.forEach(immutableItemTag -> immutableItemTags.add(immutableItemTag));
			
		// REMOVING ALL NODES:
		final Node topNode = n;				
		final List<Node> removedNodes = new ArrayList<>();
			
		XMLNode.getChildNodes(topNode).stream().forEach( child -> removedNodes.add( topNode.removeChild(child) ) );
			
//		nodeAnalysis(removedNodes.get(0));
		addChannels(topNode, immutableItemTags);
			
		return doc;
	}		
	
	//
	public void writeXml(Path fileOutputPath) {
		
		doc.setXmlStandalone(true); //before creating the DOMSource
		
		 // Use a Transformer for output
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer;
		try {
			OutputStream fos = Files.newOutputStream(fileOutputPath);
			
			transformer = tFactory.newTransformer(); 
		    DOMSource source = new DOMSource(doc);
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
}
