package com.kaczurba.lgtvchannels.xmls;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class XMLElementsView {
	public final static Predicate<Node> elementsOnly = node -> (NodeType.fromInteger(node.getNodeType()) == NodeType.ELEMENT_NODE && (node.getNodeValue() == null) );
	
	public static List<Node> getChildElementNodes(Node node) {
		return XMLNode.getChildNodes(node, elementsOnly);
	}
	
	public static List<Node> getChildElementNodesWithName(Node node, String nodeName) {
		List<Node> nodes = getChildElementNodes(node);
		return getNodesWithName(nodes, nodeName);
	}
	
	public static Node getFirstChildElementNodeWithName(Node node, String nodeName) {
		List<Node> nodes = getChildElementNodesWithName(node, nodeName);
		if (nodes.size() > 0)
			return nodes.get(0);
		return null;
	}
	
	public static List<Node> getNodesWithName(List<Node> nodes, String nodeName) {
		return nodes.stream().filter(node -> node.getNodeName().equals(nodeName) ).collect(Collectors.toList());
	}
	
	public static List<Node> getPath(Node node) {
		List<Node> path = new ArrayList<>();
		Node currentNode = node;
		do {
			path.add(0, currentNode);
			currentNode = currentNode.getParentNode();
		} while (currentNode != null);
		return path;
	}
	
	public static String getPathAsString(Node node) {
		return getPath(node).stream().map(n -> n.getNodeName()).collect(Collectors.joining(","));
	}
	
	public static Node getNodeWithName(List<Node> nodes, String nodeName) {
		List<Node> nodes2 = nodes.stream().filter(node -> node.getNodeName().equals(nodeName) ).collect(Collectors.toList());
		if (nodes2.size() > 1)
			throw new IllegalStateException("More than one node is named: " + nodeName);
		if (nodes2.size() == 0)
			throw new IllegalStateException("No node found: " + nodeName);
		
		return nodes2.get(0);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public static Node getTextNodeChildNode(Node node) {
		List<Node> nodes = XMLNode.getChildNodes(node, n -> NodeType.fromInteger(n.getNodeType()) == NodeType.TEXT_NODE);
		
		if (nodes.size() > 1)
			throw new IllegalStateException("More than one TEXT_NODE was found");
		if (nodes.size() == 0) {
			throw new IllegalStateException("No TEXT_NODE found; Only the following ones were found" + XMLNode.getChildNodes(node, n -> true).stream().map(x -> NodeType.fromInteger(x.getNodeType()) ).collect(Collectors.toList()) );
		}
		
		return nodes.get(0);
	}
}

