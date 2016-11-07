package com.kaczurba.lgtvchannels.xmls;

import java.util.Arrays;

import org.w3c.dom.Node;

//import javax.xml.soap.Node;

public enum NodeType {
    /**
     * The node is an <code>Element</code>.
     */	
    ELEMENT_NODE(1),
    /**
     * The node is an <code>Attr</code>.
     */
    ATTRIBUTE_NODE(2),
    /**
     * The node is a <code>Text</code> node.
     */
    TEXT_NODE(3),
    /**
     * The node is a <code>CDATASection</code>.
     */
    CDATA_SECTION_NODE(4),
    /**
     * The node is an <code>EntityReference</code>.
     */
    ENTITY_REFERENCE_NODE(5),
    /**
     * The node is an <code>Entity</code>.
     */
    ENTITY_NODE(6),
    /**
     * The node is a <code>ProcessingInstruction</code>.
     */
    PROCESSING_INSTRUCTION_NODE(7),
    /**
     * The node is a <code>Comment</code>.
     */
    COMMENT_NODE(8),
    /**
     * The node is a <code>Document</code>.
     */
    DOCUMENT_NODE(9),
    /**
     * The node is a <code>DocumentType</code>.
     */
    DOCUMENT_TYPE_NODE(0),
    /**
     * The node is a <code>DocumentFragment</code>.
     */
    DOCUMENT_FRAGMENT_NODE(1),
    /**
     * The node is a <code>Notation</code>.
     */
    NOTATION_NODE(2);
    
    final short value; 
    private NodeType(int value) {
    	this.value = (short) value;
    }
    
    public static NodeType fromNode(Node n) {
    	return fromInteger(n.getNodeType());
    }
    
    public static NodeType fromInteger(int value) {
    	
    	for (NodeType x : NodeType.values()){
    		if (x.value == value)
    			return x;
    	}
    	throw new IllegalArgumentException("Invalid argument provided.");
    }
}
