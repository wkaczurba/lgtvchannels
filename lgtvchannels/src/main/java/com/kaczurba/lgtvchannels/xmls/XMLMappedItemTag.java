package com.kaczurba.lgtvchannels.xmls;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

public class XMLMappedItemTag implements Item, Serializable {
	
	private final transient Node node;
	
	private void sanitize() {
		List<Node> childNodes = XMLNode.getChildNodes(node);
		
		if (childNodes.size() == 0)
			throw new IllegalArgumentException("Node has no childnodes.");		
		
		Set<NodeType> nodeTypes = childNodes.stream().map(n -> NodeType.fromNode(n)).collect(Collectors.toSet());
		int maxDepth = XMLNode.getDepth(node, XMLElementsView.elementsOnly);
		
		if (maxDepth > 2) {
			throw new IllegalArgumentException("Node has more than 2 levels");
		}
		
		nodeTypes.removeIf(nt -> nt.equals(NodeType.ELEMENT_NODE) || nt.equals(NodeType.TEXT_NODE));
		if (nodeTypes.size() > 0) {
			throw new IllegalArgumentException("Node should not have childNodes with types: " + nodeTypes);
		}
		
		// Checking for non-unique elements:
		List<Node> elementChildNodes = XMLElementsView.getChildElementNodes(node);
		Set<String> keys = new HashSet<>();
		for (Node n : elementChildNodes) {
			if (!keys.add(n.getNodeName())) {
				throw new IllegalArgumentException("Node contains non-unique elements: " + n.getNodeName()); 
			}
		}
	}
	
	public XMLMappedItemTag(Node node) {
		this.node = node;
		sanitize();
		
/*		System.out.println("ITEMS: " + getKeys());
		System.out.println("serviceType -> " + get("serviceType") );
		
		set("prNum", "-999");
		
		for (String key : getKeys()) {
			System.out.println(key + " : " + get(key));
		}*/
	}
	
	protected Map<String, Node> getKeysAsHashMap() {
		Map<String, Node> map = XMLElementsView.getChildElementNodes(node).stream().collect(Collectors.toMap(n -> n.getNodeName(), n -> n));
		return map;
	}
	
	protected Node getNode(String key) {
		return getKeysAsHashMap().get(key);
	}
	
	public Set<String> getKeys() {
		return getKeysAsHashMap().keySet();
	}

	public void set(String key, String value) {
		XMLNode.getChildNodeOfType( getNode(key), NodeType.TEXT_NODE).setNodeValue(value);
	}
	
	public String get(String key) {
		return XMLNode.getChildNodeOfType( getNode(key), NodeType.TEXT_NODE).getNodeValue();
	}
	

	// TODO: Deal with this...
	/*public static class Builder {
		private Node node;
		private 
		
		public Builder()
		
	}*/
	
	public String toString() {
		return getKeys().toString();
		//return getKeys().stream().peek(k->System.out.println("processing: " + k)).map(k -> k +":" + get(k)).collect(Collectors.joining(System.lineSeparator())); 
	}

	@Override
	public Map<String, String> getAsMap() {
		
		// THIS ONE SHOULD BE WORKING OK:
		/*Map<String, String> map = XMLElementsView.getChildElementNodes(node)
				.stream().collect(Collectors.toMap(n -> n.getNodeName(), n -> n.getNodeValue()));
		return map;*/

		LinkedHashMap<String, String> map2 = new LinkedHashMap<>();
		XMLElementsView.getChildElementNodes(node).stream().forEach( n -> map2.put(n.getNodeName(), get(n.getNodeName())) );
		
		return map2;
		
	}
}


