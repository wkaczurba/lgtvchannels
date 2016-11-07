package com.kaczurba.lgtvchannels.xmls;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNode {
	
	public static List<Node> nodeListToList(NodeList nodeList) {
		List<Node> nodes = new ArrayList<Node>();
		for (int i=0; i < nodeList.getLength(); i++) {
			nodes.add(nodeList.item(i));
		}
		return nodes;
	}
	
	public static int getDepth(Node node) {
		return getDepth(node, n -> true);
	}
	
	public static int getDepth(Node node, Predicate<Node> predicate) {
		int value;
		OptionalInt optionalInt = getChildNodes(node).stream().filter(predicate).mapToInt(n -> getDepth(n)).max();

		if (!optionalInt.isPresent())
			return 0;
		return optionalInt.getAsInt() + 1;
	}
	
	public static List<Node> getChildNodes(Node node) {
		return nodeListToList(node.getChildNodes());
	}
	
	public static Node getChildNodeOfType(Node node, NodeType nodeType) {
		List<Node> childTextNodes = XMLNode.getChildNodes( node, n -> NodeType.fromNode(n) == nodeType );
		if (childTextNodes.size() == 0) {
			throw new IllegalArgumentException("Node does not have any childNodes of type: " + nodeType);
		} else if (childTextNodes.size() > 1) {
			throw new IllegalArgumentException("Node has " + childTextNodes.size() + " childNodes of type: " + nodeType);
		}
		return childTextNodes.get(0);
	}
	
	public static List<Node> getChildNodes(Node node, Predicate<Node> predicate) {
		List<Node> matchingNodes = new ArrayList<Node>(); 
		NodeType nodeType = NodeType.fromInteger( node.getNodeType() );
		
		return XMLNode.getChildNodes(node).stream().filter(predicate).collect(Collectors.toList());
	}
		
	public static String getNodeInfo(Node node) {
		StringBuilder sb = new StringBuilder();
		NodeType nodeType = NodeType.fromInteger(node.getNodeType());
		
		sb.append(String.format("node.getNodeName(): %s%n", node.getNodeName()));
		sb.append(String.format("noode.getNodeType(): %s%n", nodeType));
		sb.append(String.format("node.getNodeValue(): %s%n", node.getNodeValue()));
		
		NamedNodeMap namedNodeMap = node.getAttributes();
		sb.append(String.format("node.getAttributes():%n"));
		
		if (namedNodeMap == null)
			return sb.toString();
		else {
			int length = namedNodeMap.getLength(); 
			sb.append(String.format("  .getLength(): " + length));
			// Print subnodes here.
			//sb.append(c)
			
			for (int j = 0; j < length; j++) {
				//String str = getNodeInfo(node);
				String str = getNodeInfo(namedNodeMap.item(j));
				
				
				String toAppend = Arrays.asList( str.split(System.lineSeparator()) )
					.stream()
					.map(s -> "  " + s)
					.collect(Collectors.joining());
				
				sb.append(toAppend);
			}
			
		}

		return sb.toString();
	}

}
